# Example 1:

### MH algorithm for one-sample normal problem with 

## Setup
s2 = 1 
t2<-10 ; mu<-5


## MCMC
s2<-1 ; t2<-10 ; mu<-5 
x<-c(9.37, 10.18, 9.16, 11.60, 10.33)

theta<-0 ; delta<-2 ; S<-10000 ; THETA<-NULL 
#set.seed(1)

for(s in 1:S)
{
  
  theta.star<-rnorm(1,theta,sqrt(delta))
  
  log.r<-( sum(dnorm(x,theta.star,sqrt(s2),log=TRUE)) +
             dnorm(theta.star,mu,sqrt(t2),log=TRUE) )  -
    ( sum(dnorm(x,theta,sqrt(s2),log=TRUE)) +
        dnorm(theta,mu,sqrt(t2),log=TRUE) ) 
  
  if(log(runif(1))<log.r) { theta<-theta.star }
  
  THETA<-c(THETA,theta)
  
}

par(mar=c(3,3,1,1),mgp=c(1.75,.75,0))
par(mfrow=c(1,2))


ts.plot(THETA,type="l",xlab="iteration",ylab=expression(theta))

hist(THETA[-c(1:50)],prob=TRUE,main="",xlab=expression(theta),ylab="density")
th<-seq(min(THETA),max(THETA),length=100)
lines(th,dnorm(th,mu.n,sqrt(t2.n)), col = "red" )


### MH algorithm with different proposal distributions

ACR = NULL #stores the acceptance rates
ACF<-NULL
THETAA<-NULL
for(delta2 in 2^c(-5,-1,1,5,7) ) {
  set.seed(1)
  THETA<-NULL
  S<-10000
  theta<-0
  acs<-0
  delta<-2
  
  for(s in 1:S) 
  {
    
    theta.star<-rnorm(1,theta,sqrt(delta2))
    log.r<-sum( dnorm(x,theta.star,sqrt(s2),log=TRUE)-
                  dnorm(x,theta,sqrt(s2),log=TRUE)  )  +
      dnorm(theta.star,mu,sqrt(t2),log=TRUE)-dnorm(theta,mu,sqrt(t2),log=TRUE) 
    
    if(log(runif(1))<log.r)  { theta<-theta.star ; acs<-acs+1 }
    THETA<-c(THETA,theta) 
    
  }
  #plot(THETA[1:1000])
  
  ACR<-c(ACR,acs/s) 
  ACF<-c(ACF,acf(THETA,plot=FALSE)$acf[2]  )
  THETAA<-cbind(THETAA,THETA)
}


par(mfrow=c(1,3),mar=c(2.75,2.75,.5,.5),mgp=c(1.7,.7,0))
laby<-c(expression(theta),"","","","")

for(k in c(1,3,5)) {
  plot(THETAA[1:500,k],type="l",xlab="iteration",ylab=laby[k], 
       ylim=range(THETAA) )
  abline(h=mu.n,lty=2)
}


# Example 2:
data(Default, package="ISLR")

# install.packages("tidyverse")
library(tidyverse)

# For the computations we need the factors converted to numerical variables
Def2 <- Default %>%
mutate(default = as.integer(as.character(default) == "Yes")) %>%
mutate(student = as.integer(as.character(student) == "Yes"))

# Now all factors are 0s and 1s:
head(Def2)

prior <- function(betas){
return(prod(sapply(betas, dnorm, mean=0, sd=10)))
 }

likelihood <- function(X, y, betas){
 e <- exp(X %*% betas)
 px <- e/(1+e)
 return(px^y * ((1-px)^(1-y)))
 }

# For stability, we use log transformations
log_posterior <- function(X, y, betas)
  {
    log_prior <- log(prior(betas))
    log_likelihoods = log(likelihood(X, y, betas))
    return(log_prior + sum(log_likelihoods))
   }

MCMC <- function(X, y, n, beta_start=c(0,0,0,0), jump_dist_sd=0.1){
  B <- ncol(X) # number of betas (coefficients)
   beta <- matrix(nrow=n, ncol=B)
   beta[1,] <- beta_start
   for(i in 2:n){
    current_betas <- beta[i-1,]
     new_betas <- current_betas + rnorm(B, mean=0, sd=jump_dist_sd)
     for(j in 1:B)
      {
      test_betas <- current_betas
       test_betas[j] <- new_betas[j]
       rr <- log_posterior(X, y, test_betas) - log_posterior(X, y, current_betas)
       if(log(runif(1)) < rr){
        beta[i,j] <- new_betas[j]
         } else beta[i,j] <- current_betas[j]
       }
     }
   return(beta)
   }

# set.seed(1000)
burn_in_length <- 5000
N = 10000 # total number of points

 Def2 <- Def2 %>% mutate(balance=balance/1000) %>% mutate(income=income/1000)
 y <- Def2$default
 X <- cbind(1, as.matrix(Def2[,2:4])) # pad with a column of ones for intercept

# Run the MH with jump distribution sd set at 0.05: 
beta = MCMC(X, y, n=N, jump_dist_sd = 0.05)

 
MCMC_results <- apply(beta, MARGIN=2, function(x){c(mean(x), quantile(x, c(0.025, 0.5, 0.975)))})
colnames(MCMC_results) <- c("Intercept", "student", "balance", "income")
round(MCMC_results, 4)
 
ts.plot(beta[,2])
ts.plot(beta[,3])
ts.plot(beta[,4])

glm_model <- glm(default ~ student + balance + income, data=Def2, family=binomial(link="logit"))
summary(glm_model) 