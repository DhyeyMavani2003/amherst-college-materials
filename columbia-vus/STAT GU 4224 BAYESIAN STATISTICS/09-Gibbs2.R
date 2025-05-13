# Finding the stationary distribution of a transition matrix

# Brute force:

install.packages("expm")
library(expm) # used to compute matrix power

# Define the transition matrix 
(P = matrix(c(c(0.4,0.5,0.1), c(0.3,0.4,0.3), c(0.2,0.3,0.5)), nrow = 3, byrow = T))

# Check sum across = 1
apply(P,1,sum)  

# Solution
(pi_bru = (P %^% 100)[1,])

# Check:
pi_bru - pi_bru%*%P

# Eigen decomposition
library(MASS) # For the ginv function

# Get the eigenvectors of P, note: R returns right eigenvectors
r=eigen(P)
(rvec=r$vectors)

# left eigenvectors are the inverse of the right eigenvectors
(lvec=ginv(r$vectors))
# The eigenvalues
(lam<-r$values)
# Two ways of checking the spectral decomposition:
## Standard definition
rvec%*%diag(lam)%*%ginv(rvec)

## With left eigenvectors 
rvec%*%diag(lam)%*%lvec

# Normalize the solution
pi_eig<-lvec[1,]/sum(lvec[1,])
pi_eig

#check
sum(pi_eig)
pi_eig %*% P

# Alternative: we can also obtain the left eigenvectors as the transposes of the right eigenvectors of t(P)
r<-eigen(t(P))
V<-r$vectors
lam<-r$values
V%*%diag(lam)%*%ginv(V)

# Rate of convergence:
lam[2]

# Linear equation method
K = 3
(A = rbind(t(P-diag(rep(1, K)))[1:(K-1),], rep(1, K)))
solve (A, c(rep(0,K-1),1))


# Mixture model:
mu1 = -2
mu2 = 2
s1 = 0.6
s2 = 0.6
pd = c(0.5, 0.5)

# iid Monte Carlo sample
S = 10000
d = rbinom(S, 1, pd[1])
x = 1:S
for (i in 1:S) if (d[i] == 0) x[i] = rnorm(1, mu1, s1) else x[i] = rnorm(1, mu2, s2)
hist(x, freq = F, ylim = c(0,0.4))
grid = seq(-5, 5, len = 1000)
lines(grid, pd[1]*dnorm(grid, mu1, s1) + pd[2]*dnorm(grid, mu2, s2), lw = 2)


# Gibbs
d = 1:S
xGibbs = 1:S
for (i in 2:S)
{
  if (d[i-1] == 0) xGibbs[i] = rnorm(1, mu1, s1) else xGibbs[i] = rnorm(1, mu2, s2)
  d[i] = rbinom(1, 1, 1-pd[1]*dnorm(xGibbs[i], mu1, s1)/(pd[1]*dnorm(xGibbs[i], mu1, s1) + pd[2]*dnorm(xGibbs[i], mu2, s2)))
}
hist(xGibbs, freq = F, ylim = c(0,0.4))

lines(grid, pd[1]*dnorm(grid, mu1, s1) + pd[2]*dnorm(grid, mu2, s2), lw = 2)
table(d)
par(mfrow = c(1,2))
ts.plot(xGibbs)
ts.plot(x)

acf(xGibbs)
acf(x)

install.packages("coda")
library(coda)
effectiveSize(xGibbs)
effectiveSize(x)