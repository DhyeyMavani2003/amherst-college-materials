import random as rand

def findOrderQ(q,p):
    # Your code here
    if (p-1)%q != 0:
        return None
    '''
    for g in range(2,p):
        if (pow(g,(p-1)/q,p)==1):
            return g
            
    # Should either return g or return None.
    return None
    '''
    while True:
        g = rand.randint(2,p)
        if (pow(g,(p-1)//q,p)!=1):
            return pow(g,(p-1)//q,p)
    
'''
def modpow(a,n,m):
    
    h=1
    b=a
    f=n
    if f<0:
        b = modinv(a,m)
        f *= -1
    while f!= 0:
        if f%2 == 1:
            h = h*b%m
        b = b*b%m
        f = f//2
    return h
    

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
'''