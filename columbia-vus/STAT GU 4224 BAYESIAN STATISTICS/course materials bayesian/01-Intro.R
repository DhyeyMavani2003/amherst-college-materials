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
# We will learn how to estimate any posterior later.