# Chapter 3 sensitivity example

# Import lpSolve package
library(lpSolve)

# Set coefficients of the objective function
f.obj <- c(-700,-600,0)

# Set matrix corresponding to coefficients of constraints by rows
# Assumes non-negative solutions
f.con <- matrix(c(1,1,0,
                  8,6,1),
                nrow = 2, byrow = TRUE)

# Set inequality signs
f.dir <- c("=","=")

# Set right hand side coefficients
f.rhs <- c(40,300)

# Solution to linear program
result <- lp("min", f.obj, f.con, f.dir, f.rhs, compute.sens = T)

primalsoln <- result$solution # x1 x2 x3

optimalvalue <- result$objval

dualsoln <- result$duals[1:2] # y1 y2

# changing b=f.rhs should change optimal value by y1*delta_b1+y2*delta_b2
delta_b <- c(2,-10)
f.rhs <- c(40,300)+delta_b
change <- lp("min", f.obj, f.con, f.dir, f.rhs)$objval - optimalvalue
dualsoln[1]*delta_b[1]+dualsoln[2]*delta_b[2]


# Complementary slackness example

# Set coefficients of the objective function
f.obj <- c(13,10,6)

# Set matrix corresponding to coefficients of constraints by rows
# Assumes non-negative solutions
f.con <- matrix(c(5,1,3,
                  3,1,0),
                nrow = 2, byrow = TRUE)

# Set inequality signs
f.dir <- c("=","=")

# Set right hand side coefficients
f.rhs <- c(8,3)

# Solution to linear program
result <- lp("min", f.obj, f.con, f.dir, f.rhs, compute.sens = T)

primalsoln <- result$solution # x1 x2 x3

optimalvalue <- result$objval

# Given the primal solution, we can construct the dual solution
# using complementary slackness: solve 5*y1+3*y2=13, 3*y1=6 to get
# y1=2, y2=1. Check this result:

dualsoln <- result$duals[1:2] # y1 y2
