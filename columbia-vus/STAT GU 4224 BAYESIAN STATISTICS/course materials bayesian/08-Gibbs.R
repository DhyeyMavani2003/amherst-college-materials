# Gibbs sampler example

library(MASS)
# Means and standard deviations of the bivariate normal data
m1 <- 1; m2 <- 1
s1 <- 1
s2 <- 22

# Correlation between the two components
r <- 0.9

# Covariance matrix
s <- matrix(c(s1^2, r*s1*s2, r*s1*s2, s2^2),2,2)
m <- c(m1,m2)

# Generate draw from the bivariate normal
z <- mvrnorm(1000,m,s)
plot(z,col="grey",pch=20)

# Gibbs sampler to simulate this data
iter <- 1000
x <- 1:iter
y <- 1:iter
x[1] <- -1
for (i in 1:iter)
{
  y[i] <- rnorm(1, m2+s2*r*(x[i]-m1)/s1, sqrt((1-r^2)*s2^2))
  if (i < iter) x[i+1] <- rnorm(1, m1+s1*r*(y[i]-m2)/s2, sqrt((1-r^2)*s1^2))
}

# Compare with package generator:
plot(z,col="grey",pch=20, main = "r = 0.9")
points(x,y,col="red", pch=20)

ts.plot(x)

# Now change r to 0.999 and repeat!


# Heights example

x = c(169.6,166.8,157.1,181.1,158.4,165.6,166.7,156.5,168.1,165.3)

(x.bar = mean(x))
n = length(x)

prior.mean = 165
prior.var = 2^2

nu0 = 1
sigma.sq0 = 0.01



# Create double grid first
d = 1000
mu = seq(160, 171, len = d)
sigma2 = seq(10, 150, len = d)

# Joint posterior
library(invgamma)
post = matrix(0, d, d)

for (i in 1:d) for (j in 1:1000)
post[i,j] = prod(dnorm(x, mu[i], sqrt(sigma2[j])))*dinvgamma(sigma2[j], nu0/2, nu0*sigma.sq0/2)*dnorm(mu[i], prior.mean, prior.var)

post = post/sum(post)

contour(mu, sigma2, post)


# Gibbs sampling
S = 10000
mu = 1:S
s2 = 1:S
var.x = var(x)
for (i in 2:S)
{
  mun = (prior.mean/prior.var + n*x.bar*s2[i-1])/(1/prior.var + n*s2[i-1]) 
  t2n = 1/(1/prior.var + n*s2[i-1])
  mu[i] = rnorm(1, mun, sqrt(t2n))
  nun = nu0+n
  s2n = (nu0*sigma.sq0 + (n-1)*var.x + n*(x.bar - mu[i])^2 ) /nun
  s2[i] = rinvgamma(1, (nun)/2, (nun)*s2n/2)
}

plot(mu[2:S], s2[2:S])

par(mfrow = c(1,2))
# Marginal posterior of sigma^2 
hist(s2[2:S])

# Marginal posterior of mu 
hist(mu[2:S])

# Posterior 95% CI for mu
quantile(mu,c(.025,.975))

# Posterior 95% HDI for sigma2

library(HDInterval)
hdi(s2)
# Compare to quantile
quantile(s2,c(.025,.975))
# Very different!