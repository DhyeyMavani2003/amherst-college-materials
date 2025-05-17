import random

def dh(g,p,B):
    # Your code here
    a = random.randint(2,p-1)
    A = modpow(g,a,p)
    S = modpow(B,a,p)
    
    return A,S

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