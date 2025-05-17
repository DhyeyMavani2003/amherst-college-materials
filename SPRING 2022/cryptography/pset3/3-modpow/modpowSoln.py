
def modpow(a,n,m):
    # Your code here
    
    if n == 0:
        return 1
    elif n == -1:
        return modinv(a,m)
    else:
        rec = modpow(a,n//2,m)
        if n%2 == 0:
            return rec*rec%m
        else:
            return rec*rec*a%m
    
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
    
'''
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