def analyzeElgamal(g,p,A,c11,c12,m1,c21,c22):
    # Your code here
    m2 = (m1*c22*modinv(c12,p)%p)
    return m2

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
