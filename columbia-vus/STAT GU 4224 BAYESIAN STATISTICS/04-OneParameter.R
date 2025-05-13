# Define a grid of 50 points between 0 & 1
n_grid = 50
p_grid = seq(from=0 , to=1 , length.out= n_grid )

# define the uniform prior on the grid
prior = rep( 1 , n_grid)

par(mfrow=c(1,3))
plot(p_grid , prior, type = "l", xlab="chance of winning" , ylab="prior probability" )


# From lecture notes we have w = 6 winnings out of n = 9 plays
likelihood = dbinom(6, size = 9, prob = p_grid) # computes the likelihood at each point in the grid
plot(p_grid , likelihood , type = "l", xlab="chance of winning" , ylab="likelihood" )

# compute product of likelihood and prior
unstd.posterior <- likelihood * prior

# normalize the posterior, so it sums to 1
posterior <- unstd.posterior / sum(unstd.posterior)


plot(p_grid , posterior , type = "l", xlab="chance of winning" , ylab="posterior probability" )
mtext( "50 point grid" )


# Plotting exact posterior with different priors and sample sizes
n = 5
x = 1
a = 1
b = 1

prior = dbeta(p_grid, a, b)
posterior = dbeta(p_grid, a+x, b+n-x)
plot(p_grid , prior, type = "l", xlab="chance of winning" , ylab="prior probability" , ylim =c(0, 3))
lines(p_grid, posterior, col = "red")

a = 5
b = 9

prior = dbeta(p_grid, a, b)
posterior = dbeta(p_grid, a+x, b+n-x)
plot(p_grid , prior, type = "l", xlab="chance of winning" , ylab="prior probability" , ylim =c(0, 4))
lines(p_grid, posterior, col = "red")


n = 100
x = 20
a = 1
b = 1

prior = dbeta(p_grid, a, b)
posterior = dbeta(p_grid, a+x, b+n-x)
plot(p_grid , prior, type = "l", xlab="chance of winning" , ylab="prior probability" , ylim =c(0, 10))
lines(p_grid, posterior, col = "red")

par(mfrow=c(1,1))
# HPDI example
install.packages("HDInterval")
library(HDInterval)
hdi(qbeta, 0.95, shape1 =2, shape2 = 5)

# Notice this function is very flexible and also works on a sample from the posterior:
tst <- rbeta(1e5, 2, 5)
hdi(tst)