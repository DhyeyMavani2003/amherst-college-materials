

setwd("~/Desktop/Time series")
AD <- read.csv("Accidental_Deaths.csv")

plot(AD$time,AD$Y,type="l",col="blue",main="Accidental Deaths",ylab="Y",xlab="year (t)")
points(AD$time,AD$Y)

length(AD$Y)

72/12
72/6

2*pi/12
2*pi/6

#par(mfrow=c(1,1))
par(mfrow=c(3,3))

# Define harmonic model
harmonic_model <- lm(Y~I(sin(6*time))+I(cos(6*time)),data=AD)
plot_time <- seq(1970,1980,length=1000)
plot_y <-  predict(harmonic_model,newdata = data.frame(time=plot_time))
plot(AD$time,AD$Y,main="AD 1-freq",ylab="Y",xlab="year (t)")
lines(plot_time,plot_y,col="blue")

# define residuals after subtracting s_t
AD_resid <- residuals(harmonic_model)
plot(AD$time,AD_resid,type="l",col="blue",ylab="Residuals",xlab="year (t)")
points(AD$time,AD_resid)
abline(h=0)
acf(AD_resid)

# Define harmonic model
harmonic_model <- lm(Y~I(sin(12*time))+I(cos(12*time))+I(sin(6*time))+I(cos(6*time)),data=AD)
plot_time <- seq(1970,1980,length=1000)
plot_y <-  predict(harmonic_model,newdata = data.frame(time=plot_time))
plot(AD$time,AD$Y,main="AD 2-freq",ylab="Y",xlab="year (t)")
lines(plot_time,plot_y,col="blue")

# define residuals after subtracting s_t
AD_resid <- residuals(harmonic_model)
plot(AD$time,AD_resid,type="l",col="blue",ylab="Residuals",xlab="year (t)")
points(AD$time,AD_resid)
abline(h=0)
acf(AD_resid)


# Define harmonic model + trend
harmonic_model <- lm(Y~time+I(sin(12*time))+I(cos(12*time))+I(sin(6*time))+I(cos(6*time)),data=AD)
plot_time <- seq(1970,1980,length=1000)
plot_y <-  predict(harmonic_model,newdata = data.frame(time=plot_time))
plot(AD$time,AD$Y,main="AD 1-freq & trend",ylab="Y",xlab="year (t)")
lines(plot_time,plot_y,col="blue")

# define residuals after subtracting s_t
AD_resid <- residuals(harmonic_model)
plot(AD$time,AD_resid,type="l",col="blue",ylab="Residuals",xlab="year (t)")
points(AD$time,AD_resid)
abline(h=0)
acf(AD_resid)




