# Chapter 2 application example

# Import lpSolve package
library(lpSolve)

# Network flow example

# Assumes non-negative solutions!

# Set coefficients of the objective function
f.obj <- c(1,1,1,rep(0,7))

# Set matrix corresponding to coefficients of constraints by rows
f.con <- matrix(c(1,0,0,-1,-1,rep(0,5),
                  0,1,0,1,0,-1,rep(0,4),
                  0,0,1,0,0,0,-1,-1,0,0,
                  0,0,0,0,1,0,1,0,-1,0,
                  rep(0,5),1,0,1,0,-1,
                  1,rep(0,9),
                  0,1,rep(0,8),
                  0,0,1,rep(0,7),
                  0,0,0,1,rep(0,6),
                  rep(0,4),1,rep(0,5),
                  rep(0,5),1,rep(0,4),
                  rep(0,6),1,rep(0,3),
                  rep(0,7),1,rep(0,2),
                  rep(0,8),1,0,
                  rep(0,9),1),
                nrow = 15, byrow = TRUE)

# Set inequality signs
f.dir <- c(rep("=",5),rep("<=",10))

# Set right hand side coefficients
maxflows <- c(3,1,1,1,1,3,4,4,4,1)
f.rhs <- c(f.con[1:5,] %*% maxflows,
           2*maxflows)

# Solution to linear program
result <- lp("max", f.obj, f.con, f.dir, f.rhs)

soln <- result$solution-maxflows # optimal flows in each segment
