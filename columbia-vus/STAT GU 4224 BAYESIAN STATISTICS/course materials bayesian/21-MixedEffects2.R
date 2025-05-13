install.packages("faraway")

# Data on the brightness of paper which may vary between operators of the 
# production machinery.
data(pulp, package="faraway")
summary(pulp)

library(ggplot2)
ggplot(pulp, aes(x=operator, y=bright))+geom_point(position = position_jitter(width=0.1, height=0.0))

# Research questions:
# Is there a difference between operators in general?
# How much is the difference between operators in general?
# How does the variation between operators compare to the variation within operators?
# What is the difference between these four operators?

# Linear model with fixed effects

amod = aov(bright ~ operator, pulp)
anova(amod)
# We find a statistically significant difference.
# We can estimate the coefficients:
coef(amod)

# The treatment coding sets operator a as the reference level. 
# We can also test for a difference between pairs of operators:
TukeyHSD(amod)
# Only the d to b difference is found significant.
# We can't answer questions 2 and 3 with this analysis


# Fit a random effect model
# $$ y_{ij} = \mu + \alpha_i + \epsilon_{ij}, i=1,... ,a, j=1,... ,n_i, $$ 
library(lme4)
mmod <- lmer(bright ~ 1+(1|operator), pulp)
faraway::sumary(mmod)
# There is a bit less variation between operators vs. within (0.26 vs. 0.33)

# We must use the ML method for hypothesis testing:
smod <- lmer(bright ~ 1+(1|operator), pulp, REML = FALSE)
faraway::sumary(smod)

# Compare to the model without operators:
nullmod <- lm(bright ~ 1, pulp)
lrtstat <- as.numeric(2*(logLik(smod)-logLik(nullmod)))
pvalue <- pchisq(lrtstat,1,lower=FALSE)
data.frame(lrtstat, pvalue)
# the p-value greater than 0.05 suggests no strong evidence against that 
# hypothesis that there is no variation among the operators. 
# this is doubtful because of the chi-square approximation

# Individual estimates:
ranef(mmod)$operator
# CI
dd = as.data.frame(ranef(mmod))
ggplot(dd, aes(y=grp,x=condval)) +
  geom_point() +
  geom_errorbarh(aes(xmin=condval -2*condsd,
                     xmax=condval +2*condsd), height=0)

# Finally with stan
library(rstan)
rstan_options(auto_write = TRUE)
options(mc.cores = parallel::detectCores())

write("
data {
  int<lower=0> N; // sample size
  int<lower=0> J; // number of groups
  int<lower=1,upper=J> predictor[N]; // group indeces
  vector[N] response; // y variable
}
parameters {
  vector[J] eta;
  real mu;
  real<lower=0> sigmaalpha;
  real<lower=0> sigmaepsilon;
}
transformed parameters {
  vector[J] a;
  vector[N] yhat;

  a = mu + sigmaalpha * eta;

  for (i in 1:N)
    yhat[i] = a[predictor[i]];
}
model {
  eta ~ normal(0, 1);

  response ~ normal(yhat, sigmaepsilon);
}
", "Example7.stan")

pulpdat <- list(N=nrow(pulp),
                J=length(unique(pulp$operator)),
                response=pulp$bright,
                predictor=as.numeric(pulp$operator))
mod1 <- stan_model("Example7.stan")
system.time(fit <- sampling(mod1, data=pulpdat))

# there are warning messages: increase number of iterations
system.time(fit <- sampling(mod1, data=pulpdat, iter=100000))
# The same underlying problems remain but the inference will now be more reliable.

# Diagnostics
pname <- "mu"
muc <- rstan::extract(fit, pars=pname,  permuted=FALSE, inc_warmup=FALSE)
mdf <- reshape2::melt(muc)
mdf |> dplyr::filter(iterations %% 100 == 0) |> 
  ggplot(aes(x=iterations,y=value,color=chains)) + geom_line() + ylab(mdf$parameters[1])
# using the native R pipe |>

# We see the traces of the four chains overlaid in different colors. 
# The chains appear roughly stationary although there are some occasional 
# larger excursions (which is why we needed more iterations).

pname <- "sigmaalpha"
muc <- rstan::extract(fit, pars=pname,  permuted=FALSE, inc_warmup=FALSE)
mdf <- reshape2::melt(muc)
mdf |> dplyr::filter(iterations %% 100 == 0) |> 
  ggplot(aes(x=iterations,y=value,color=chains)) + 
  geom_line() + ylab(mdf$parameters[1])

pname <- "sigmaepsilon"
muc <- rstan::extract(fit, pars=pname,  permuted=FALSE, inc_warmup=FALSE)
mdf <- reshape2::melt(muc)
mdf |> dplyr::filter(iterations %% 100 == 0) |> 
  ggplot(aes(x=iterations,y=value,color=chains)) + 
  geom_line() + ylab(mdf$parameters[1])

print(fit, pars=c("mu","sigmaalpha","sigmaepsilon","a"))

(get_posterior_mean(fit, pars=c("mu","sigmaalpha","sigmaepsilon","a")))

# Posterior distributions
postsig <- rstan::extract(fit, pars=c("sigmaalpha","sigmaepsilon"))
ref <- reshape2::melt(postsig,value.name="bright")
ggplot(ref,aes(x=bright, color=L1))+
  geom_density()+
  xlim(0,2) +
  guides(color=guide_legend(title="SD"))
# We see that the error SD can be localized much more than the operator SD. 

opre <- rstan::extract(fit, pars="a")
ref <- reshape2::melt(opre, value.name="bright")
ref[,2] <- (LETTERS[1:4])[ref[,2]]
ggplot(data=ref,aes(x=bright, color=Var2))+geom_density()+guides(color=guide_legend(title="operator"))

# BRMS
install.packages("brms")
library(brms)
suppressMessages(bmod <- brm(bright ~ 1+(1|operator), pulp))
plot(bmod)
stancode(bmod)
# We see that brms is using student t distributions with 3 degrees of freedom 
# for the priors. For the two error SDs, this will be truncated at zero 
# to form half-t distributions. 
# This explains why we encountered fewer problems in the fit because we are supplying more informative priors.

summary(bmod)

# Example 2:
# In Davison and Hinkley, 1997, the results of a study on Nitrofen, 
# a herbicide, are reported. Due to concern regarding the effect on 
# animal life, 50 female water fleas were divided into five groups of 
# ten each and treated with different concentrations of the herbicide. 
# The number of offspring in three subsequent broods for each flea was recorded. 
data(nitrofen, package="boot")
head(nitrofen)

# We need to rearrange the data to have one response value per line:
lnitrofen = data.frame(conc = rep(nitrofen$conc,each=3),
                       live = as.numeric(t(as.matrix(nitrofen[,2:4]))),
                       id = rep(1:50,each=3),
                       brood = rep(1:3,50))
head(lnitrofen)

lnitrofen$jconc <- lnitrofen$conc + rep(c(-10,0,10),50)
lnitrofen$fbrood = factor(lnitrofen$brood)
ggplot(lnitrofen, aes(x=jconc,y=live, shape=fbrood, color=fbrood)) + 
  geom_point(position = position_jitter(w = 0, h = 0.5)) + 
  xlab("Concentration") + labs(shape = "Brood")

# Since the response is a small count, a Poisson model is a natural choice.
# We fit a model using penalized quasi-likelihood (PQL) using the lme4 package:
glmod <- glmer(live ~ I(conc/300)*fbrood + (1|id), nAGQ=25, 
              family=poisson, data=lnitrofen)
summary(glmod, correlation = FALSE)
# We scaled the concentration by dividing by 300 (the maximum value is 310) to avoid scaling problems encountered with glmer(). 
# The first brood is the reference level so the slope for this group is 
# estimated as -0.0437 and is not statistically significant, confirming the impression from the plot.

# We can see that numbers of offspring in the second and third broods start out significantly higher 
# for zero concentration of the herbicide, with estimates of 1.1688 and 1.3512.
# But as concentration increases, we see that the numbers decrease significantly

predf = data.frame(conc=rep(c(0,80,160,235,310),each=3),fbrood=rep(1:3,5))
predf$fbrood = factor(predf$fbrood)
predf$live = predict(glmod, newdata=predf, re.form=~0, type="response")
ggplot(predf, aes(x=conc,y=live,group=fbrood,color=fbrood)) + 
  geom_line() + xlab("Concentration")
# We see that if only the first brood were considered, the herbicide does not have a large effect. In the second and third broods, the (negative) effect of the herbicide becomes more apparent with fewer live offspring being produced as the concentration rises.

bmod <- brm(live ~ I(conc/300)*fbrood + (1|id), 
            family=poisson, 
            data=lnitrofen, 
            refresh=0, silent=2, cores=4)
plot(bmod)
summary(bmod)


# Example 3(textbook)
#### Tumor location example
load("tumorLocation.RData") 
Y = tumorLocation 

# Number of tumors per location for each mouse and the average
xs = seq(.05,1,by=.05) 
m<-nrow(Y) 

par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))
par(mfrow=c(1,2))
plot(c(0,1),range(Y),type="n",xlab="location",ylab="number of tumors")

for(j in 1:m) { lines(xs,Y[j,],col="gray") } # individual mice
lines( xs,apply(Y,2,mean),lwd=3) # average

lya<-log(apply(Y,2,mean))

X<-cbind( rep(1,ncol(Y)),poly(xs,deg=4,raw=TRUE))

fit2<- lm(lya~-1+X[,1:3] )
fit3<- lm(lya~-1+X[,1:4] )
fit4<- lm(lya~-1+X[,1:5] )

yh2<-X[,1:3]%*%fit2$coef
yh3<-X[,1:4]%*%fit3$coef
yh4<-X[,1:5]%*%fit4$coef

plot(xs,lya,type="l",lwd=3,xlab="location",ylab="log average number of tumors",
     ylim=range(c(lya,yh2,yh3,yh4)) )

points(xs,yh2,pch="2",col="black")
lines(xs,yh2,col="gray")
points(xs,yh3,pch="3",col="black")
lines(xs,yh3,col="gray")
points(xs,yh4,pch="4",col="black")
lines(xs,yh4,col="gray")

#### MCMC  

## mvnorm log density
ldmvnorm<-function(X,mu,Sigma,iSigma=solve(Sigma),dSigma=det(Sigma))
{
  Y<-t( t(X)-mu)
  sum(diag(-.5*t(Y)%*%Y%*%iSigma))  -
    .5*(  prod(dim(X))*log(2*pi) +     dim(X)[1]*log(dSigma) )
}

m<-nrow(Y) ; p<-ncol(X)


## priors
BETA<-NULL
for(j in 1:m)
{
  BETA<-rbind(BETA,lm(log(Y[j,]+1/20)~-1+X)$coef) 
}

mu0<-apply(BETA,2,mean) 
S0<-cov(BETA) ; eta0<-p+2
iL0<-iSigma<-solve(S0)
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

## MCMC
THETA.post<<-SIGMA.post<-NULL ; set.seed(1)
for(s in 1:50000) 
{
  
  ##update theta
  Lm<-solve( iL0 +  m*iSigma )
  mum<-Lm%*%( iL0%*%mu0 + iSigma%*%apply(BETA,2,sum) )
  theta<-t(rmvnorm(1,mum,Lm))
  
  ##update Sigma
  mtheta<-matrix(theta,m,p,byrow=TRUE)
  iSigma<-rwish(1,eta0+m, 
                solve( S0+t(BETA-mtheta)%*%(BETA-mtheta)) )
  
  ##update beta
  Sigma<-solve(iSigma) ; dSigma<-det(Sigma)
  for(j in 1:m)
  {
    beta.p<-t(rmvnorm(1,BETA[j,],.5*Sigma))
    
    lr<-sum( dpois(Y[j,],exp(X%*%beta.p),log=TRUE ) -
               dpois(Y[j,],exp(X%*%BETA[j,]),log=TRUE ) ) +
      ldmvnorm( t(beta.p),theta,Sigma,
                iSigma=iSigma,dSigma=dSigma ) -
      ldmvnorm( t(BETA[j,]),theta,Sigma,
                iSigma=iSigma,dSigma=dSigma )
    
    if( log(runif(1))<lr ) { BETA[j,]<-beta.p }
  }
  
  ##store some output
  if(s%%10==0)
  {  
    # cat(s,"\n") 
    THETA.post<-rbind(THETA.post,t(theta)) 
    SIGMA.post<-rbind(SIGMA.post,c(Sigma)) 
  }
  
}


## MCMC diagnostics
library(coda)
round(apply(THETA.post,2,effectiveSize),2)

#### Different posterior regions 
eXB.post<-NULL
for(s in 1:dim(THETA.post)[1])
{
  beta<-rmvnorm(1,THETA.post[s,],matrix(SIGMA.post[s,],p,p))
  eXB.post<-rbind(eXB.post,t(exp(X%*%t(beta) )) )
}

qEB<-apply( eXB.post,2,quantile,probs=c(.025,.5,.975))

eXT.post<- exp(t(X%*%t(THETA.post )) )
qET<-apply( eXT.post,2,quantile,probs=c(.025,.5,.975))
yXT.pp<-matrix( rpois(prod(dim(eXB.post)),eXB.post),
                dim(eXB.post)[1],dim(eXB.post)[2] )

qYP<-apply( yXT.pp,2,quantile,probs=c(.025,.5,.975))

par(mar=c(2.75,2.75,.5,.5),mgp=c(1.7,.7,0))
par(mfrow=c(1,3))

plot( c(0,1),range(c(0,qET,qEB,qYP)),type="n",xlab="location",
      ylab="number of tumors")
lines(xs, qET[1,],col="black",lwd=1)
lines(xs, qET[2,],col="black",lwd=2)
lines(xs, qET[3,],col="black",lwd=1)

plot( c(0,1),range(c(0,qET,qEB,qYP)),type="n",xlab="location",
      ylab="")
lines(xs, qEB[1,],col="black",lwd=1)
lines(xs, qEB[2,],col="black",lwd=2)
lines(xs, qEB[3,],col="black",lwd=1)

plot( c(0,1),range(c(0,qET,qEB,qYP)),type="n",xlab="location",
      ylab="")
lines(xs, qYP[1,],col="black",lwd=1)
lines(xs, qYP[2,],col="black",lwd=2)
lines(xs, qYP[3,],col="black",lwd=1)
