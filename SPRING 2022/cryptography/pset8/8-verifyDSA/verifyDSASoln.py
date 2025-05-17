def verifyDSA(p,q,g,A,d,s1,s2):
    # Your code here
    return (s1%q) == (((pow(g,(modinv(s2,q)*d),p)*pow(A,(modinv(s2,q)*s1),p))%p)%q)
    # Should return True or return False

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