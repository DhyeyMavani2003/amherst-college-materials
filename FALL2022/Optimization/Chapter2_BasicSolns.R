# Chapter 2 example of solving LP using basic feasible points

library(pracma)

A <- matrix(c(2, 1, 1, 0, 1, 3, 0, 1), 2, 4, byrow = TRUE)
A
rref(A)

Ab <- matrix(c(2, 1, 1, 0, 1, 1, 3, 0, 1, 1), 2, 5, byrow = TRUE)
Ab
rref(Ab)

a1 <- c(2,1)
a2 <- c(1,3)
a3 <- c(1,0)
a4 <- c(0,1)
b <- c(1,1)

# choose different pairs of linearly independent 
# columns to be first two in A to find all basic solns
rref(cbind(a1,a2,b))
rref(cbind(a1,a3,b))
rref(cbind(a1,a4,b))
rref(cbind(a2,a3,b))
rref(cbind(a2,a4,b))
rref(cbind(a3,a4,b))

