def rsaThreePrimes(p,q,r,e,c):
    # your code here
    d = modinv(e,(p-1)*(q-1)*(r-1))
    N = p*q*r
    m = pow(c,d,N)
    return m

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
