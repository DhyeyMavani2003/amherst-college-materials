#### Ice core example

# Analyses of ice cores from East Antarctica have allowed scientists to deduce historical 
# atmospheric conditions of the last few hundred thousand years

load("icecore.RData") 

par(mar=c(2.75,2.75,.5,.5),mgp=c(1.7,.7,0))
layout(matrix( c(1,1,2),nrow=1,ncol=3) )

plot(icecore[,1],  (icecore[,3]-mean(icecore[,3]))/sd(icecore[,3]) ,
     type="l",col="black",
     xlab="year",ylab="standardized measurement",ylim=c(-2.5,3))
legend(-115000,3.2,legend=c("temp",expression(CO[2])),bty="n",
       lwd=c(2,2),col=c("black","gray"))
lines(icecore[,1],  (icecore[,2]-mean(icecore[,2]))/sd(icecore[,2]),
      type="l",col="gray")

plot(icecore[,2], icecore[,3],xlab=expression(paste(CO[2],"(ppmv)")),ylab="temperature difference (deg C)")

# Correlated errors
par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))
par(mfrow=c(1,2))

lmfit<-lm(icecore$tmp~icecore$co2)
hist(lmfit$res,main="",xlab="residual",ylab="frequency")
acf(lmfit$res,ci.col="gray",xlab="lag")

#### Starting values for MCMC
n<-dim(icecore)[1]
y<-icecore[,3]
X<-cbind(rep(1,n),icecore[,2])
DY<-abs(outer( (1:n),(1:n) ,"-"))

# Starting values for betas will be from the OLS fit
lmfit = lm(y~ -1 + X) # remove coefficient because matrix X already contains a column 1
beta<-lmfit$coef # initial beta
s2<-summary(lmfit)$sigma^2
phi<-acf(lmfit$res,plot=FALSE)$acf[2]
nu0<-1 ; s20<-1 ; T0<-diag(1/1000,nrow=2)


## MCMC - 1000 scans saving every scan
#set.seed(1)
S<-1000 ; odens<-S/1000
OUT<-NULL ; ac<-0 ; par(mfrow=c(1,2))

## rmvnorm function for proposals
rmvnorm<-function(n,mu,Sigma)
{ # samples from the multivariate normal distribution
  E<-matrix(rnorm(n*length(mu)),n,length(mu))
  t(  t(E%*%chol(Sigma)) +c(mu))
}


for(s in 1:S)
{
  
  Cor<-phi^DY  ; iCor<-solve(Cor)
  V.beta<- solve( t(X)%*%iCor%*%X/s2 + T0)
  E.beta<- V.beta%*%( t(X)%*%iCor%*%y/s2  )
  beta<-t(rmvnorm(1,E.beta,V.beta)  )
  
  s2<-1/rgamma(1,(nu0+n)/2,(nu0*s20+t(y-X%*%beta)%*%iCor%*%(y-X%*%beta)) /2 )
  
  phi.p<-abs(runif(1,phi-.1,phi+.1))
  phi.p<- min( phi.p, 2-phi.p)
  lr<- -.5*( determinant(phi.p^DY,log=TRUE)$mod -
               determinant(phi^DY,log=TRUE)$mod  +
               sum(diag( (y-X%*%beta)%*%t(y-X%*%beta)%*%(solve(phi.p^DY) -solve(phi^DY)) ) )/s2 )
  
  if( log(runif(1)) < lr ) { phi<-phi.p ; ac<-ac+1 }
  
  if(s%%odens==0)
  {
    #cat(s,ac/s,beta,s2,phi,"\n") ; 
    OUT<-rbind(OUT,c(beta,s2,phi))
  }
}

OUT.1000<-OUT
library(coda)
apply(OUT.1000,2,effectiveSize )


## MCMC - 25000 scans saving every 25th scan
# set.seed(1)
S<-25000 ; odens<-S/1000
OUT<-NULL ; ac<-0 ; par(mfrow=c(1,2))
for(s in 1:S)
{
  
  Cor<-phi^DY  ; iCor<-solve(Cor)
  V.beta<- solve( t(X)%*%iCor%*%X/s2 + T0)
  E.beta<- V.beta%*%( t(X)%*%iCor%*%y/s2  )
  beta<-t(rmvnorm(1,E.beta,V.beta)  )
  
  s2<-1/rgamma(1,(nu0+n)/2,(nu0*s20+t(y-X%*%beta)%*%iCor%*%(y-X%*%beta)) /2 )
  
  phi.p<-abs(runif(1,phi-.1,phi+.1))
  phi.p<- min( phi.p, 2-phi.p)
  lr<- -.5*( determinant(phi.p^DY,log=TRUE)$mod -
               determinant(phi^DY,log=TRUE)$mod  +
               sum(diag( (y-X%*%beta)%*%t(y-X%*%beta)%*%(solve(phi.p^DY) -solve(phi^DY)) ) )/s2 )
  
  if( log(runif(1)) < lr ) { phi<-phi.p ; ac<-ac+1 }
  
  if(s%%odens==0)
  {
    #cat(s,ac/s,beta,s2,phi,"\n") ; 
    OUT<-rbind(OUT,c(beta,s2,phi))
  }
}

OUT.25000<-OUT
library(coda)
apply(OUT.25000,2,effectiveSize )

# Convergence check
par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))
par(mfrow=c(1,2))
plot(OUT.1000[,4],xlab="scan",ylab=expression(rho),type="l")
acf(OUT.1000[,4],ci.col="gray",xlab="lag")

par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))
par(mfrow=c(1,2))
plot(OUT.25000[,4],xlab="scan/25",ylab=expression(rho),type="l")
acf(OUT.25000[,4],ci.col="gray",xlab="lag/25")


par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))
par(mfrow=c(1,2))

plot(density(OUT.25000[,2],adj=2),xlab=expression(beta[2]),
     ylab="posterior marginal density",main="")

plot(y~X[,2],xlab=expression(CO[2]),ylab="temperature")
abline(mean(OUT.25000[,1]),mean(OUT.25000[,2]),lwd=2)
abline(lmfit$coef,col="gray",lwd=2)
legend(180,2.5,legend=c("GLS estimate","OLS estimate"),bty="n",
       lwd=c(2,2),col=c("black","gray"))