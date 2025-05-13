library(tseries)


##############################################
# Simulate ARIMA(p=1,d=1,q=0)
##############################################
Y <- arima.sim(model=list(order = c(1,1,0), ar = 0.7), n = 200)
plot(Y,type="o",main="ARIMA(1,1,0)")

############################
# acf and pacf of Y
############################
acf(Y)
pacf(Y)

############################
# acf and pacf of diff(Y)
############################
acf(diff(Y))
pacf(diff(Y))

############################
# Dickey-Fuller Test
# KPSS Test
############################
adf.test(Y)
adf.test(diff(Y))
# Ho: Not Stationary, HA: Stationary

kpss.test(Y)
kpss.test(diff(Y))
# Ho: Stationary, HA: Not Stationary

##############################################
# Simulate ARIMA(p=0,d=1,q=1)
##############################################
Y <- arima.sim(model=list(order = c(0,1,1), ma = -.6), n = 200)
plot(Y,type="o",main="ARIMA(0,1,1)")

############################
# acf and pacf of Y
############################
acf(Y)
pacf(Y)

############################
# acf and pacf of diff(Y)
############################
acf(diff(Y))
pacf(diff(Y))

############################
# Dickey-Fuller Test
# KPSS Test
############################
adf.test(Y)
adf.test(diff(Y))
# Ho: Not Stationary, HA: Stationary

kpss.test(Y)
kpss.test(diff(Y))
# Ho: Stationary, HA: Not Stationary

