# Write function bsgsBoundedOrder(g,h,p,q) to solve the discrete logarithm function g^x = h mod{p} under the assumption that the order of g is at most q. You may assume that p is a prime number, but unlike the previous set it will be quite large (256 bits). The integer q will be various sizes, up to 40 bits. Any correct solution x will be marked correct, even if it is not the smallest possible solution.

import math

def bsgsBoundedOrder(g,h,p,q):
    # your code here
    N = int(math.ceil(math.sqrt(q))) 
    t = dict()
    # Baby step.
    b = 1
    for i in range(N):
        #t[pow(g, i, p)]=i
        t[b]=i
        b = (b*g)%p
    # Fermat’s Little Theorem
    c = pow(g, N * (p - 2), p)
    y = h%p
    for j in range(N):
        # y = (h * pow(c, j, p)) % p
        if y in t: 
            return j * N + t[y]
        y = (y*c) % p
    return None
