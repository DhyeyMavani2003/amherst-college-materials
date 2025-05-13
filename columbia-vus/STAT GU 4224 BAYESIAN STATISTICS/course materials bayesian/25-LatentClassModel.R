library(rstan)

options(mc.cores = parallel::detectCores())

write("


data {
int<lower=1> I; // # of items
int<lower=1> J; // # of respondents
int<lower=1> C; // # of classes
int y[J,I]; // response  matrix
}

parameters {
  simplex[C] alpha; // probabilities of being in one group

   real <lower = 0, upper = 1> p[C, I];
}
transformed parameters{
   vector[I] p_prod; 

   /* product of endorsing probs across classes 
    to check convergence up to permutation of class labels */
   for(i in 1:I){
     p_prod[i] = prod(p[, i]); 
   }

}

model {
real lmix[C];
for (j in 1:J){
  for (c in 1: C){
               lmix[c] = log(alpha[c]) + bernoulli_lpmf(y[j, ] | p[c,]);
               }
  target += log_sum_exp(lmix);
  }
}

generated quantities {
  int<lower = 1> pred_class_dis[J];     // posterior predictive prediction for respondent j in latent class c
  simplex[C] pred_class[J];     // posterior probabilities of respondent j in latent class c
  real lmix[C];


for (j in 1:J){
  for (c in 1: C){
               lmix[c] = log(alpha[c]) + bernoulli_lpmf(y[j, ] | p[c,]);
    }               
  for (c in 1: C){
               pred_class[j][c] = exp((lmix[c])-log_sum_exp(lmix));
    }
    pred_class_dis[j] = categorical_rng(pred_class[j]); 
    // Generate a categorical variate with N-simplex distribution parameter theta; 
    // may only be used in transformed data and generated quantities blocks
  }
}
", "Example8.stan")

# simulate data with four items and two classes
J <- 1000
I = 4
latent_group <- sample(1:2,
                       prob = c(0.2, 0.8),
                       size = J,
                       replace = TRUE)

p1 <- c(0.4, 0.5, 0.4, 0.35)
p2 <- c(0.7, 0.1, 0.3, 0.9)

item <- matrix(data = NA, nrow = J, ncol = I)

library(dplyr)
for (i in 1:J) {
  item[i, ] = case_when(
    latent_group[i] == 1 ~ rbinom(
      n = rep(1, I),
      size = rep(1, I),
      prob = p1
    ),
    latent_group[i] == 2 ~ rbinom(
      n = rep(1, I),
      size = rep(1, I),
      prob = p2
    )
  )
}


# how the data look like
DT::datatable(item)

# check whether the simulated data match the generating value
item[latent_group == 1,] %>% colMeans()
item[latent_group == 2,] %>% colMeans()

#Stan program
lca_data <- list(y = item, #response matrix
                 J = J, #number of units/persons
                 I = I, #number of items
                 C = 2)

stan_fit<-
  stan(
    file = "Example8.stan",
    data = lca_data,
    iter = 1000,
    chains = 4
  )

print(stan_fit, c("alpha", "p", "lp__", "p_prod"))
traceplot(stan_fit, c("alpha", "p"))


# extract stan fit as the required format of the input
pars <- stan_fit %>% names %>% `[`(1:10)
stan_fit@model_pars

post_par <- rstan::extract(stan_fit,
                           c("alpha", "p", "pred_class", "lp__"),
                           permuted = TRUE)


# classification probabilities
post_class_p <- post_par$pred_class

# simulated allocation vectors
post_class <- ((post_class_p[,,1] > 0.5)*1) + 1

m = 2000 # of draws
K = 2 # of classes
J = 5 # of component-wise parameters

# initialize mcmc arrays
mcmc <- array(data = NA, dim = c(m = m, K = K, J = J))

# assign posterior draws to the array
mcmc[, , 1] <- post_par$alpha
for (i in 1:(J - 1)) {
  mcmc[, , i + 1] <- post_par$p[, , i]
}

# set of selected relabeling algorithm
set <-
  c("PRA",
    "ECR",
    "ECR-ITERATIVE-1",
    "AIC",
    "ECR-ITERATIVE-2",
    "STEPHENS")

# find the MAP draw as a pivot
mapindex = which.max(post_par$lp__)

install.packages("lpSolve")
library(label.switching)

# switch labels
ls_lcm <-
  label.switching(
    method = set,
    zpivot = post_class[mapindex,],
    z = post_class,
    K = K,
    prapivot = mcmc[mapindex, ,],
    constraint = 1,
    mcmc = mcmc,
    p = post_class_p,
    data = lca_data$y
  )

# similarity of the classification
ls_lcm$similarity

# permuted posterior based on ECR method
mcmc_permuted <- permute.mcmc(mcmc, ls_lcm$permutations$ECR)

# change dimension for each parameter defined as in the Stan code
mcmc_permuted <-
  array(
    data = mcmc_permuted$output,
    dim = c(2000, 1, 10),
    dimnames = list(NULL, NULL, pars)
  )

# reassess the model convergence after switch the labels
fit_permuted <-
  monitor(mcmc_permuted, warmup = 0,  digits_summary = 3)

# Get estimated and generating values for wanted parameters
generating_values = c(.8, .2, p2, p1)
sim_summary <- as.data.frame(fit_permuted)
(estimated_values <- sim_summary[pars %>% sort(), c("mean", "2.5%", "97.5%")])

# Assesmble a data frame to pass to ggplot()
sim_df <- data.frame(parameter = factor(pars, rev(pars)), row.names = NULL)
sim_df$middle <- estimated_values[, "mean"] - generating_values
sim_df$lower <- estimated_values[, "2.5%"] - generating_values
sim_df$upper <- estimated_values[, "97.5%"] - generating_values

# Plot the discrepancy
ggplot(sim_df) + aes(x = parameter, y = middle, ymin = lower, ymax = upper) +
  scale_x_discrete() + labs(y = "Discrepancy", x = NULL) + geom_abline(intercept = 0,
                                                                       slope = 0, color = "white") + geom_linerange() + geom_point(size = 2) +
  theme(panel.grid = element_blank()) + coord_flip()
