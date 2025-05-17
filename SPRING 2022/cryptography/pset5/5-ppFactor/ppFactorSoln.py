# Write a function ppFactor(N) which accepts an integer N >= 2, and returns a list of the prime powers (all powers of different primes) factoring N, in any order. For example, if N = 12 the function should return either [4,3] or [3,4]. The integer N may be quite large (up to 1024 bits), but you may assume that all of the prime-power factors are 16 bits or smaller.

import math
'''
def ppFactors(n):
    # Your code here: make a list "factors"
    factors = []
    for p in range(2,2**16+1):
        if (n%p==0):
            j = 0
            while (n%p == 0):
                n /= p
                j += 1
            factors.append(p**j)
    if n == 1:
        return factors
    else:
        factors.append(n)
        return factors
'''

def ppFactor(p):
    
    d, primeFactors = 2, []
    while d*d <= p:
        j = 0
        while (p % d) == 0:
            p //= d
            j += 1
        if j>0:
            primeFactors.append(d**j)
        d += 1
    if p > 1:
        primeFactors.append(p)
    return primeFactors
