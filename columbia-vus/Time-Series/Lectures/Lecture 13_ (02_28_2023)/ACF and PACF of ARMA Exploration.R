

################################################################
# Define MA(q), AR(p) and ARMA(p,q) simulation functions with 20% burn-in
################################################################
# Students will likely yield similar acf and pacf plots using
# the arima.sim() function directly, without discarding a burn-in period.

#########
# MA sim
#########
my_MA_sim <- function(n=189,ma_coeff=c(.5),sigma=1) {
  n.start <- n + floor(.2/(1-.2)*n)
  burnin <- floor(.20*n.start)
  model_params <- list(ma = ma_coeff)
  ts_sim <- arima.sim(model = model_params, n = n.start,sd=sigma)
  ts_sim <- ts_sim[(burnin+1):n.start]
  return(ts_sim) 
}

#########
# AR sim
#########
my_AR_sim <- function(n=189, ar_coeff=c(.5),sigma=1) {
    n.start <- n + floor(.2/(1-.2)*n)
    burnin <- floor(.20*n.start)
    model_params <- list(ar = ar_coeff)
    ts_sim <- arima.sim(model = model_params, n = n.start,sd=sigma)
    ts_sim <- ts_sim[(burnin+1):n.start]
    return(ts_sim)
    }

#########
# ARMA sim
#########
my_ARMA_sim <- function(n=189,ma_coeff=c(.5),ar_coeff=c(.5),sigma=1) {
  n.start <- n + floor(.2/(1-.2)*n)
  burnin <- floor(.20*n.start)
  model_params <- list(ma = ma_coeff,ar=ar_coeff)
  ts_sim <- arima.sim(model = model_params, n = n.start,sd=sigma)
  ts_sim <- ts_sim[(burnin+1):n.start]
  return(ts_sim) 
}


################################################################
# Test some MA(q) and AR(p) models with their sample ACF/PACF's
################################################################
# MA sim
#n=10000 # also try
#ma_coeff=c(.3,.3,.2) # also try
n=100
ma_coeff=c(.4,.5)
sigma <- 1
main_exp <- paste("MA sim: ",paste(ma_coeff,collapse=", "), paste(" (n=",n,")",sep=""))
ma_sim <- my_MA_sim(n=n,ma_coeff=ma_coeff,sigma=sigma)
length(ma_sim)
plot(ma_sim,type="o",
     main=main_exp,
     xlab="t",
     ylab=expression(X[t]))
acf(ma_sim, main=main_exp)
pacf(ma_sim, main=main_exp)

# AR Sim
#n=1000 # also try
#n=10000 # also try
#ar_coeff=c(.4,.4,.1) # also try
n=100
sigma <- 1
ar_coeff <- c(.5,.4)
main_exp <- paste("AR sim: ",paste(ar_coeff,collapse=", "), paste(" (n=",n,")",sep=""))

roots <- polyroot(c(1,-1*ar_coeff)) # complex roots of AR poly = 0
roots
Mod(roots) # looks causal.. great!

ar_sim <- my_AR_sim(n=n,ar_coeff=ar_coeff,sigma=sigma)
length(ar_sim)
plot(ar_sim,type="o",
     main=main_exp,
     xlab="t",
     ylab=expression(X[t]))
acf(ar_sim, main=main_exp)
pacf(ar_sim, main=main_exp)



################################################################
# Test some ARMA(p,a) models with their sample ACF/PACF's
################################################################

#n=1000, 10000, 100000 # try larger n's
#ar_coeff=c(-.2,.4) # try other phi
#ma_coeff=c(1.3,-.9) # try other theta
n=100
ar_coeff=c(.4,.4,.1)
ma_coeff=c(.4,.5)
sigma <- 1
paste("p=",length(ar_coeff),", ","q=",length(ma_coeff),sep="")
main_exp <- paste("ARMA(p,q) sim:",paste("p=",length(ar_coeff),", ","q=",length(ma_coeff),sep=""), paste("(n=",n,")",sep=""))
AMRA_sim <- my_ARMA_sim(n=n,ma_coeff=ma_coeff,ar_coeff=ar_coeff,sigma=sigma)
length(AMRA_sim)
plot(AMRA_sim,type="o",
     main=main_exp,
     xlab="t",
     ylab=expression(X[t]))
acf(AMRA_sim, main=main_exp)
pacf(AMRA_sim, main=main_exp)

