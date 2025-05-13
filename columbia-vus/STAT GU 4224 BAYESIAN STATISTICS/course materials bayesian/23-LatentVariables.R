# Read the dataset file 23-E9-6.dat
olympic = as.matrix(read.table(file.choose()))

colnames(olympic) = c('100-m', 'LongJump', 'ShotPut', 'HighJump',  '400-m', '110-m.hurdles', 'Discus', 'PoleVault', 'Javelin', '1500-m')
olympic
# Note this is the correlation matrix, not the original data!

# Eigenvalues
eigen(olympic)$values
# 3 or 4 factors should be enough

# Scree plot to select number of factors:
plot(eigen(olympic)$values, xlab = 'Eigenvalue Number', ylab = 'Eigenvalue Size', main = 'Scree Graph', type = 'b', xaxt = 'n')
axis(1, at = seq(1, 10, by = 1))
abline(h = 1)
# We decide to use 4 factors

# Meaningful names
rownames(olympic) = colnames(olympic)

# The psych package has many functions available for performing factor analysis
# install.packages("psych")
library(psych)

# factanal function uses the MLE method:
(fit = factanal(factors = 4, covmat = olympic))

print(fit, digits = 2, cutoff=.3, sort = TRUE)
# plot factor 1 by factor 2 
load = fit$loadings[,1:2] 
plot(load,type="n") # set up plot
text(load,labels=colnames(olympic),cex=.7) # add variable names


# Example 2:
# Install package sem
 install.packages("sem")
library(sem)

# Example from lecture notes for the data in Table 14.1 of Rencher (file T14_1_GRADES.DAT)

d = read.table(file.choose(), header = F)
colnames(d) = c("Lab", "HW", "PopQuiz", "Exam1", "Exam2", "FinalExam")
head(d)

# Assume 2 factors: 
# f1 = "Daily effort" which affects Lab, HW, and PopQuiz
# f2 = "Knowledge mastery" which affects PopQuiz, Exam1, Exam2, and FinalExam
# Assume further HW has loading 1 wrt to f1 and FinalExam has loading 1 wrt to f2
# That is q = 14 total parameters.

# The model:
mod2 <- c("Daily     -> Lab, lambda11, NA",
          "Daily     -> HW, NA, 1",
          "Daily     -> PopQuiz, lambda31, NA",
          "Knowledge     -> PopQuiz, lambda32, NA",
          "Knowledge  -> Exam1, lambda4, NA",
          "Knowledge  -> Exam2, lambda5, NA",
          "Knowledge  -> FinalExam, NA, 1",
          
          "Lab        <-> Lab, psi1, NA",
          "HW         <-> HW, psi2, NA",
          "PopQuiz        <-> PopQuiz, psi3, NA",
          "Exam1        <-> Exam1, psi4, NA",
          "Exam2         <-> Exam2, psi5, NA",
          "FinalExam         <-> FinalExam, psi6, NA",
          
          "Daily    <-> Daily, phi11, NA",
          "Daily    <-> Knowledge, phi12, NA",
          "Knowledge <-> Knowledge, phi22, NA")

# save the model as text file
writeLines(mod2, con = "knowledge_model.txt")

# Read model from file (function specifyModel reads file or accepts character string)
(knowledge_model <- specifyModel(file = "knowledge_model.txt"))

knowledge_sem <- sem::sem(knowledge_model, S = cov(d), nrow(d))
summary(knowledge_sem)

# The Ho: model fits the data has p-value 0.09 so it is not rejected!

# Fit indeces:
# Bentler's CFI (equation 14.20 from Rencher)
summary(knowledge_sem, fit.indices = "CFI")
# Should be > 0.95 so model fits well

# Example 3
# lavaan package (latent variable analysis)
install.packages("lavaan")
library(lavaan)
?PoliticalDemocracy

# How to specify a model with lavaan:

# Regressions: a typical regression model is simply a set of regression formulas, 
# where some variables may be latent.

# Measurement models: if we have latent variables in any of the regression formulas, 
# we must ‘define’ them by listing their (manifest or latent) indicators. 
# We do this by using the special operator “=~”, which can be read as is measured by.

# Variances and covariances: specified using a ‘double tilde’ operator


# SEM model:
model <- '
# measurement model for latent variables
ind60 =~ x1 + x2 + x3
dem60 =~ y1 + y2 + y3 + y4
dem65 =~ y5 + y6 + y7 + y8

# regressions between latent variables:
dem60 ~ ind60
dem65 ~ ind60 + dem60

# correlations and covariances
y1 ~~ y5
y2 ~~ y4 + y6 # Equivalent to y2 ~~ y4; y2 ~~ y6
y3 ~~ y7
y4 ~~ y8
y6 ~~ y8
'

fit <- lavaan::sem(model, data = PoliticalDemocracy)
summary(fit)

install.packages("lavaanPlot")
library(lavaanPlot)
lavaanPlot(model = fit, coef = TRUE, cov = TRUE)

# Example 4: confirmatory factor analysis and Bayesian version
library("lavaan")
# We use the classic Holzinger and Swineford (1939) dataset
# consists of mental ability test scores of seventh- and eighth-grade 
# children from two different schools


HS.model <- ' visual  =~ x1 + x2 + x3
              textual =~ x4 + x5 + x6
              speed   =~ x7 + x8 + x9 '

fit <- lavaan(HS.model, data=HolzingerSwineford1939,
              auto.var=TRUE, auto.fix.first=TRUE,
              auto.cov.lv.x=TRUE)
# Note by default, the factor loading of the first indicator of 
# a latent variable is fixed to 1

summary(fit, fit.measures=TRUE)

lavaanPlot(model = fit, coef = TRUE, cov = TRUE)


#install.packages("blavaan")
library("blavaan")
HS.model <- ' visual =~ x1 + x2 + x3
verbal =~ x4 + x5 + x6 '
bfit <- bcfa(HS.model, data = HolzingerSwineford1939)
summary(bfit)

HS.model <- ' visual =~ x1 + prior("normal(1,1)")*x2 + x3
verbal =~ x4 + x5 + x6 '
bfit <- bcfa(HS.model, data = HolzingerSwineford1939,
             dp = dpriors(lambda = "normal(1,5)"),
             burnin = 500, sample = 500, n.chains = 4,
             save.lvs = TRUE,
             bcontrol = list(cores = 4))
summary(bfit)