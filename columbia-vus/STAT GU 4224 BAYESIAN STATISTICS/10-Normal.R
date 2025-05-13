# Density curves of various univariate normal distributions:
x = seq(-5, 5, len= 1000)
plot(x, dnorm(x), type = "l", ylim = c(0, 0.6), ylab = "Normal Density")
lines(x, dnorm(x, m = -1), col = "red")
lines(x, dnorm(x, m = 1), col = "green")
lines(x, dnorm(x, sd = 1.5), col = "blue")
lines(x, dnorm(x, sd = 0.75), col = "gray54")

legend(-4.5, 0.55, legend=c("N(0,1)", "N(-1,1)", "N(1,1)", 
                           parse(text=paste("N(0, 1.5^2)")), parse(text=paste("N(0, 0.75^2)"))), 
       col=c("black", "red", "green", "blue", "gray54"), lty = 1, cex=0.8)


# Density curves of various chi-square distributions:
x <- seq(0, 75, len= 500)
plot(x, dchisq(x, 4), type = "l", ylim = c(0, 0.2), ylab = "Chi-square Densities")
lines(x, dchisq(x, 10), col = "purple")
lines(x, dchisq(x, 20), col = "green")
lines(x, dchisq(x, 30), col = "blue")
lines(x, dchisq(x, 50), col = "gray54")

legend(50, 0.17, legend=c("df = 4", "df = 10", "df = 20", "df = 30", "df = 50"), 
       col=c("black", "purple", "green", "blue", "gray54"), lty = 1, cex=0.8)


# Density curves of various log-normal distributions:
x = seq(0, 9, len = 1000) # grid on the horizontal line
plot(x, dlnorm(x, 0, 1), type = "l", ylim = c(0, 0.9), ylab = "F densities")
lines(x, dlnorm(x, 1, 0.5), col = "purple")
lines(x, dlnorm(x, 0, 20), col = "red")

legend(6, 0.8, legend=c("mu = 0, sigma = 1", "mu = 1, sigma = 0.5", "mu = 0, sigma = 20"), 
       col=c("black", "purple", "red"), lty = 1, cex=0.8)


# Bivariate normal
# Create the grid first
x <- seq(-3, 3, len = 100)
y <- seq(-3, 3, len = 100)

# Standard bivariate normal
z1 <- (1/(2*pi))*exp(-outer(x^2, y^2, "+")/2) # outer function avoids writing loops

# Or use library mnormt
install.packages("mnormt")
mu = c(0, 0)
sigma = diag(rep(1, 2))
library(mnormt)
f     <- function(x, y) dmnorm(cbind(x, y), mu, sigma)
z     <- outer(x, y, f)

p = par() # save graph parameters
par(pty = "s") # Make plot area square
contour(x,y,z1)
contour(x, y, z) # Same as above
image(x,y,z1)
persp(x, y, z1, theta = 30, phi = 30, expand = 0.5, col = "lightblue", ticktype='detailed')
# theta, phi: Define the angle of the viewing direction.

# Or with package rgl
install.packages("rgl")
library("rgl")
persp3d(x,y,z1)

# Any bivariate normal (notice it is slower because of the two loops)
x <- seq(-8, 8, len = 100)
y <- seq(-4, 4, len = 100)

# S is the covariance matrix. 
# Make sure it has a positive determinant and positive diagonal elements!
# In general, it needs to be positive definite.
S = matrix(c(11, 4, 4, 2), 2, 2)
d = det(S)

z2 <- matrix(rep(0, 100^2), 100, 100) # Output matrix
for (i in 1:100)
  for (j in 1:100)
    z2[i, j] <- (1/(2*pi*sqrt(d)))*exp(-(t(c(x[i], y[j]))%*%solve(S)%*%c(x[i], y[j]))/2)

contour(x,y,z2)
persp(x,y,z2, theta = 10, phi = 30, expand = 0.5, col = "lightblue")


library(MASS)
# Simulate bivariate normal data
mu <- c(0,0)  # Mean
(Sigma <- S)  # Covariance matrix
contour(x,y,z2)

# Generate sample from N(mu, Sigma)
bivn <- mvrnorm(500, mu = mu, Sigma = Sigma )  # from Mass package
head(bivn)
points(bivn, col = "red", cex = 0.5)

# Calculate kernel density estimate
bivn.kde <- kde2d(bivn[,1], bivn[,2], n = 50) 
image(bivn.kde)       # from base graphics package
contour(bivn.kde, add = T)     # from base graphics package
contour(x,y,z2, add = T, col = "gray")

# Notice each marginal distribution is normal (as expected by the theory):
hist(bivn[,1])
hist(bivn[,2])


install.packages("rWishart")
library(rWishart)
rWishart(2, 25, diag(1, 20))
rSingularWishart(2, 5, diag(1, 20)) # n < p