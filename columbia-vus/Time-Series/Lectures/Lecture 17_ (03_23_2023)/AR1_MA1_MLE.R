#################################################################################
# TS sim
#################################################################################

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



#############################################################
# negative log like like function 
# A(1) assuming a multivariate normal
#############################################################


################################################
# Covariance function
################################################
Covariance_matrix_AR1 <- function(phi,sigma,n) {
  cov_mat <- matrix(0,nrow=n,ncol=n)
  for (i in 1:n) {
    for (j in 1:n) {
      cov_mat[i,j] <- phi^abs(i-j)
    }
  }
  cov_mat <- sigma^2/(1-phi^2)*cov_mat
  return(cov_mat)
}
# Test covariance
Covariance_matrix_AR1(phi=.5,sigma=1,n=4)

# negative log like like function 

neg_log_like_MN_AR1 <- function(params,x) {
  phi <- params[1]
  sigma <-  params[2]
  n <- length(x)
  x <- as.matrix(x)
  Omega <- Covariance_matrix_AR1(phi=phi,sigma=sigma,n=n)
  quad_form <- c(t(x)%*%solve(Omega)%*%x)
  neg_log_like <- (n/2)*log(2*pi)-(1/2)*log(det(solve(Omega)))+(1/2)*quad_form
  return(neg_log_like)
}
# test
# Try phi=.1, .5., .9, ect.. 
set.seed(1)
ts <- my_AR_sim(n=100, ar_coeff=c(.1),sigma=1)
plot(ts,type="o")
length(ts)


neg_log_like_MN_AR1(params=c(.1,.1),x=ts)
nlm(neg_log_like_MN_AR1,p=c(.1,1),x=ts)


#############################################################
# negative log like like function 
# A(1) assuming exact likelihood
#############################################################

neg_log_like_exact_AR1 <- function(params,x) {
  phi <- params[1]
  sigma <-  params[2]
  n <- length(x)
  x0_part <- (1/2)*log(2*pi)+(1/2)*log(sigma^2/(1-phi^2))+(1/(2*sigma^2))*(1-phi^2)*x[1]^2 
  x_part <- (1/2)*log(2*pi)+(n/2)*log(sigma^2)+(1/(2*sigma^2))*sum((x[2:n]-phi*x[1:(n-1)])^2)
  neg_log_like <- x0_part+x_part
  return(neg_log_like)
}
nlm(neg_log_like_exact_AR1,p=c(.1,1),x=ts)

# Compare
nlm(neg_log_like_exact,p=c(.1,1),x=ts)$estimate
nlm(neg_log_like_MN,p=c(.1,1),x=ts)$estimate


#############################################################
# negative log like like function 
# MA(1) assuming a multivariate normal
#############################################################


################################################
# Covariance function
################################################
# MA acvf
MA_gamma <- function(h,theta,sigma) {
  acvf <- ifelse(abs(h)==0,sigma^2*(1+theta^2),
         ifelse(abs(h)==1,sigma^2*theta,
                0))
  return(acvf)
  } 
MA_gamma(h=0,theta=.5,sigma=1)

#Covariance 
Covariance_matrix_MA <- function(theta,sigma,n) {
  cov_mat <- matrix(0,nrow=n,ncol=n)
  for (i in 1:n) {
    for (j in 1:n) {
      cov_mat[i,j] <- MA_gamma(h=i-j,theta=theta,sigma=sigma)
    }
  }
  return(cov_mat)
}
Covariance_matrix_MA(theta=.5,sigma=1,n=10)

# negative log like like function 

neg_log_like_MN_MA <- function(params,x) {
  theta <- params[1]
  sigma <-  params[2]
  n <- length(x)
  x <- as.matrix(x)
  Omega <- Covariance_matrix_MA(theta=theta,sigma=sigma,n=n)
  quad_form <- c(t(x)%*%solve(Omega)%*%x)
  neg_log_like <- (n/2)*log(2*pi)-(1/2)*log(det(solve(Omega)))+(1/2)*quad_form
  return(neg_log_like)
}


# test
# Try theta=.1, .5., .9, ect..
set.seed(1)
ts <- my_MA_sim(n=200, ma_coeff=c(.75),sigma=1)
plot(ts,type="o")
length(ts)
neg_log_like_MN_MA(c(.1,1),x=ts)
nlm(neg_log_like_MN_MA,p=c(.1,1),x=ts)







