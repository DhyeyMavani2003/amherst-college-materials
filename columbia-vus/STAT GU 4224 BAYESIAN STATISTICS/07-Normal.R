# Heights example

x = c(169.6,166.8,157.1,181.1,158.4,165.6,166.7,156.5,168.1,165.3)
sigma.sq = 50

(x.bar = mean(x))
n = length(x)

prior.mean = 165
prior.var = 2^2

(post.var = 1/(1/prior.var + n/sigma.sq))
(post.mean = post.var*(prior.mean/prior.var + sum(x)/sigma.sq))


grid = seq(155, 180, len = 100)
plot(grid, dnorm(grid, prior.mean, sqrt(prior.var)), type = "l", col = "blue", ylim = c(0, 0.3),
     xlab = "x", ylab = "Densities")

lines(grid, dnorm(grid, post.mean, sqrt(post.var)), col = "red")
legend(171, 0.25, legend=c("Prior", "Posterior"),
       col=c("blue", "red"), lty=1, cex=0.8)

# What to do when sigma is not known and we want a prior on it?

k0 =1
nu0 = 1
sigma.sq0 = 50
(post.mean = (k0*prior.mean + n*x.bar)/(k0+n))
(post.var = (nu0*sigma.sq0 + (n-1)*var(x) + k0*n*(x.bar-prior.mean)^2/(k0+n))/(nu0+n))

kn = k0 + n

# Plot the posterior distributions
library(invgamma)
s = seq(0, 150, len = 1000)
plot(s, dinvgamma(s, (nu0+n)/2, (nu0 + n)*post.var/2), type = "l", xlab = "sigma^2")

# Create double grid first
mu = seq(150, 181, len = 1000)

# Joint posterior
post = matrix(0, 1000, 1000)
for (i in 1:1000) for (j in 1:1000)
post[i,j] <- dnorm(mu[i], post.mean, sqrt(s[j]/k0))* dinvgamma(s[j], (nu0+n)/2, (nu0 + n)*post.var/2)
contour(mu, s, post)
image(mu, s, post)
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

# Marginal posterior of sigma^2 (IG)
hist(s2)

# Marginal posterior of mu (see exercise)
hist(mu)

# Posterior 95% CI for mu
quantile(mu,c(.025,.975))