



##############################################################
# Load in data
##############################################################
setwd("~/Desktop")
#write.csv(data.frame(t=1:30,x=ts_train),file="ARMA_11_train.csv",row.names=F)
ts_train <- read.csv("ARMA_11_train.csv")
#write.csv(data.frame(t=31:40,x=ts_test),file="ARMA_11_test.csv",row.names=F)
ts_test <- read.csv("ARMA_11_test.csv")
n <- nrow(ts_train)+nrow(ts_test)

##############################################################
# Plot Data
##############################################################
plot(ts_train$x,type="o",xlim=c(0,40),xlab="t",ylab="x",main="ARMA(1,1): Train and Test")
points((n-10+1):n,ts_test$x,col="red")
lines((n-10+1):n,ts_test$x,col="red",lty=2)
lines(c(n-10,n-10+1),c(ts_train$x[n-10],ts_test$x[1]),col="red",lty=2)


##############################################################
# arima()
##############################################################

arima_R <- arima(ts_train$x, order=c(1,0,1), method="ML",include.mean = T)

##############################################################
# forecast
##############################################################

arima_R_forecast <- predict(arima_R, n.ahead = 10)
arima_R_forecast$pred
arima_R_forecast$se



##############################################################
# Plot Data with Forecast
##############################################################

plot(ts_train$x,type="o",xlim=c(0,40),xlab="t",ylab="x",main="ARMA(1,1): Train and Test")
points((n-10+1):n,ts_test$x,col="red")
lines((n-10+1):n,ts_test$x,col="red",lty=2)
lines(c(n-10,n-10+1),c(ts_train$x[n-10],ts_test$x[1]),col="red",lty=2)
points((n-10+1):n,arima_R_forecast$pred,col="blue",pch=19,cex=.75)
lines((n-10+1):n,arima_R_forecast$pred,col="blue",lty=1)




