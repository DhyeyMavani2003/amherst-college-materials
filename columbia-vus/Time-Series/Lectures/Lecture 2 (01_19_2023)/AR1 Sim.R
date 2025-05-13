
sig <- 1
phi <- .9
n <- 100
Y_t <- NULL
Y_t[1] <- 0
for (i in 1:n) {
  Y_t[i+1] <- phi*Y_t[i]+rnorm(1,sd=sig)
}
# Plot AR(1)
plot(0:n,Y_t,type="o",main="AR(1)")

# Estimate phi
lm(Y_t[2:n]~Y_t[1:(n-1)]-1)

# Plot White Noise
plot(0:n,rnorm(n+1),type="o",main="WN")



