# Implement the Babystep-Giantstep algorithm, efficiently enough to solve the discrete logarithm problem for primes up to 40 bits in length. That is, write a function bsgs(g,h,p) that finds an integer x such that g^x = h mod{p}. You may assume that p is prime, 1 <= g,h < p, and that a solution x exists.

#The test bank on Gradescope will be identical to the test bank for disclog on Problem Set 2, but now your code must solve all 40 cases for full credit.

#(It is fine to implement any algorithm you can devise to solve the discrete logarithm problem, but BSGS is probably the easiest to implement based on what we've discussed in class.)


import math
def bsgs(g,h,p):
    # your code here
    N = int(math.ceil(math.sqrt(p - 1))) 
    t = dict()
    # Baby step.
    b = 1
    for i in range(N):
        #t[pow(g, i, p)]=i
        t[b]=i
        b = (b*g)%p
    
    # Fermat’s Little Theorem
    c = pow(g, N * (p - 2), p)
    y = h%p ####
    for j in range(N):
        #y = (h * pow(c, j, p)) % p
        if y in t: 
            return j * N + t[y]
        y = (y*c) % p
    return None

#def bsgs(g,h,p):
#    # your code here
#    N = int(math.ceil(math.sqrt(p - 1))) 
#    t = {}
#    c = pow(g, N * (p - 2), p)
#    # Baby step.
#    for i in range(N):
#        t[pow(g, i, p)]=i
#    # Fermat’s Little Theorem
#        
#        y = (h * pow(c, i, p)) % p
#        if y in t: 
#            return i * N + t[y]
#    return None

