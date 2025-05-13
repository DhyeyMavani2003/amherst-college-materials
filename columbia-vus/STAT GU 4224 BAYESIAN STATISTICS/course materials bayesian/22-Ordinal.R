# Example 1: Educational attainment
load("socmob.RData") 

yincc<-match(socmob$INC,sort(unique(socmob$INC)))
ydegr<-socmob$DEGREE+1
yage<-socmob$AGE
ychild<-socmob$CHILD
ypdeg<-1*(socmob$PDEG>2)
tmp<-lm(ydegr~ychild+ypdeg+ychild:ypdeg)


# Graph summaries of variables
par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))
par(mfrow=c(1,2))
plot(table(socmob$DEG+1)/sum(table(socmob$DEG+1)),
     lwd=2,type="h",xlab="DEG",ylab="probability")
plot(table(socmob$CHILD)/sum(table(socmob$CHILD)),lwd=2,type="h",xlab="CHILD",ylab="probability" )

X<-cbind(ychild,ypdeg,ychild*ypdeg)
y<-ydegr
keep<- (1:length(y))[ !is.na( apply( cbind(X,y),1,mean) ) ]
X<-X[keep,] ; y<-y[keep]
ranks<-match(y,sort(unique(y))) ; uranks<-sort(unique(ranks))
n<-dim(X)[1] ; p<-dim(X)[2]
iXX<-solve(t(X)%*%X)  ; V<-iXX*(n/(n+1)) ; cholV<-chol(V)

#### Ordinal probit regression

## setup
#set.seed(1)
beta<-rep(0,p) 
z<-qnorm(rank(y,ties.method="random")/(n+1))
g<-rep(NA,length(uranks)-1)
K<-length(uranks)
BETA<-matrix(NA,1000,p) ; Z<-matrix(NA,1000,n) ; ac<-0
mu<-rep(0,K-1) ; sigma<-rep(100,K-1) 

## MCMC
S<-25000
for(s in 1:S) 
{
  #update g 
  for(k in 1:(K-1)) 
  {
    a<-max(z[y==k])
    b<-min(z[y==k+1])
    u<-runif(1, pnorm( (a-mu[k])/sigma[k] ),
             pnorm( (b-mu[k])/sigma[k] ) )
    g[k]<- mu[k] + sigma[k]*qnorm(u)
  }
  #update beta
  E<- V%*%( t(X)%*%z )
  beta<- cholV%*%rnorm(p) + E
  #update z
  ez<-X%*%beta
  a<-c(-Inf,g)[ match( y-1, 0:K) ]
  b<-c(g,Inf)[y]  
  u<-runif(n, pnorm(a-ez),pnorm(b-ez) )
  z<- ez + qnorm(u)
  
  
  #help mixing
  c<-rnorm(1,0,n^(-1/3))  
  zp<-z+c ; gp<-g+c
  lhr<-  sum(dnorm(zp,ez,1,log=T) - dnorm(z,ez,1,log=T) ) + 
    sum(dnorm(gp,mu,sigma,log=T) - dnorm(g,mu,sigma,log=T) )
  if(log(runif(1))<lhr) { z<-zp ; g<-gp ; ac<-ac+1 }
  
  if(s%%(S/1000)==0) 
  { 
    cat(s/S,ac/s,"\n")
    BETA[s/(S/1000),]<-  beta
    Z[s/(S/1000),]<- z
  }
} 

par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))
par(mfrow=c(1,2))
plot(X[,1]+.25*(X[,2]),Z[1000,],
     pch=15+X[,2],col=c("gray","black")[X[,2]+1],
     xlab="number of children",ylab="z", ylim=range(c(-2.5,4,Z[1000,])),
     xlim=c(0,9))

beta.pm<-apply(BETA,2,mean)
ZPM<-apply(Z,2,mean)
abline(0,beta.pm[1],lwd=2 ,col="gray")
abline(beta.pm[2],beta.pm[1]+beta.pm[3],col="black",lwd=2 )
legend(5,4,legend=c("PDEG=0","PDEG=1"),pch=c(15,16),col=c("gray","black"))


# The lines suggest that for people whose parents did not go to college,
# the number of children they have is indeed weakly negatively associated 
# with their educational outcome. 
# However, the opposite seems to be true among people whose parents 
# went to college. 

plot(density(BETA[,3],adj=2),lwd=2,xlim=c(-.5,.5),main="",
     xlab=expression(beta[3]),ylab="density")
sd<-sqrt(  solve(t(X)%*%X/n)[3,3] )
x<-seq(-.7,.7,length=100)
lines(x,dnorm(x,0,sd),lwd=2,col="gray")
legend(-.5,6.5,legend=c("prior","posterior"),lwd=c(2,2),col=c("gray","black"),bty="n")


# Example 2: applying to graduate school. 

# Ordinal logistic regression

library(foreign)

dat <- read.dta("https://stats.idre.ucla.edu/stat/data/ologit.dta")
head(dat)

# This dataset has a three level variable called apply, with levels "unlikely", "somewhat likely", and "very likely", 
# coded 1, 2, and 3, respectively, that we will use as our outcome variable. We also have three variables that we will 
# use as predictors: pared, which is a 0/1 variable indicating whether at least one parent has a graduate degree; 
# public, which is a 0/1 variable where 1 indicates that the undergraduate institution is public and 0 private, 
# and gpa, which is the student's grade point average.

# Descriptive stats
lapply(dat[, c("apply", "pared", "public")], table)

# three way cross tabs (xtabs) and flatten the table
ftable(xtabs(~ public + apply + pared, data = dat))

summary(dat$gpa)

# We will use the polr function in R
# model is:

# logit(P(Y <= j)) = bj0 - b1*x1 - ... - bp*xp

library(MASS)
m <- polr(apply ~ pared + public + gpa, data = dat, Hess=TRUE)
summary(m)

# Coefficients:
#           Value Std. Error t value
# pared   1.04769     0.2658  3.9418
# public -0.05879     0.2979 -0.1974
# gpa     0.61594     0.2606  2.3632
# 
# Intercepts:
#                              Value   Std. Error t value
# unlikely|somewhat likely     2.2039  0.7795     2.8272
# somewhat likely|very likely  4.2994  0.8043     5.3453
# 
# Residual Deviance: 717.0249 
# AIC: 727.0249 

# Estimated model:
# logit(P(Y <= 1)) = 2.20 - 1.05*PARED - (-0.06)*PUBLIC - 0.616*GPA
# logit(P(Y <= 2)) = 4.30 - 1.05*PARED - (-0.06)*PUBLIC - 0.616*GPA

# Profile CI
(ci <- confint(m))
# when using the Profile Likelihood Based Confidence Interval, 
# two points on either side of MLE are chosen such that likelihood 
# at those two points is equal to (maximum likelihood – ½ * (1-alpha) 
# percentile of the chi-square distribution with df = 1).


# CIs assuming normality
confint.default(m)

# Interpretation:

# for pared, we would say that for a one unit increase in pared (i.e., going from 0 to 1), 
# we expect a 1.05 increase in the expected value of apply on the log odds scale, 
# given all of the other variables in the model are held constant.

# For gpa, we would say that for a one unit increase in gpa, 
# we would expect a 0.62 increase in the expected value of apply in the log odds scale, 
# given that all of the other variables in the model are held constant.

# odds ratios
exp(coef(m))

# OR and CI
exp(cbind(OR = coef(m), ci))

# Interpretation:

# Parental education:
# For students whose parents did attend college, the odds of being more likely 
# (i.e., very or somewhat likely versus unlikely) to apply is 2.85 times that 
# of students whose parents did not go to college, holding constant all other variables.

# School type:
# For students in private school, the odds of being more likely to apply is 1.06 times 
# [i.e., 1/0.943] that of public school students, holding constant all other variables (positive odds ratio).

# GPA:
# For every one unit increase in student's GPA the odds of being more likely to apply 
# (very or somewhat likely versus unlikely) is multiplied 1.85 times (i.e., increases 85%), 
# holding constant all other variables.


# Now Bayesian:
install.packages("rstanarm")
library(rstanarm)

post0 <- stan_polr(apply ~ pared + public + gpa, data = dat,
                   prior = R2(0.25), prior_counts = dirichlet(1),
                   seed = 12345)
print(post0, digits = 2)