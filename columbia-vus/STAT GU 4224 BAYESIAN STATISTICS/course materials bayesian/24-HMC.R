# Recall the Gibbs sampler example

library(MASS)
# Means and standard deviations of the bivariate normal data
m1 <- 1; m2 <- 1
s1 <- 1
s2 <- 2

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
acf(x)


library(coda)
effectiveSize(x)


# MH version
library(MASS)
library(mvtnorm)
x <- 1:iter
y <- 1:iter
x[1] = 1
y[1] = 1
jump_dist_sd = 0.2
sp=diag(c(jump_dist_sd^2, jump_dist_sd^2))
# sp = s

for (i in 1:(iter-1))
{
  new <- mvrnorm(1, mu=c(x[i], y[i]), Sigma = sp)
  r = min(1, dmvnorm(new, mean = m, sigma = s)*dmvnorm(c(x[i], y[i]), mean = new, sigma = sp)/(dmvnorm(c(x[i], y[i]), mean = m, sigma = s)*dmvnorm(new, mean =c(x[i], y[i]), sigma = sp)))
  if (runif(1) < r) {x[i+1] <- new[1]; y[i+1] <- new[2]} else {x[i+1] <- x[i]; y[i+1] <- y[i]} 
}



plot(z,col="grey",pch=20, main = "r = 0.9")
points(x,y,col="red", pch=20)
ts.plot(x)
acf(x)
effectiveSize(x)

# Hamiltonian
e = 0.07
jump_dist_sd = 0.9
for (i in 1:(iter-1))
{
  new <- mvrnorm(1, mu=c(x[i], y[i]), Sigma = sp)
  new = t(new - e*solve(s) %*% (c(x[i], y[i])-m))
  r = min(1, dmvnorm(new, mean = m, sigma = s)*dmvnorm(c(x[i], y[i]), mean = new, sigma = sp)/(dmvnorm(c(x[i], y[i]), mean = m, sigma = s)*dmvnorm(new, mean =c(x[i], y[i]), sigma = sp)))
  if (runif(1) < r) {x[i+1] <- new[1]; y[i+1] <- new[2]} else {x[i+1] <- x[i]; y[i+1] <- y[i]} 
}
plot(z,col="grey",pch=20, main = "r = 0.9")
points(x,y,col="red", pch=20)
ts.plot(x)
acf(x)
effectiveSize(x)

