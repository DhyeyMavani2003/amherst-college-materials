x <-  c(1.9, 0.8, 1.1, 0.1, -0.1, 4.4, 4.6, 1.6,5.5, 3.4)
y <- c(0.7, -1.0, -0.2, -1.2, -0.1, 3.4,  0.0, 0.8, 3.7, 2.0 )
n <- 10

plot(x,y)

x_bar <- mean(x)
y_bar <- mean(y)
S_x_sq <- sum((x-x_bar)^2)

beta_1_hat <- sum((x-x_bar)*(y-y_bar))/sum((x-x_bar)^2)
beta_0_hat <- y_bar - beta_1_hat*x_bar

lines(x, beta_0_hat+beta_1_hat*x, col="red")

sigma_tilde_sq <- sum( (y- beta_0_hat-beta_1_hat*x)^2)/(n-2)
se_0 <- sigma_tilde_sq * ((1/n)+ x_bar^2/S_x_sq)
se_1 <- sigma_tilde_sq/S_x_sq
cov <- -x_bar*sigma_tilde_sq/S_x_sq

t_stat <- beta_1_hat/sqrt(se_1) 
p_val <- 2*(1-pt(abs(t_stat), df=n-2))

lm1 <- lm(y ~ x)
summary(lm1)
plot(x,y)
abline(lm1, col="red")