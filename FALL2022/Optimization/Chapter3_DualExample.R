# Chapter 3 dual example (tables, desks, chairs)

# Import lpSolve package
library(lpSolve)

# Solve original max profit LP

# Set coefficients of the objective function
f.obj <- c(80,60,50)

# Set matrix corresponding to coefficients of constraints by rows
# Assumes non-negative solutions
f.con <- matrix(c(8,6,4,
                  5,4,4),
                nrow = 2, byrow = TRUE)

# Set inequality signs
f.dir <- c("<=","<=")

# Set right hand side coefficients
f.rhs <- c(100,60)

# Solution to linear program
result <- lp("max", f.obj, f.con, f.dir, f.rhs)

soln <- result$solution # x1 x2 x3

# Solve dual LP

# Set coefficients of the objective function
f.obj <- c(100,60)

# Set matrix corresponding to coefficients of constraints by rows
# Assumes non-negative solutions
f.con <- matrix(c(8,5,
                  6,4,
                  4,4),
                nrow = 3, byrow = TRUE)

# Set inequality signs
f.dir <- c(">=",">=",">=")

# Set right hand side coefficients
f.rhs <- c(80,60,50)

# Solution to linear program
result <- lp("min", f.obj, f.con, f.dir, f.rhs)

soln <- result$solution # y1 y2
