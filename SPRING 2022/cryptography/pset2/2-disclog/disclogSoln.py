"""
Write a function disclog(g,h,p) that solves the discrete logarithm problem in a naive way (quickly enough to work in less than 1 second if p is a 20-bit prime). Multiple answers are possible (we’ll discuss this later); an answer n will be marked correct as long as g^n ≡ h (mod p). To allow you to see exactly where the naive approach becomes too slow (or, if you’re up for it, to allow you to try to implement better methods), the test bank will include cases where p ranges up to 40 bits, but you will receive full credit as long as your code solves the test cases up to 20-bit primes. (Later, we’ll discuss and implement an
algorithm that can solve the entire test bank).
"""
'''
# basically find n such that g^n ≡ h (mod p), here g, h, p are inputs
# g^n when divided by p leaves remainder h

def disclog(g,h,p):
    # Your code here
    n = 1
    while g**n%p != h:
        n += 1
    return n
'''

def disclog(g,h,p):
    # Your code here
    r = g%p
    n = 1
    G = r
    while G%p != h:
        G *= r
        G = G%p
        n += 1
    return n
