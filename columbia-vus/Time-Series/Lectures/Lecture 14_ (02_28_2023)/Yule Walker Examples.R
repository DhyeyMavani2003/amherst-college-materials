#################################################################################
# AR sim
#################################################################################
my_AR_sim <- function(n=189, ar_coeff=c(.5),sigma=1) {
  n.start <- n + floor(.2/(1-.2)*n)
  burnin <- floor(.20*n.start)
  model_params <- list(ar = ar_coeff)
  ts_sim <- arima.sim(model = model_params, n = n.start,sd=sigma)
  ts_sim <- ts_sim[(burnin+1):n.start]
  return(ts_sim)
}

#################################################################################
## Yule Walker Fun
# function that computes Yule Walker  estimates of AR(p) coefs
#################################################################################
my_yule_walker <- function(ts_data,AR_order=2) {
  gamma_h <- c(acf(ts_data,plot=F,lag.max=AR_order)$acf) # compute gamma_h vec for h=AR_order=p
  gamma_h_index <- cbind(seq(0,length(gamma_h)-1),gamma_h) # combine gamma_h with lag index
  Gamma <- matrix(NA,nrow=length(gamma_h)-1,ncol=length(gamma_h)-1) # create empty Gamma matrix
  # loop to create Gamma matrix
  for (i in 1:(length(gamma_h)-1)) {
    for (j in 1:(length(gamma_h)-1)) {
      h <- abs(i-j) # calculate lag
      select_acvf <- gamma_h_index[which(gamma_h_index[,1]==h),2] # extract element of gamma_h with lag h
      Gamma[i,j] <- c(select_acvf) # store element
    }
  }
  # Yule Walker 
  return(c(solve(Gamma)%*%matrix(gamma_h[2:(1+AR_order)])))
  }

#################################################################################
# Test function
#################################################################################

################################
# AR Sim: true p =2, yw_order=2
################################
n=100 # n= 10000
sigma <- 1
ar_coeff <- c(.5,.4)
main_exp <- paste("AR sim: ",paste(ar_coeff,collapse=", "), paste(" (n=",n,")",sep=""))

#roots <- polyroot(c(1,-1*ar_coeff)) # complex roots of AR poly = 0
#roots
#Mod(roots) 
#Mod(roots) >1 # looks causal.. great!

# Some EDA
ar_sim <- my_AR_sim(n=n,ar_coeff=ar_coeff,sigma=sigma)
length(ar_sim)
plot(ar_sim,type="o",
     main=main_exp,
     xlab="t",
     ylab=expression(X[t]))
acf(ar_sim, main=main_exp)
pacf(ar_sim, main=main_exp)

## Yule Walker estimates
yw <- my_yule_walker(ts_data=ar_sim,AR_order=2)
# truth
ar <- ar_coeff
data.frame(yw =yw ,ar=ar)

################################
# AR Sim: true p =2, yw_order=3
################################
n=100 # n= 10000
sigma <- 1
ar_coeff <- c(.5,.4)
main_exp <- paste("AR sim: ",paste(ar_coeff,collapse=", "), paste(" (n=",n,")",sep=""))

#roots <- polyroot(c(1,-1*ar_coeff)) # complex roots of AR poly = 0
#roots
#Mod(roots) 
#Mod(roots) >1 # looks causal.. great!

# Some EDA
ar_sim <- my_AR_sim(n=n,ar_coeff=ar_coeff,sigma=sigma)
length(ar_sim)
plot(ar_sim,type="o",
     main=main_exp,
     xlab="t",
     ylab=expression(X[t]))
acf(ar_sim, main=main_exp)
pacf(ar_sim, main=main_exp)

## Yule Walker estimates
yw <- my_yule_walker(ts_data=ar_sim,AR_order=3)
# truth
ar <- ar_coeff
# compare
yw 
ar

# What about the estimated roots? Are they causal/stationary?
#roots <- polyroot(c(1,-1*yw)) # complex roots of AR poly = 0
#roots
#Mod(roots) 
#Mod(roots) >1 

