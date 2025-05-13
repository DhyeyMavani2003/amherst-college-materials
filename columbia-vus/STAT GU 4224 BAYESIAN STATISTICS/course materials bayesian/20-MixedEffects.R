#### NELS data
load("nelsSES.RData") 

ids<-sort(unique(nels$sch_id)) 
m<-length(ids)
Y<-list() ; X<-list() ; N<-NULL
for(j in 1:m) 
{
  Y[[j]]<-nels[nels$sch_id==ids[j], 4] 
  N[j]<- sum(nels$sch_id==ids[j])
  xj<-nels[nels$sch_id==ids[j], 3] 
  xj<-(xj-mean(xj))
  X[[j]]<-cbind( rep(1,N[j]), xj  )
}



#### OLS fits
S2.LS<-BETA.LS<-NULL
for(j in 1:m) {
  fit<-lm(Y[[j]]~-1+X[[j]] )
  BETA.LS<-rbind(BETA.LS,c(fit$coef)) 
  S2.LS<-c(S2.LS, summary(fit)$sigma^2) 
} 

par(mar=c(2.75,2.75,.5,.5),mgp=c(1.7,.7,0))
par(mfrow=c(1,3))

plot( range(nels[,3]),range(nels[,4]),type="n",xlab="SES", 
      ylab="math score")
for(j in 1:m) {    abline(BETA.LS[j,1],BETA.LS[j,2],col="gray")  }

BETA.MLS<-apply(BETA.LS,2,mean)
abline(BETA.MLS[1],BETA.MLS[2],lwd=2)

plot(N,BETA.LS[,1],xlab="sample size",ylab="intercept")
abline(h= BETA.MLS[1],col="black",lwd=2)
plot(N,BETA.LS[,2],xlab="sample size",ylab="slope")
abline(h= BETA.MLS[2],col="black",lwd=2)


#### Hierarchical regression model

## mvnormal simulation
rmvnorm<-function(n,mu,Sigma)
{ 
  E<-matrix(rnorm(n*length(mu)),n,length(mu))
  t(  t(E%*%chol(Sigma)) +c(mu))
}

## Wishart simulation
rwish<-function(n,nu0,S0)
{
  sS0 <- chol(S0)
  S<-array( dim=c( dim(S0),n ) )
  for(i in 1:n)
  {
    Z <- matrix(rnorm(nu0 * dim(S0)[1]), nu0, dim(S0)[1]) %*% sS0
    S[,,i]<- t(Z)%*%Z
  }
  S[,,1:n]
}

## Setup
p<-dim(X[[1]])[2]
theta<-mu0 = apply(BETA.LS,2,mean)
nu0<-1 ; s2<-s20<-mean(S2.LS)
eta0<-p+2
Sigma<-S0<-L0<-cov(BETA.LS)
BETA<-BETA.LS
THETA.b<-S2.b<-NULL
iL0<-solve(L0) ; iSigma<-solve(Sigma)
Sigma.ps<-matrix(0,p,p)
SIGMA.PS<-NULL
BETA.ps<-BETA*0
BETA.pp<-NULL
#set.seed(1)
mu0[2]+c(-1.96,1.96)*sqrt(L0[2,2])

## MCMC
for(s in 1:10000) {
  ##update beta_j 
  for(j in 1:m) 
  {  
    Vj<-solve( iSigma + t(X[[j]])%*%X[[j]]/s2 )
    Ej<-Vj%*%( iSigma%*%theta + t(X[[j]])%*%Y[[j]]/s2 )
    BETA[j,]<-rmvnorm(1,Ej,Vj) 
  } 
  ##
  
  ##update theta
  Lm<-  solve( iL0 +  m*iSigma )
  mum<- Lm%*%( iL0%*%mu0 + iSigma%*%apply(BETA,2,sum))
  theta<-t(rmvnorm(1,mum,Lm))
  ##
  
  ##update Sigma
  mtheta<-matrix(theta,m,p,byrow=TRUE)
  iSigma<-rwish(1, eta0+m, solve( S0+t(BETA-mtheta)%*%(BETA-mtheta) ) )
  ##
  
  ##update s2
  RSS<-0
  for(j in 1:m) { RSS<-RSS+sum( (Y[[j]]-X[[j]]%*%BETA[j,] )^2 ) }
  s2<-1/rgamma(1,(nu0+sum(N))/2, (nu0*s20+RSS)/2 )
  ##
  ##store results
  if(s%%10==0) 
  { 
    #cat(s,s2,"\n")
    S2.b<-c(S2.b,s2);THETA.b<-rbind(THETA.b,t(theta))
    Sigma.ps<-Sigma.ps+solve(iSigma) ; BETA.ps<-BETA.ps+BETA
    SIGMA.PS<-rbind(SIGMA.PS,c(solve(iSigma)))
    BETA.pp<-rbind(BETA.pp,rmvnorm(1,theta,solve(iSigma)) )
  }
  ##
}

## MCMC diagnostics
library(coda)
effectiveSize(S2.b)
effectiveSize(THETA.b[,1])
effectiveSize(THETA.b[,2])

apply(SIGMA.PS,2,effectiveSize)

tmp<-NULL;for(j in 1:dim(SIGMA.PS)[2]) { tmp<-c(tmp,acf(SIGMA.PS[,j])$acf[2]) }

acf(S2.b)
acf(THETA.b[,1])
acf(THETA.b[,2])

par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))
par(mfrow=c(1,2))

plot(density(THETA.b[,2],adj=2),xlim=range(BETA.pp[,2]), 
     main="",xlab="slope parameter",ylab="posterior density",lwd=2)
lines(density(BETA.pp[,2],adj=2),col="gray",lwd=2)
legend( -3 ,1.0 ,legend=c( expression(theta[2]),expression(tilde(beta)[2])), 
        lwd=c(2,2),col=c("black","gray"),bty="n") 

quantile(THETA.b[,2],prob=c(.025,.5,.975))
mean(BETA.pp[,2]<0) 

BETA.PM<-BETA.ps/1000
plot( range(nels[,3]),range(nels[,4]),type="n",xlab="SES",
      ylab="math score")
for(j in 1:m) {    abline(BETA[j,1],BETA[j,2],col="gray")  }
abline( mean(THETA.b[,1]),mean(THETA.b[,2]),lwd=2 )


# Example 2: rstan
# simulate some data
# set.seed(20180417)
N = 500 # total sample size
J = 10 # number of groups
id<-rep(1:J,each=50) #index of groups 
K<-3 #number of regression coefficients

#population-level regression coefficient
theta <- c(10,-0.05,5)
#standard deviation of the group-level coefficient
tau<-c(5,0.1,1)
#standard deviation of individual observations
sigma<-6
#group-level regression coefficients
beta<-mapply(function(g,t) rnorm(J,g,t),g= theta,t=tau)
#the model matrix
X<-model.matrix(~x+y,data=data.frame(x=runif(N,-2,2),y=runif(N,-2,2)))

#simulate response data Y
y<-vector(length = N)
for(n in 1:N) y[n]<-rnorm(1,X[n,] %*% beta[id[n],], sigma) 

dim(X)
head(X)
head(Y)

write("
data {
  int<lower=1> N; //the number of observations
  int<lower=1> J; //the number of groups
  int<lower=1> K; //number of columns in the model matrix
  int<lower=1,upper=J> id[N]; //vector of group indices
  matrix[N,K] X; //the model matrix
  vector[N] y; //the response variable
}
parameters {
  vector[K] theta; //population-level regression coefficients
  vector<lower=0>[K] tau; //the standard deviation of the regression coefficients
  vector[K] beta_raw[J];
  real<lower=0> sigma; //standard deviation of the individual observations
}
transformed parameters {
  vector[K] beta[J]; //matrix of group-level regression coefficients
  //computing the group-level coefficient, based on non-centered parameterization based on section 22.6 STAN (v2.12) user's guide
  for(j in 1:J){
    beta[j] = theta + tau .* beta_raw[j];
  }
}
model {
  vector[N] mu; //linear predictor
  //priors
  theta ~ normal(0,5); //weakly informative priors on the regression coefficients
  tau ~ cauchy(0,2.5); //weakly informative priors, see section 6.9 in STAN user guide
  sigma ~ gamma(2,0.1); //weakly informative priors, see section 6.9 in STAN user guide
  for(j in 1:J){
   beta_raw[j] ~ normal(0,1); //fill the matrix of group-level regression coefficients;
   //implies beta~normal(gamma,tau)
  }
  for(n in 1:N){
    mu[n] = X[n] * beta[id[n]]; //compute the linear predictor using relevant group-level regression coefficients
  }
  //likelihood
  y ~ normal(mu,sigma);
}", "Example6.stan")

library(rstan)
mod <- stan_model("Example6.stan")

mod1_fit <- sampling(mod,  data=list(N=N,J=J,K=K,id=id,X=X,y=y))
summary(mod1_fit, pars = c("theta", "tau"),probs = c(0.025, 0.975))$summary