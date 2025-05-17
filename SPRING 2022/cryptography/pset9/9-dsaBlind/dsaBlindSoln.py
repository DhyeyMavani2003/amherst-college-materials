import random
def dsaBlind(p,q,g,A):
    # Your code here
    i = random.randint(1,p-1)
    j = random.randint(1,p-1)
    s1 = pow(g,i,p)*pow(A,j,p)%p
    while (modinv(j,q) == None):
            j = random.randint(1,p-1)
    s2 = s1*modinv(j,q)%q
    d = s2*i%q
    while not verifyDSA(p,q,g,A,d,s1,s2):
        i = random.randint(1,p-1)
        j = random.randint(1,p-1)
        while (modinv(j,q) == None):
            j = random.randint(1,p-1)
        s1 = pow(g,i,p)*pow(A,j,p)%p    
        s2 = s1*modinv(j,q)%q
        d = s2*i%q
    
    return d,s1,s2

def modinv(a,m):
    prevR, prevV = m,0
    curR, curV = a,1
    while curR != 0:
        q = prevR//curR
        nextR = prevR - q*curR
        nextV = prevV - q*curV
        prevR, prevV = curR, curV
        curR, curV = nextR, nextV
    if prevR != 1:
        return None
    return (prevV%m)

def verifyDSA(p,q,g,A,d,s1,s2):
    # Your code here
    if (modinv(s2,q) == None):
        return False
    return (s1%q) == (((pow(g,(modinv(s2,q)*d%q),p)*pow(A,(modinv(s2,q)*s1%q),p))%p)%q)
    # Should return True or return False