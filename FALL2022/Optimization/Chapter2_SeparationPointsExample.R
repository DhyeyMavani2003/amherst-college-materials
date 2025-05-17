# Chapter 2 examples: production plan and separation of points

# Import lpSolve package (to solve linear programming problems)
library(lpSolve)

# Production example from first day of class

# Set coefficients of the objective function
f.obj <- c(3, 4) # 3*x1+4*x2

# Set matrix corresponding to coefficients of constraints by rows
# Assumes non-negative solutions
f.con <- matrix(c(1,2,#  1*x1+2*x2<=10
                  2,1,#  2*x1+1*x2<=10
                  1,1),# 1*x1+1*x2<=6
                nrow = 3, byrow = TRUE)

# Set inequality signs
f.dir <- c("<=",
           "<=",
           "<=")

# Set right hand side coefficients
f.rhs <- c(10,
           10,
           6)

# Solution to linear program
result <- lp("max", f.obj, f.con, f.dir, f.rhs)

soln <- result$solution # x1 x2


# Separation of points example

# p_i points: (0,2) (2,3) (1,3) (3,4) (5,5)
# q_j points: (1,0) (2,1) (3,3) (4,3)

plot(c(0,2,1,3,5),c(2,3,3,4,5), col="red", pch=19, 
     xlab="x", ylab="y", xlim=c(0,5), ylim=c(0,5))
points(c(1,2,3,4),c(0,1,3,3), col="blue", pch=19)
abline(a=1,b=.75) # guess a line that works

# Set coefficients of the objective function
f.obj <- c(0,0,1) # 0*a + 0*b + delta

# Set matrix corresponding to coefficients of constraints by rows
# Assumes non-negative solutions
f.con <- matrix(c(0, 1, 1, # a*0+b+delta <= 2
                  2, 1, 1, # a*2+b+delta <= 3
                  1, 1, 1, # a*1+b+delta <= 3
                  3, 1, 1, # a*3+b+delta <= 4
                  5, 1, 1, # a*5+b+delta <= 5
                  1, 1, -1, # a*1+b-delta >= 0
                  2, 1, -1, # a*2+b-delta >= 1
                  3, 1, -1, # a*3+b-delta >= 3
                  4, 1, -1),# a*4+b-delta >= 3
                nrow = 9, byrow = TRUE)

# Set inequality signs
f.dir <- c("<=",
           "<=",
           "<=",
           "<=",
           "<=",
           ">=",
           ">=",
           ">=",
           ">=")

# Set right hand side coefficients
f.rhs <- c(2,
           3,
           3,
           4,
           5,
           0,
           1,
           3,
           3)

# Solution to linear program
result <- lp("max", f.obj, f.con, f.dir, f.rhs)

soln <- result$solution # a b delta

plot(c(0,2,1,3,5),c(2,3,3,4,5), col="red", pch=19, 
     xlab="x", ylab="y", xlim=c(0,5), ylim=c(0,5))
points(c(1,2,3,4),c(0,1,3,3), col="blue", pch=19)
abline(a=soln[2],b=soln[1]) # plot separating line found via linear program
abline(a=soln[2]+soln[3],b=soln[1], lty=2)
abline(a=soln[2]-soln[3],b=soln[1], lty=2)

# Instead of line, could find separating curve y=a*log(x+1)+b
f.con <- matrix(c(log(0+1), 1, 1, # a*0+b+delta <= 2
                  log(2+1), 1, 1, # a*2+b+delta <= 3
                  log(1+1), 1, 1, # a*1+b+delta <= 3
                  log(3+1), 1, 1, # a*3+b+delta <= 4
                  log(5+1), 1, 1, # a*5+b+delta <= 5
                  log(1+1), 1, -1, # a*1+b+delta >= 0
                  log(2+1), 1, -1, # a*2+b+delta >= 1
                  log(3+1), 1, -1, # a*3+b+delta >= 3
                  log(4+1), 1, -1),# a*4+b+delta >= 3
                nrow = 9, byrow = TRUE)

# Solution to linear program
result <- lp("max", f.obj, f.con, f.dir, f.rhs)

soln <- result$solution # a b delta

plot(c(0,2,1,3,5),c(2,3,3,4,5), col="red", pch=19, 
     xlab="x", ylab="y", xlim=c(0,5), ylim=c(0,5))
points(c(1,2,3,4),c(0,1,3,3), col="blue", pch=19)
# plot separating curve found via linear program
x <- seq(0,5,0.1)
points(x, soln[1]*log(x+1)+soln[2], type="l")
points(x, soln[1]*log(x+1)+soln[2]+soln[3], type="l", lty=2)
points(x, soln[1]*log(x+1)+soln[2]-soln[3], type="l", lty=2)

