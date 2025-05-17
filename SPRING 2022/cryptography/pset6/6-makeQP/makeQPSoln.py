import random
def makeQPs(qbits,pbits):
    # Your code here
    q = pickQ(qbits)
    k_low = 1 + (2**(pbits-1) - 1)//q
    k_high = (2**pbits - 1)//q
    p_low = q*k_low + 1
    p_high = q*k_high + 1
    if ((p_low.bit_length == pbits) and 
        (p_high.bit_length == qbits) and 
        isPrime(q) == False
       ):
        makeQP(qbits,pbits)
    k = k_low
    p = q*k + 1
    while (isPrime(p) != True):
        k = random.randint(k_low,k_high)
        p = q*k + 1
    
    return q,p
    
    #return q,p
# choose q
# choose p = 1 + kq where 2^(pbits-1) <= p <= 2^pbits -1
# math.ceil(2^(pbits-1) - 1/q) <= k <= math.floor(2^pbits -2//q)
# 1 + low <= k <= 

def makeQP(qbits,pbits):
    q = pickQ(qbits)
    k_low = 1 + (2**(pbits-1) - 1)//q
    k_high = (2**pbits - 1)//q
    p_low = q*k_low + 1
    p_high = q*k_high + 1
    
    k = k_low
    p = q*k + 1
    while (isPrime(p) != True):
        k = random.randint(k_low,k_high)
        p = q*k + 1
    
    return q,p
    
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

# This function can be used to test whether your function is working.
# You can use this function as-is to make sure you have the right bit lengths.
# Once you provide an ``isPrime'' function, uncomment the last 8 lines, which will check that your output is prime.
def checkQP(q,p,qbits,pbits):
    if q.bit_length() == qbits:
        print('q has the correct number of bits.')
    else:
        print('q is %d bits long, but should be %d bits long'%(q.bit_length(),qbits))

    if p.bit_length() == pbits:
        print('p has the correct number of bits.')
    else:
        print('p is %d bits long, but should be %d bits long'%(p.bit_length(),pbits))

    if p%q == 1:
        print('p is 1 mod q, as desired.')
    else:
        print('p is not 1 mod q.')

    # Uncomment these lines once you provide an isPrime function.
    if isPrime(q):
        print('q is prime.')
    else:
        print('q is not prime.')
    if isPrime(p):
        print('p is prime.')
    else:
        print('p is not prime.')
