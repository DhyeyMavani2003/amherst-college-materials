a = 2 ; b = 1 # prior parameters
n1 = 111 ; sx1 = 217 # data in group 1 
n2 = 44 ; sx2 = 66 # data in group 2

# Posterior mean for Group 1
(a+sx1)/(b+n1) 

# Posterior mean for Group 2
(a+sx2)/(b+n2)
 
# Are these significantly different?

# posterior 95% CI for mean of Group 1
qgamma( c(.025 ,.975) ,a+sy1 ,b+n1)

# posterior 95% CI for mean of Group 1
qgamma( c(.025 ,.975) ,a+sy2 ,b+n2) 
# HPDI 

library(HDInterval)
hdi(qgamma, 0.95, shape =a+ sx1, rate = b+n1)
hdi(qgamma, 0.95, shape =a+ sx2, rate = b+n2)

# There is a small overlap.
# In general, not a good idea to check if the individual intervals overlap!

# Plot of the posteriors and prior
n_grid = 100
p_grid = seq(from=0 , to= 5 , length.out= n_grid )

prior = dgamma(p_grid, a, b)
posterior1 = dgamma(p_grid, a+ sx1, b+n1)
posterior2 = dgamma(p_grid, a+ sx2, b+n2)
plot(p_grid , prior, type = "l", xlab="theta" , ylab="" , ylim =c(0, 3), lty = 2)
lines(p_grid, posterior1, col = "black")
lines(p_grid, posterior2, col = "red")

legend(3, 2.5, legend=c("Posterior Group 1", "Posterior Group 2", "Prior"),
       col=c("black", "red", "black"), lty=c(1,1,2), cex=0.8)

# Find the posterior probability that theta1 > theta2

# Simulate from the posterior distribution:
R = 10000
px1 = rgamma(R, a+ sx1, b+n1)
px2 = rgamma(R, a+ sx2, b+n2)
sum(px1 > px2)/R
# That is, posterior probability that theta1 > theta2 is around 0.97
# This means this is almost certain.

# Exercise (difficult!): Find the posterior probability that theta1 > theta2 without simulation.
# Hint: You will need to use the Beta distribution cdf.

# Posterior predictive distributions:
x = 0:10
xn1=dnbinom(x, size=(a+sx1), mu=(a+sx1)/(b+n1))
xn2=dnbinom(x, size=(a+sx2), mu=(a+sx2)/(b+n2))

# Define transparent colors for overlapping the pmf graphs
blue = rgb(0, 0, 1, alpha=0.5)
red = rgb(1, 0, 0, alpha=0.5)

barplot(xn2, col = blue, names.arg = x)
barplot(xn1, add = T, col = red)
legend(6, 0.3, legend=c("Predictive Group 2", "Predictive Group 1"),
       col=c(blue, red), lty=c(1,1), cex=0.8)


# How sensitive is the result to the prior?
a = 18 ; b = 10 # prior parameters
hdi(qgamma, 0.95, shape =a+ sx1, rate = b+n1)
hdi(qgamma, 0.95, shape =a+ sx2, rate = b+n2)
prior = dgamma(p_grid, a, b)
posterior1 = dgamma(p_grid, a+ sx1, b+n1)
posterior2 = dgamma(p_grid, a+ sx2, b+n2)
plot(p_grid , prior, type = "l", xlab="theta" , ylab="" , ylim =c(0, 3), lty = 2)
lines(p_grid, posterior1, col = "black")
lines(p_grid, posterior2, col = "red")

legend(3, 2.5, legend=c("Posterior Group 1", "Posterior Group 2", "Prior"),
       col=c("black", "red", "black"), lty=c(1,1,2), cex=0.8)
px1 = rgamma(R, a+ sx1, b+n1)
px2 = rgamma(R, a+ sx2, b+n2)
sum(px1 > px2)/R
# Almost the same result.