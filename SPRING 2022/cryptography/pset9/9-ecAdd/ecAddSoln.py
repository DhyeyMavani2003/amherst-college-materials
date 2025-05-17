def ecAdd(P,Q,A,B,p):
    # Your code here
    # Should either return 0 (for point at infinity)
    #   or return (x,y), where x,y are in Z / p Z
    if (P == 0):
        return Q
    if (Q == 0):
        return P
    
    xp,yp = P
    xq,yq = Q
    
    if (xp == xq) and (yp+yq)%p == 0:
        return 0
    
    if P == Q:
        m = (3*xp*xp + A)*modinv(2*yp,p)%p
        h = (yp - m*xp)%p
        x = (m**2 - 2*xp)%p
        y = (-m*x-h)%p
        return (x,y)
    
    m = (yq - yp)*modinv((xq - xp),p)%p
    h = (yp - m*xp)%p
    x = (m**2 - xp - xq)%p
    y = (-m*x - h)%p
    return (x,y)
    
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
    