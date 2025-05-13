a = 2 ; b = 1 # prior parameters
n1 = 111 ; sx1 = 217 # data in group 1 
n2 = 44 ; sx2 = 66 # data in group 2

# Plots of histogram, overlayed with true density and estimated density
S = 50
theta = rgamma(S ,a+sx2 ,b+n2) 

par(mfrow = c(1,2))
hist(theta, freq = F, ylim = c(0,2.5), main = "")
title(paste("Simulated size S = ", S))

p_grid = seq(1, 2, len = 100)
posterior2 = dgamma(p_grid, a+ sx2, b+n2)
lines(p_grid, posterior2, col = "red")
lines(density(theta), col = "blue")
legend(1.57, 2.51, legend=c("Estimated", "True"), col=c("blue", "red"), lty=c(1,1), cex=0.6)

S = 500
theta = rgamma(S ,a+sx2 ,b+n2) 
hist(theta, freq = F, ylim = c(0,2.5), main = paste("Simulated size S = ", S))

p_grid = seq(1, 2, len = 100)
posterior2 = dgamma(p_grid, a+ sx2, b+n2)
lines(p_grid, posterior2, col = "red")
lines(density(theta), col = "blue")
legend(1.6, 2.51, legend=c("Estimated", "True"), col=c("blue", "red"), lty=c(1,1), cex=0.6)


# Compute posterior quantities

# Mean
# Exact value:
(a+ sx2)/(b+n2)
S = 500
theta = rgamma(S ,a+sx2 ,b+n2) 
par(mfrow=c(1,3))
plot(1, theta[1], xlim=c(1,S), ylim=c(0,2),xlab = "Sample size S", main = "E(theta|x)")
for (i in 2:S) lines(c(i-1, i), c(mean(theta[1:(i-1)]), mean(theta[1:(i)])))
abline(h =(a+ sx2)/(b+n2), col = "red" )

# Posterior probability
# exact value:
pgamma(1.46,a+sx2 ,b+n2)
S = 500
theta = rgamma(S ,a+sx2 ,b+n2) 
plot(1, (theta[1] < 1.46)/1, xlim=c(1,S), ylim = c(0,2),xlab = "Sample size S", main = "P(theta < 1.46|x)")
for (i in 2:S) lines(c(i-1, i), c(mean(theta[1:(i-1)] < 1.46), mean(theta[1:(i)]<1.46)))
abline(h =pgamma(1.46,a+sx2 ,b+n2), col = "red" )

# Posterior median
# Exact value = ?
theta = rgamma(S ,a+sx2 ,b+n2) 
plot(1, median(theta[1]), xlim=c(1,S), ylim = c(0,2),xlab = "Sample size S", main = "median(theta|x)")
for (i in 2:S) lines(c(i-1, i), c(median(theta[1:(i-1)]), median(theta[1:(i)])))
par(mfrow=c(1,1))


# Logit
a = 1 ; b = 1
theta.prior.mc = rbeta(10000, a , b)
gamma.prior.mc = log ( theta.prior.mc/(1 - theta.prior.mc) )
plot(density(gamma.prior.mc), xlim = c(-4,4), ylim = c(0,6))

n0 =860- 441 ; n1 =441
theta.post.mc = rbeta(10000 , a+n1 , b+n0 )
gamma.post.mc = log ( theta.post.mc/(1 -theta.post.mc) )
lines(density(gamma.post.mc), col = "red") # Estimated posterior logit


# Pposterior predictive distributions:
a = 2 ; b = 1 # prior parameters
n1 = 111 ; sx1 = 217 # data in group 1 
n2 = 44 ; sx2 = 66 # data in group 2
x = 0:15 # any reasonable upper bound, like 15

# 1) Direct computation:
ans = 0
for (i in 0:15) for (j in ((i+1):15)) 
  ans = ans+ dnbinom(j, size=(a+sx1), mu=(a+sx1)/(b+n1))*dnbinom(i, size=(a+sx2), mu=(a+sx2)/(b+n2))
ans

# 2) Sampling from the posterior predictive distributions directly:

xn1=rnbinom(10000, size=(a+sx1), mu=(a+sx1)/(b+n1))
xn2=rnbinom(10000, size=(a+sx2), mu=(a+sx2)/(b+n2))
sum(xn1 > xn2)/10000

# 3) Sampling from the posterior predictive distributions via theta:
theta1.mc = rgamma(10000,a+sx1, b+n1)
theta2.mc = rgamma(10000,a+sx2, b+n2)
x1.mc = rpois (10000 , theta1.mc)
x2.mc = rpois (10000 , theta2.mc)
mean(x1.mc >x2.mc)


# Monte Carlo integration:
# Compute the integral of (cos(50*x) + sin(20*x))^2 between 0 and 1

g <- function(x){
  (cos(50*x) + sin(20*x))^2
}

x = seq(0,1, len = 1000)
plot(x, g(x), type = "l")
integrate(g, 0, 1)

n.sam <- 1000
# step 1: generate n i.i.d samples from f (in this case uniform(0,1))
x.sam <- runif(n.sam)

# compute the MC approximation
(theta.mc <- sum(sapply(x.sam, g))/n.sam)

# Exact answer:
(8240 + 280*cos(30) - 120*cos(70) - 105*sin(40) + 42*sin(100))/8400

# Try a different density f(x)
n.sam <- 10000
# step 1: generate n i.i.d samples from f (in this case uniform(0,1))
x.sam <- rbeta(n.sam, 9, 7)

# compute the MC approximation
(theta.mc = mean(g(x.sam)/dbeta(x.sam, 9, 7)))
# Very unstable!