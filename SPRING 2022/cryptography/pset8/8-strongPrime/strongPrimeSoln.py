import random
'''
def strongPrimeLong(qbits,pbits):
    # Your code here
    q1 = pickQ(qbits)
    q2 = pickQ(qbits)
    while (q1 == q2):
        q2 = pickQ(qbits)
    p = pickQ(pbits)
    bool1 = ((p-1) >= (2**(pbits-1) -1) and (p-1) <= (2**pbits -2) and (p-1)%q1 == 0)
    bool2 = ((p+1) >= (2**(pbits-1) +1) and (p+1) <= (2**pbits +0) and (p+1)%q2 == 0)
    while (not(bool1 and bool2)):
        p = pickQ(pbits)
    return q1, q2, p

# q1: q1 | (p-1)
# p = k * q1 + 1

# q2: q2 | (p+1)
# p = k_ * q2 - 1
# make sure that (p+1)%q2 == 0

# kq1 cong -2 mod q2

def strongPrimeNope(qbits,pbits):
    q1 = pickQ(qbits)
    q2 = pickQ(qbits)
    while (q1 == q2):
        q2 = pickQ(qbits)
        
    k_low = 1 + (2**(pbits-1) - 1)//q1
    k_high = (2**pbits - 1)//q1
    p_low = q1*k_low + 1
    p_high = q1*k_high + 1

    k = k_low
    p = q1*k + 1
    while (isPrime(p) != True and (p+1)%q2 != 0):
        q2 = pickQ(qbits)
        k = random.randint(k_low,k_high)
        p = q1*k + 1
        
    return q2, q1, p

def strongPrimeOK(qbits, pbits):
    q1 = pickQ(qbits)
    q2 = pickQ(qbits)
    while (q1 == q2):
        q2 = pickQ(qbits)
        
    a = crt(1,q1,-1,q2)
    prod = q1*q2
    
    k_low = 1 + (2**(pbits-1) - 1)//prod
    k_high = (2**pbits - 1)//prod
    p_low = prod*k_low + 1
    p_high = prod*k_high + 1

    k = k_low
    p = prod*k + 1
    while (isPrime(p) != True):
        k = random.randint(k_low,k_high)
        p = prod*k + 1
    
    return q2, q1, p
'''
def strongPrime(qbits, pbits):
    q1 = pickQ(qbits)
    q2 = pickQ(qbits)
    while (q1 == q2):
        q2 = pickQ(qbits)

    a = linearCong(q1, -2, q2)[0]
    
    k_low = 1 + (2**(pbits-1) - 1)//q1
    k_high = (2**pbits - 1)//q1
    
    l_low = 1 + (k_low - a)//q2
    l_high = (k_high - a)//q2
    

    k = k_low
    p = q1*k + 1
    while (isPrime(p) != True):
        l = random.randint(l_low, l_high)
        k = a + l*q2
        p = q1*k + 1
    
    return q1, q2, p
    
    
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
'''
def crt(a1,m1,a2,m2):
    # Your code here
    # m1_inv = pow(m1,m2-2,m2)
    # x = (a1 + ((m1_inv * (a1 - a2) % m2)*m1)%(m1*m2)) % (m1*m2)
    gcd_m1_m2,u,v = bezout(m1,m2)
    x = ((m2*v*a1) + (m1*u*a2))%(m1*m2)
    return x

def bezout(a,b):
    # Your code here
    #if (a<b):
    #    a,b=b,a
    
    r_0,u_0,v_0 = a,1,0
    r_1,u_1,v_1 = b,0,1
    while r_1 > 0:
        q = r_0 // r_1
        r_0,r_1 = r_1, (r_0 % r_1)
        u_0,u_1 = u_1, (u_0 - q * u_1)
        v_0,v_1 = v_1, (v_0 - q * v_1)     
    
    return r_0,u_0,v_0
'''
def ExtendedEuclidAlgo(a, b):

    if a == 0 :
        return b, 0, 1
         
    gcd, x1, y1 = ExtendedEuclidAlgo(b % a, a)
    
    x = y1 - (b // a) * x1
    y = x1
     
    return gcd, x, y

def linearCong(m, b, N):
     
    m = m % N
    b = b % N
    u = 0
    v = 0
    
    d, u, v = ExtendedEuclidAlgo(m, N)
    
    if (b % d != 0):
        return None

    x0 = (u * (b // d)) % N
    if (x0 < 0):
        x0 += N
        
    M = N//d
    r = x0%(M)
    
    return r,M
 