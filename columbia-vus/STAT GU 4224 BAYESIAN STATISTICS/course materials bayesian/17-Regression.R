# Example from textbook:
par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))

x1<-c(0,0,0,0,0,0,1,1,1,1,1,1)
x2<-c(23,22,22,25,27,20,31,23,27,28,22,24)
y<-c(-0.87,-10.74,-3.27,-1.97,7.50,-7.25,17.05,4.96,10.40,11.05,0.26,2.51)

par(mfrow=c(1,1))
plot(y~x2,pch=16,xlab="age",ylab="change in maximal oxygen uptake", 
     col=c("black","gray")[x1+1])
legend(27,0,legend=c("aerobic","running"),pch=c(16,16),col=c("gray","black"))


# Model with 
# b2 = group difference
# b3 = age effect
# b4 = interaction
par(mfrow=c(2,2),mar=c(3,3,1,1),mgp=c(1.75,.75,0),oma=c(0,0,.25,0))


plot(y~x2,pch=16,col=c("black","gray")[x1+1],ylab="change in maximal oxygen uptake",xlab="",xaxt="n")
abline(h=mean(y[x1==0]),col="black") 
abline(h=mean(y[x1==1]),col="gray")
mtext(side=3,expression(paste(beta[3]==0,"  ",beta[4]==0)) )

plot(y~x2,pch=16,col=c("black","gray")[x1+1],xlab="",ylab="",xaxt="n",yaxt="n")
abline(lm(y~x2),col="black")
abline(lm((y+.5)~x2),col="gray")
mtext(side=3,expression(paste(beta[2]==0,"  ",beta[4]==0)) )

plot(y~x2,pch=16,col=c("black","gray")[x1+1],
     xlab="age",ylab="change in maximal oxygen uptake" )
fit<-lm( y~x1+x2)
abline(a=fit$coef[1],b=fit$coef[3],col="black")
abline(a=fit$coef[1]+fit$coef[2],b=fit$coef[3],col="gray")
mtext(side=3,expression(beta[4]==0)) 

plot(y~x2,pch=16,col=c("black","gray")[x1+1],
     xlab="age",ylab="",yaxt="n", main = "With Interaction")
abline(lm(y[x1==0]~x2[x1==0]),col="black")
abline(lm(y[x1==1]~x2[x1==1]),col="gray")

# OLS
n<-length(y)
X<-cbind(rep(1,n),x1,x2,x1*x2)
p<-dim(X)[2]
(beta.ols<- solve(t(X)%*%X)%*%t(X)%*%y)

# Bayesian estimation via MCMC
n<-length(y)
X <- cbind(rep(1,n),x1,x2,x1*x2)
p<-dim(X)[2]

fit.ls<-lm(y~-1+ X)
beta.0 = rep(0,p) # prior coefficients b0

(Sigma.0= diag(c(150,30,6,5)^2,p)) # prior cov matrix for betas
nu.0<-1 ; sigma2.0<- 15^2

beta.0<-fit.ls$coef
nu.0<-1  ; sigma2.0<-sum(fit.ls$res^2)/(n-p)
Sigma.0<- solve(t(X)%*%X)*sigma2.0*n


S<-5000

rmvnorm<-function(n,mu,Sigma) 
{ # samples from the multivariate normal distribution
  E<-matrix(rnorm(n*length(mu)),n,length(mu))
  t(  t(E%*%chol(Sigma)) +c(mu))
}

## some convenient quantities
n<-length(y)
p<-length(beta.0)
iSigma.0<-solve(Sigma.0)
XtX<-t(X)%*%X

## store mcmc samples in these objects
beta.post<-matrix(nrow=S,ncol=p)
sigma2.post<-rep(NA,S)

## starting value
#set.seed(1)
sigma2<- var( residuals(lm(y~0+X)) )

## MCMC algorithm
for( scan in 1:S) {
  
  #update beta
  V.beta<- solve(  iSigma.0 + XtX/sigma2 )
  E.beta<- V.beta%*%( iSigma.0%*%beta.0 + t(X)%*%y/sigma2 )
  beta<-t(rmvnorm(1, E.beta,V.beta) )
  
  #update sigma2
  nu.n<- nu.0+n
  ss.n<-nu.0*sigma2.0 + sum(  (y-X%*%beta)^2 )
  sigma2<-1/rgamma(1,nu.n/2, ss.n/2)
  
  #save results of this scan
  beta.post[scan,]<-beta
  sigma2.post[scan]<-sigma2
}

round( apply(beta.post,2,mean), 3)
# Compare to OLS:
beta.ols


#### g-prior: see http://www2.stat.duke.edu/~pdh10/FCBS/Replication/chapter9.R


# With Rstan:
library(rstan)
write(
  "data {
  int N; // number of observations
  // response
  vector[N] y;
  // number of columns in the design matrix X
  int K;
  // design matrix X
  // should not include an intercept
  matrix [N, K] X;
  real scale_alpha; // prior sd on alpha
  vector[K] scale_beta; // prior sds on betas
  real loc_sigma;
}

parameters {
 real alpha; // intercept
  vector[K] beta;  // regression coefficients beta vector
  real sigma;
}

// this is a convenient way to utilize the fact the mean of Y depends on x
transformed parameters {
  vector[N] mu; // defines the mean of each Y
  mu = alpha + X * beta;  //notice * is vector multiplication
}

model {
  // priors
  alpha ~ normal(0, scale_alpha);
  beta ~ normal(0, scale_beta); // notice the beta priors are independent
  // to model correlated betas you can use lkj_corr prior
  sigma ~ exponential(loc_sigma); // this does not match the textbook
 
  y ~ normal(mu, sigma); // likelihood
}
", "Example4.stan")

mod = stan_model("Example4.stan")

mod_data = list(
  X = X[,-1],
  K = ncol(X)-1,
  N = length(x1),
  y = y
)

mod_data$scale_alpha = 150 # see p. 156 for explanations
mod_data$scale_beta <- c(30, 6, 5)
mod_data$loc_sigma <- sd(y)

mod_fit = sampling(mod, data = mod_data, iter = 3000)

summary(mod_fit, pars = c("alpha", "beta", "sigma"), probs = c(0.025, 0.975))$summary
# Compare to book:
round( apply(beta.post,2,mean), 3)


# Stan example:
data("Duncan", package = "carData")
head(Duncan)
# OLS:
duncan_lm <- lm(prestige ~ type + income + education, data = Duncan)

# Another stan example:
write(
  "data {
  // number of observations
  int N;
  // response
  vector[N] y;
  // number of columns in the design matrix X
  int K;
  // design matrix X
  // should not include an intercept
  matrix [N, K] X;
  // priors on alpha
  real scale_alpha;
  vector[K] scale_beta;
  real loc_sigma;
  // keep responses
  int use_y_rep;
  int use_log_lik;
}
parameters {
  // regression coefficient vector
  real alpha;
  vector[K] beta;
  real sigma;
}
transformed parameters {
  vector[N] mu;

  mu = alpha + X * beta;
}
model {
  // priors
  alpha ~ normal(0., scale_alpha);
  beta ~ normal(0., scale_beta);
  sigma ~ exponential(loc_sigma);
  // likelihood
  y ~ normal(mu, sigma);
}
generated quantities {
  // simulate data from the posterior
  vector[N * use_y_rep] y_rep;
  // log-likelihood posterior
  vector[N * use_log_lik] log_lik;
  for (i in 1:num_elements(y_rep)) {
    y_rep[i] = normal_rng(mu[i], sigma);
  }
  for (i in 1:num_elements(log_lik)) {
    log_lik[i] = normal_lpdf(y[i] | mu[i], sigma);
  }
}
", "Example5.stan")

mod1 <- stan_model("Example5.stan")

library(tidyverse)
library("recipes")
rec <- recipe(prestige ~ income + education + type, data = Duncan) %>%
  step_dummy(type) %>%
  prep(data = Duncan, retain = TRUE) 
X <- juice(rec, all_predictors(), composition = "matrix")
y <- drop(juice(rec, all_outcomes(), composition = "matrix"))

mod1_data <- list(
  X = X,
  K = ncol(X),
  N = nrow(X),
  y = y,
  use_y_rep = TRUE,
  use_log_lik = FALSE
)

mod1_data$scale_alpha <- sd(y) * 10
mod1_data$scale_beta <- apply(X, 2, sd) * sd(y) * 2.5
mod1_data$loc_sigma <- sd(y)
mod1_fit <- sampling(mod1, data = mod1_data)

summary(mod1_fit, pars = c("alpha", "beta", "sigma"), probs = c(0.025, 0.975))$summary

# Generated quantities
d=mod1_fit@sim$samples[[1]]
hist(d$'y_rep[1]')


#### Diabetes example
load("diabetes.RData")
yf<-diabetes$y
yf<-(yf-mean(yf))/sd(yf)

Xf<-diabetes$X
Xf<-t( (t(Xf)-apply(Xf,2,mean))/apply(Xf,2,sd))

## set up training and test data
n<-length(yf)
#set.seed(1)

i.te<-sample(1:n,100)
i.tr<-(1:n)[-i.te]

y<-yf[i.tr] ; y.te<-yf[i.te]
X<-Xf[i.tr,]; X.te<-Xf[i.te,]

par(mfrow=c(1,3),mar=c(2.75,2.75,.5,.5),mgp=c(1.5,.5,0))
olsfit<-lm(y~-1+X) # no need to include intercept when data are standardized
y.te.ols<-X.te%*%olsfit$coef
plot(y.te,y.te.ols,xlab=expression(italic(y)[test]),
     ylab=expression(hat(italic(y))[test])) ; abline(0,1)
mean( (y.te-y.te.ols )^2 )
plot(olsfit$coef,type="h",lwd=2,xlab="regressor index",ylab=expression(hat(beta)[ols]))

## backwards selection
bselect<-function(y,X,p=0.05) 
{
  Xc<-X 
  remain<-1:dim(Xc)[2]
  removed<-NULL
  
  fit<-lm(y~-1+Xc)   
  pv<-summary(fit)$coef[,4]
  
  while(any(pv>p) & length(remain)> 0) {
    jpmax<-which.max(pv)
    Xc<-Xc[,-jpmax,drop=FALSE]
    removed<-c(removed,remain[jpmax])
    remain<-remain[-jpmax] 
    if(length(remain)>0 ) {fit<-lm(y~-1+Xc)   ; pv<-summary(fit)$coef[,4] }
    
  }  
  list(remain=remain,removed=removed) 
} 


bselect.tcrit<-function(y,X,tcrit=qnorm(.975))
{
  Xc<-X
  remain<-1:dim(Xc)[2]
  s2<-removed<-NULL
  
  fit<-lm(y~-1+Xc)
  ts<-summary(fit)$coef[,3]
  s2<-summary(fit)$sigma^2
  while(any(abs(ts)<tcrit) & length(remain)> 0) {
    jpmax<-which.min(abs(ts))
    Xc<-Xc[,-jpmax,drop=FALSE]
    removed<-c(removed,remain[jpmax])
    remain<-remain[-jpmax]
    if(length(remain)>0 ) {fit<-lm(y~-1+Xc)   ; ts<-summary(fit)$coef[,3] ;
    s2<-c(s2,summary(fit)$sigma^2) }
    
  }
  list(remain=remain,removed=removed)
}



(vars<-bselect.tcrit(y,X,tcrit=1.65))
bslfit<-lm(y~-1+X[,vars$remain])
y.te.bsl<-X.te[,vars$remain]%*%bslfit$coef
mean( (y.te-y.te.bsl)^2)
plot(y.te,y.te.bsl,ylim=range( c(y.te.bsl,y.te.ols)),
     xlab=expression(italic(y)[test]),ylab=expression(hat(italic(y))[test]))
abline(0,1)


#### Bayesian model selection
p<-dim(X)[2]
S<-10000
source("regression_gprior.R")

## Don't run it again if you've already run it
runmcmc<-!any(system("ls",intern=TRUE)=="diabetesBMA.RData")
if(!runmcmc){ load("diabetesBMA.RData") }

if(runmcmc){
  
  BETA<-Z<-matrix(NA,S,p)
  z<-rep(1,dim(X)[2] )
  lpy.c<-lpy.X(y,X[,z==1,drop=FALSE])
  for(s in 1:S)
  {
    for(j in sample(1:p))
    {
      zp<-z ; zp[j]<-1-zp[j]
      lpy.p<-lpy.X(y,X[,zp==1,drop=FALSE])
      r<- (lpy.p - lpy.c)*(-1)^(zp[j]==0)
      z[j]<-rbinom(1,1,1/(1+exp(-r)))
      if(z[j]==zp[j]) {lpy.c<-lpy.p}
    }
    
    beta<-z
    if(sum(z)>0){beta[z==1]<-lm.gprior(y,X[,z==1,drop=FALSE],S=1)$beta }
    Z[s,]<-z
    BETA[s,]<-beta
    if(s%%10==0)
    { 
      bpm<-apply(BETA[1:s,],2,mean) ; plot(bpm)
      cat(s,mean(z), mean( (y.te-X.te%*%bpm)^2),"\n")
      Zcp<- apply(Z[1:s,,drop=FALSE],2,cumsum)/(1:s)
      plot(c(1,s),range(Zcp),type="n") ; apply(Zcp,2,lines)
    }
  } 
  save(BETA,Z,file="diabetesBMA.RData")
}

par(mar=c(2.75,2.75,.5,.5),mgp=c(1.7,.7,0))

beta.bma<-apply(BETA,2,mean,na.rm=TRUE)
y.te.bma<-X.te%*%beta.bma
mean( (y.te-y.te.bma)^2)

layout( matrix(c(1,1,2),nrow=1,ncol=3) )

plot(apply(Z,2,mean,na.rm=TRUE),xlab="regressor index",ylab=expression(
  paste( "Pr(",italic(z[j] == 1),"|",italic(y),",X)",sep="")),type="h",lwd=2)

plot(y.te,y.te.bma,xlab=expression(italic(y)[test]),
     ylab=expression(hat(italic(y))[test])) ; abline(0,1)