def relatedkeys(g,p,A,c11,c12,m1,c21,c22):
    # your code here
    for u in range(1,101):
        for v in range(1,101):
            if (c21 == (pow(c11,u,p)*pow(g,v,p))%p):
                return ((c22*pow(m1*modinv(c12,p),u,p)*pow(modinv(A,p),v,p))%p)
                
    return None

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