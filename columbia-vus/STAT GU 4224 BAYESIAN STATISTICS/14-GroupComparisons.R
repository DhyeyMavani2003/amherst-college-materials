# Example 1 (RStan):

write(
"data {
  int n;
  real y[n];
}

parameters {
  real mu;
  real<lower=0> sigma;
}

model {
  for (i in 1:n)
   y[i] ~ normal(mu,sigma);
  mu ~ normal(1.7,0.3);
  sigma ~ cauchy(0,1);
}",
"Example1.stan")

library("rstan")
# options(mc.cores=4)
options(mc.cores = parallel::detectCores())

# Simulating some data
n     = 100
y     = rnorm(n, 1.6, 0.2)

# Running stan code
model = stan_model("Example1.stan")

# The following draws samples from the model defined above
fit = sampling(model,list(n = n,y = y), iter= 1000, chains=4)
# Note the data must be a named list

print(fit)

params = extract(fit)

par(mfrow=c(1,2))
ts.plot(params$mu,xlab="Iterations",ylab="mu")
hist(params$sigma,main="",xlab="sigma")
par(mfrow = c(1,1))
rm(list = ls()) # clear the environment because next model also calls data x

# Heights example

x = c(169.6,166.8,157.1,181.1,158.4,165.6,166.7,156.5,168.1,165.3)

prior.mean = 165
(x.bar = mean(x))
n = length(x)

k0 =1
nu0 = 1
sigma.sq0 = 50
(post.mean = (k0*prior.mean + n*x.bar)/(k0+n))
(post.var = (nu0*sigma.sq0 + (n-1)*var(x) + k0*n*(x.bar-prior.mean)^2/(k0+n))/(nu0+n))

kn = k0 + n

# Plot the posterior distributions
library(invgamma)
s = seq(0, 150, len = 1000)
# plot(s, dinvgamma(s, (nu0+n)/2, (nu0 + n)*post.var/2), type = "l", xlab = "sigma^2")

# Create double grid first
mu = seq(150, 181, len = 1000)

# Joint posterior
post = matrix(0, 1000, 1000)
for (i in 1:1000) for (j in 1:1000)
  post[i,j] <- dnorm(mu[i], post.mean, sqrt(s[j]/k0))* dinvgamma(s[j], (nu0+n)/2, (nu0 + n)*post.var/2)
contour(mu, s, post)

# image(mu, s, post)
# persp(mu, s, post, theta = 60, phi = 30, expand = 0.5, col = "lightblue")

# Monte Carlo sampling
S = 10000
mu = 1:S
s2 = 1:S
for (i in 1:S)
{
  s2[i] = rinvgamma(1, (nu0+n)/2, (nu0 + n)*post.var/2)
  mu[i] = rnorm(1, post.mean, sqrt(s2[i]/k0))
}

points(mu, s2, col = alpha("red", 0.3), cex = 0.5)

# Marginal posterior of mu (see exercise)
hist(mu)

# posterior means:
mean(mu)
mean(s2)

# Posterior 95% CI 
quantile(mu,c(.025,.975))
quantile(s2,c(.025,.975))

# Now try with rstan:
write(
  "data {
  int n;
  real x[n];
}

parameters {
  real mu;
  real<lower=0> sigma2;
}

model {
  for (i in 1:n)
   x[i] ~ normal(mu, sqrt(sigma2));
  mu ~ normal(165, sqrt(sigma2));
  sigma2 ~ inv_gamma(0.5, 25);
}",
"Example2.stan")

model = stan_model("Example2.stan")
fit = sampling(model,list(n=n,x = x), iter= 5000, chains=4)
print(fit)
params = extract(fit)
hist(params$mu)
hist(params$sigma2)
quantile(params$mu, c(0.025, 0.975))

# Example 3: school data
load("nels.RData") 
x1 <-y.school1
x2 <-y.school2

par(mfrow=c(1,2),mar=c(3,3,1,1),mgp=c(1.75,.75,0))
boxplot(list(x1, x2),range=0,ylab="score",names=c("school 1","school 2"))

n1<-length(x1)
n2<-length(x2)
mean(x1)
mean(x2)
sd(c(x1,x2))
s2p<- ( var(x1)*(n1-1) + var(x2)*(n2-1) )/(n1+n2-2 )
(tstat<- ( mean(x1)-mean(x2) ) /sqrt( s2p*(1/length(x1)+1/length(x2))))

# With built-in function:
t.test(x1, x2, var.equal = T)
# Not assuming equal population variances
t.test(x1,x2)


ts<-seq(-4,4,length=100)
plot(ts,dt(ts,n1+n2-1),type="l",xlab=expression(italic(t)),ylab="density")
abline(v=tstat,lwd=2,col="gray")
# Frequentist approach: Since the p-value = 0.08363 we don't reject H0


# Gibbs sampler:
## data 
n1<-length(x1) ; n2<-length(x2)

## prior parameters
mu0<-50 # tests are designed to have mean 50 score
g02<-625
del0<-0 ; t02<-625
s20<-100 # tests are designed to have st. dev. 10
nu0<-1

## starting values
mu<- ( mean(x1) + mean(x2) )/2
del<- ( mean(x1) - mean(x2) )/2

## Gibbs sampler
MU<-DEL<-S2<-NULL
X12<-NULL
#set.seed(1)
for(s in 1:5000) 
{
  
  ##update s2
  s2<-1/rgamma(1,(nu0+n1+n2)/2, 
               (nu0*s20+sum((x1-mu-del)^2)+sum((x2-mu+del)^2) )/2)
  ##
  
  ##update mu
  var.mu<-  1/(1/g02+ (n1+n2)/s2 )
  mean.mu<- var.mu*( mu0/g02 + sum(x1-del)/s2 + sum(x2+del)/s2 )
  mu<-rnorm(1,mean.mu,sqrt(var.mu))
  ##
  
  ##update del
  var.del<-  1/(1/t02+ (n1+n2)/s2 )
  mean.del<- var.del*( del0/t02 + sum(x1-mu)/s2 - sum(x2-mu)/s2 )
  del<-rnorm(1,mean.del,sqrt(var.del))
  ##
  
  ##save parameter values
  MU<-c(MU,mu) ; DEL<-c(DEL,del) ; S2<-c(S2,s2) 
  X12<-rbind(X12,c(rnorm(2,mu+c(1,-1)*del,sqrt(s2) ) ) )
}                 
plot(MU+DEL,MU-DEL)
cor(MU+DEL,MU-DEL)
par(mfrow=c(1,2),mar=c(3,3,1,1),mgp=c(1.75,.75,0))

plot( density(MU,adj=2),xlim=c(mu0-sqrt(g02),mu0+sqrt(g02)), 
      main="",xlab=expression(mu),ylab="density",lwd=2 )
ds<-seq(mu0-sqrt(g02),mu0+sqrt(g02),length=100)
lines(ds,dnorm(ds,mu0,sqrt(g02)),lwd=2,col="gray" )
legend(22,.27,legend=c("prior","posterior"),lwd=c(2,2),col=c("black","gray"),
       bty="n")

plot( density(DEL,adj=2),xlim=c(-sqrt(t02),sqrt(t02)),
      main="",xlab=expression(delta),ylab="density",lwd=2 )
ds<-seq(-sqrt(t02),sqrt(t02),length=100)
lines(ds,dnorm(ds,0,sqrt(t02)),lwd=2,col="gray" )
legend(-28,.27,legend=c("prior","posterior"),lwd=c(2,2),col=c("black","gray"),
       bty="n")

quantile(DEL,c(.025,.5,.975))
quantile(DEL*2,c(.025,.5,.975))
mean(DEL>0)
mean(X12[,1]>X12[,2])

# Exercise: Write the above model in RStan and repeat the computations.