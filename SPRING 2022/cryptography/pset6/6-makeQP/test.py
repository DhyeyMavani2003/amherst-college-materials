import math
import random

def pickQ(qbits):
    q_b_ = 1<<(qbits-1)
    q_b = 1<<qbits
    q = random.randint(q_b_,q_b - 1)
    while(isPrime(q) != True):
        q = random.randint(q_b_,q_b - 1)
    return q    

def isWitness(a,n):
    k = 0
    q = n - 1
    while q % 2 == 0:
        q = q // 2
        k += 1
    b = pow(a,q,n)
    if b == 1:
        return False
    for i in range(k):
        if b == n - 1:
            return False
        b = b*b%n
    return True

def isPrime(n):
    for x in range(100):
        num = random.randint(1, n-1)
        if isWitness(num,n):
            return False
    return True

def test():
    pbits = 10
    p = pickQ(pbits)

    while ((math.e**(2*((math.log(p))**(1/3))*(math.log(math.log(p)))**(2/3))) < (64/3)):
        pbits += 1
        p = pickQ(pbits)

    return pbits

