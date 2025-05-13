

# Load in data
setwd("~/Desktop/Classes/GU4221/Data files")
Lake_Huron <- read.csv("Lake_Huron.csv")

# Plot raw time series
plot(Lake_Huron$time,Lake_Huron$Y,type="l",col="blue",
     main="Level of Lake Huron",xlab="year (t)",ylab="Level")
points(Lake_Huron$time,Lake_Huron$Y,pch=1)

# fit lnear trend
lm_lake <- lm(Y~time,data=Lake_Huron)
abline(lm_lake)

# define residuals after subtracting trend
Huron_resid <- residuals(lm_lake)


# Plot residuals
plot(Lake_Huron$time,Huron_resid,type="l",col="blue",
     main="Residuals Lake Huron",xlab="year (t)",ylab="Residuals")
points(Lake_Huron$time,Huron_resid,pch=1)
abline(h=0,lty=2)

# ACF for residuals
acf(Huron_resid)

# Estimate AR(1)
n <- length(Lake_Huron$Y)
n
plot(Huron_resid[1:97],Huron_resid[2:98])
Huron_ar1 <- lm(Huron_resid[2:98]~Huron_resid[1:97])
abline(Huron_ar1)

# ACF of AR(1) (of residuals..)
acf(residuals(Huron_ar1))

# Try AR(2) on residuals.. 
length(3:98)
length(2:97)
length(1:96)
Huron_ar2 <- lm(Huron_resid[3:98]~Huron_resid[2:97]+Huron_resid[1:96])
acf(residuals(Huron_ar2))


