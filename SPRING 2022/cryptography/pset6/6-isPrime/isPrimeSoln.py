# Implement the Miller-Rabin primality test (or another primality test of your choice): write a function isPrime(n) that returns True or False according to whether or not n is prime. The starter code will also define a checkList that applies your function to a list of integers; you do not need to modify that part. Each test case will give your function ten integers of the same size; to pass the test case your function must give the correct answer for all ten.

import random
'''
def isPrime(n):
    # Your code here
    # Return True or False
    if (n == 2):
        return True
    elif (n%2 == 0 and n!= 2):
        return False
    elif (n == 3 or n == 5):
        return True
    else:
        for x in range(100):
            r_num = random.randint(1,n-1)
            if (pow(r_num,n-1,n) != 1):
                return False
            else:
                j = 0
                n_ = n-1
                while (n_ % 2) == 0:
                    n_ /= 2
                    j += 1
                q = n_//(2**j)
                for i in range(j):
                    power = int(q*(2**i))
                    if (pow(r_num, power, n) == -1):
                        return False
                return True
    return True
'''
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

# You do not have to modify this line
checkList = lambda ls : tuple(map(isPrime,ls))
