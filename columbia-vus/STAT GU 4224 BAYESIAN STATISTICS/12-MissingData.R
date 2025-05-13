# Mean imputation function
# The input is a = data vector

mean.imp = function (a)
  {
  missing <- is.na(a)
  a.obs <- a[!missing]
  imputed <- a
  imputed[missing] <- mean(a.obs)
  # Output the imputed vector
  return (imputed)
}

x = 1:20 # This is just a vector with first 20 natural numbers
hist(x)
# Make 4 of these missing
miss = sample(1:20, 4)
x.mcar = x
x.mcar[miss] <-NA
x.mcar
# Testing for missing values
is.na(x.mcar)
hist(x.mcar)

# Impute with mean

mean.imp(x.mcar)
x.car.mean.imp = mean.imp(x.mcar)
hist(x.car.mean.imp)

# Exercise: Try with MNAR mechanism. For example remove only the small values

x.mnar = 21:40 + rnorm(20)
x.mnar[1:4] = NA
# Finish the exercise by imputing the mean ...

# Alternative: install package mice
install.packages("mice")

# Load the package
library(mice)
d = cbind(x.mcar, x.mnar)
d

# The missing pattern matrix:
(1-is.na(d))

out = mice(data.frame(d), method = "mean", m = 1, maxit = 1)
complete(out)

# Real data example
# Read the the Social Indicators Survey data 
# Data are at http://www.stat.columbia.edu/~gelman/arm/examples/sis
# Choose file siswave3v4impute3.csv

wave3 <- read.table (file.choose(), header=T, sep=",")

# The following makes the names of the variables available for direct reference
attach(wave3)

# Sample size or number of cases
n <- nrow (wave3)

# earnings variables:

# rearn:  respondent's earnings
# tearn:  spouse's earnings
# workmos:  primary earner's months worked last year


# set up some simplified variables to work with
# returns NA of argument is negative or equal to 999999
na.fix <- function (a) 
{
  ifelse (a<0 | a==999999, NA, a)
}

# Cleaning the data
earnings <- na.fix(rearn) + na.fix(tearn)

# sets earnings equal to 0 for those who worked 0 months
earnings[workmos==0] <- 0

# rescaling the earnings
earnings <- earnings/1000

# Showing some missing values on rows 91 - 95
cbind (sex, race, educ_r, r_age, earnings, police)[91:95,]

hist(earnings)

# Better histogram
par(mfrow =c(1,2))

sorte = sort(earnings)
hist(sorte[1:(length(sorte)-3)], main = "Before imputing the mean")

earnings.imp.mean <- mean.imp (earnings)
# Now the missing values are replaced with the mean
cbind (sex, race, educ_r, r_age, earnings.imp.mean, police)[91:95,]

hist(sort(earnings.imp.mean)[1:(length(earnings.imp.mean)-3)], main = "After imputing the mean")
# Notice the pile up of values in the middle!

par(mfrow = c(1,1))

## Simple random imputation
random.imp <- function (a)
{
  missing <- is.na(a)
  n.missing <- sum(missing)
  a.obs <- a[!missing]
  imputed <- a
  imputed[missing] <- sample (a.obs, n.missing, replace=TRUE)
  return (imputed)
}

earnings.imp <- random.imp (earnings)

# Now the missing values are replaced with the data generated from sample
cbind (sex, race, educ_r, r_age, earnings.imp, police)[91:95,]


# Exercise:
# Apply mean and random imputations to the built-in airquality data 
# to impute values for the missing Ozone levels
# Plot a histogram before and after the imputation

hist(airquality$Ozone)
out = mice(airquality, method = "mean", m = 1, maxit = 1) # impute the mean
complete(out)

hist(complete(out)$Ozone)
hist(random.imp(airquality$Ozone))


# LVCF for airquality data
a5 = airquality[,1]

for (i in 1:length(a5)) if (is.na(a5[i])) a5[i] = a5[i-1]


# or keep original Ozone
airquality.lvcf = cbind(a5,airquality)

# NVCB
attach(airquality)
a6 = Ozone

for (i in length(a6):1) 
{

if (is.na(a6[i])) a6[i] = a6[i+1]
}
# or keep original Ozone
airquality.nvcb = cbind(a6,airquality)




# Inverse logistic curve
ilogit = function(x) exp(x)/(1+exp(x))
curve(ilogit(.2*x),-20,20)

# Let's use the built-in cars dataset

# Choose some constants that will not give you too much missing:
p = ilogit((cars$speed- mean(cars$speed))/5 -2)
(R = rbinom(length(cars$speed), 1, p))
mean(R)

# Finally make dist missing for those R:
y = cars$dist
y[R==1] = NA
y

# Create a new dataset with the missing:
cars.miss = cbind(cars$speed, y)
colnames(cars.miss) = c("speed", "dist")
cars.miss