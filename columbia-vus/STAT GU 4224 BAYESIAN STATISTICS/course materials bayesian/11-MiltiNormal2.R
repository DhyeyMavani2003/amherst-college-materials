# Data from the textbook of pretest and posttest score
x = structure(c(59, 43, 34, 32, 42, 38, 55, 67, 64, 45, 49, 72, 34, 
            70, 34, 50, 41, 52, 60, 34, 28, 35, 77, 39, 46, 26, 38, 43, 68, 
            86, 77, 60, 50, 59, 38, 48, 55, 58, 54, 60, 75, 47, 48, 33), .Dim = c(22L, 
                                                                                  2L), .Dimnames = list(NULL, c("pretest", "posttest")))
mu0<-c(50,50) # by design of the exam
L0<-matrix( c(625,312.5,312.5,625),nrow=2,ncol=2)

nu0<-4
S0<-matrix( c(625,312.5,312.5,625),nrow=2,ncol=2)

n<-dim(x)[1]
xbar = apply(x, 2, mean)
Sigma<-cov(x) ; THETA<-SIGMA<-NULL


library(MASS) # for the mvrnorm function
# install.packages("MCMCpack")
library(MCMCpack) # for the riwish function

for(s in 1:20000) 
{
  
  ###update theta
  Ln<-solve( solve(L0) + n*solve(Sigma) )
  mun<-Ln%*%( solve(L0)%*%mu0 + n*solve(Sigma)%*% xbar )
  theta = mvrnorm(1,mun,Ln)  
  
  
  ###update Sigma
  Sn<- S0 + ( t(x)-c(theta) )%*%t( t(x)-c(theta) ) 
  Sigma<-riwish(nu0+n, Sn)
  # Sigma<-solve( rwish(1, nu0+n, solve(Sn)) )
  
  # Update the output
  THETA = rbind(THETA,theta)
  SIGMA = rbind(SIGMA,c(Sigma)) # notice the Sigma matrix is vectorized
}


quantile(   THETA[,2]-THETA[,1], prob=c(.025,.5,.975) )
mean( THETA[,2]-THETA[,1])
mean( THETA[,2]>THETA[,1]) 
hist(THETA[,2])
hist(THETA[,1], add = T, col = "red")

bivn.kde <- kde2d(THETA[,1], THETA[,2], n = 200) 
contour(bivn.kde, nlevels = 50)

apply(THETA, 2, mean) # posterior mean of theta

cov(THETA) # covariance matrix of the posterior distribution of theta
# Compare to covariance of prior distribution
L0

#install.packages("emdbook")
library(emdbook)
library(coda)
HPDregionplot(mcmc(THETA), add = T, col = "red")
abline(a=0,b=1, col="red")