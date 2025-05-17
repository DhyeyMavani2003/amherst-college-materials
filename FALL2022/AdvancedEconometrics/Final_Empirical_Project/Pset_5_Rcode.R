#Importing Dataset
#print(plants)
#print(plants[order(plants$plant, decreasing = FALSE), ]   )

## Question 1 --------

#Setting up some Variables -----------
mean_lnk <- mean(plants$lnk)
mean_lnl <- mean(plants$lnl)
mean_lny <- mean(plants$lny)

ones <- 1
lnkXwarm <- plants$lnk * plants$warm
lnlXwarm <- plants$lnl * plants$warm
lnk0 <- plants$lnk - mean_lnk
lnl0 <- plants$lnl - mean_lnl
lny0 <- plants$lny - mean_lny


#Regression ofr part (b)  ----
regress = lm(lny~lnk+lnl, data=plants)
summary(regress)

# get the covariance matrix for part (c)  ----
vcov(regress)

# conduct hypothesis test part (e) ------------
R <- rbind(c(0, 1, 1))
r <-1
b_ols <- c(-1.30930, 0.55693, 0.72384)


#create a Nx3 matrix
X <- data.frame(plants$lnk, plants$lnl)
X <- cbind(ones = 1, X)

X_matrix <- as.matrix(X)

Q <- t(X_matrix)%*%X_matrix
Q_inv = solve(Q)


N = length(plants$lnk)
counter <- 0
for (i in 1:N)
  counter <- counter+(plants$lny[i]-b_ols[1]-b_ols[2]*plants$lnk[i]-b_ols[3]*plants$lnl[i])^2
s_squared <- (1/(N-3))*counter

t_stat <-  (R%*%b_ols-r)/((s_squared)*R%*%Q_inv%*%t(R))^(1/2)
print(paste("t_stat is",t_stat))



# finding critical region for t stat
critical_value <- qt(p=0.025, df=100-3, lower.tail=FALSE)

# regression without intercept (f) ----

regress_f = lm(formula = lny~lnk+lnl +0, data=plants) 
summary(regress_f)

# regression without intercept & standardization around the mean (g) ----

regress_g = lm(formula = lny0~lnk0+lnl0 +0) 
summary(regress_g)

## Question 2 --------

# regression (b) ----

regress_2b <- lm(formula = plants$lnk~plants$lnl) 
summary(regress_2b)

lnkres <- resid(regress_2b)


regress_2b3 <- lm(formula = plants$lny~lnkres) 
summary(regress_2b3)

# regression (c) ----

regress_2c <- lm(formula = plants$lnk~plants$lnl +0) 
summary(regress_2c)

lnkres2 <- resid(regress_2c)

one_vec <- rep(1, 100)
regress_2c3 <- lm(formula = one_vec~plants$lnl +0) 
summary(regress_2c3)

oneres2 <- resid(regress_2c3)

regress_2c5 <- lm(formula = plants$lny~lnkres2+oneres2 +0) 
summary(regress_2c5)


## Question 3 --------

# regression and cov matrix (a) ----

regress_3b = lm(formula = plants$lny~plants$lnk+plants$lnl+lnkXwarm+lnlXwarm) 
summary(regress_3b)
vcov(regress_3b)


Q3_regression <- function(){
  R <- rbind(c(0,0,0,1,0))
  r <- 0
  b_ols <- c(-1.40095, 0.61003, 0.74217, -0.10536, 0.02249)
  print(paste("R*b_ols is",R%*%b_ols-r))
  
  X <- data.frame(plants$lnk, plants$lnl, lnkXwarm, lnlXwarm)
  X <- cbind(ones = 1, X)
  
  X_matrix <- as.matrix(X)
  
  Q <- t(X_matrix)%*%X_matrix
  Q_inv = solve(Q)
  
  
  N = length(plants$lnk)
  counter <- 0
  for (i in 1:N)
    counter <- counter+(plants$lny[i]-b_ols[1]-b_ols[2]*plants$lnk[i]-b_ols[3]*plants$lnl[i]-b_ols[4]*lnkXwarm[i]-b_ols[5]*lnlXwarm[i])^2
  s_squared <- (1/(N-5))*counter
  
  print(paste("s_squared is",s_squared))
  
  t_stat <-  (R%*%b_ols-r)/((s_squared)*R%*%Q_inv%*%t(R))^(1/2)
  print(paste("t_stat is",t_stat))
  
  critical_value <- qt(p=0.1, df=100-5, lower.tail=FALSE)
  print(paste("critical value is",critical_value))
  
}
Q3_regression()



# hypothesis test

# Question 4 ------


library(dplyr)
plants <- plants  %>%
  mutate(wt = case_when(
    plant==1 ~ 1,
    plant==2 ~ 0.5
  ))

wt2 <- plants$wt*plants$wt
lnywt <- plants$lny * plants$wt
lnkwt<-plants$lnk * plants$wt
lnlwt<-plants$lnl * plants$wt
lnkXwt<-lnkXwarm * plants$wt
lnlXwt<-lnlXwarm * plants$wt

regress_4b = lm(formula = lnywt~plants$wt+lnkwt+lnlwt +lnkXwt +lnlXwt+0) 
summary(regress_4b)
vcov(regress_4b)

regress_4c = lm(formula = plants$lny~plants$lnk+plants$lnl+ lnkXwarm + lnlXwarm, weights = wt2) 
summary(regress_4c)
vcov(regress_4c)







