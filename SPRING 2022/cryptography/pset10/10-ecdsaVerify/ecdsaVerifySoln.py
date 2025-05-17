# This was a rough one. 
# You gotta look at the documentation as mentioned in the problem.
# Be sure to copy correctly because you might messup with some space characters and stuff.
# The method is standard once you figure out how to import stuff correcctly into the variables.

def ecdsaVerify(V,d,s1,s2):
    # Your code here
    # Either return True or return False
    
    p = int(39402006196394479212279040100143613805079739270465446667948293404245721771496870329047266088258938001861606973112319)
    A = (-3)
    
    b = "b3312fa7e23ee7e4988e056be3f82d19181d9c6efe8141120314088f5013875ac656398d8a2ed19d2a85c8edd3ec2aef"
    B = int(b, 16)
    
    gx= "aa87ca22be8b05378eb1c71ef320ad746e1d3b628ba79b9859f741e082542a385502f25dbf55296c3a545e3872760ab7"
    Gx = int(gx, 16)
    gy= "3617de4a96262c6f5d9e98bf9292dc29f8f41dbd289a147ce9da3113b5f0b8c00a60b1ce1d7e819d7a431d7c90ea0e5f"
    Gy = int(gy, 16)
    G = (Gx,Gy)
    q = int(39402006196394479212279040100143613805079739270465446667946905279627659399113263569398956308152294913554433653942643)
    w1 = (modinv(s2,q)*d)%q
    w2 = (modinv(s2,q)*s1)%q
    return s1 == ((ecAdd(ecMult(w1,G,A,B,p),ecMult(w2,V,A,B,p),A,B,p)[0]%p)%q)


def ecMult(n,P,A,B,p):
    # Your code here
    if (n == 0):
        return 0
    if (n == 1):
        return P
    
    rec = ecMult(n//2,P,A,B,p)
    
    if n%2 == 0:
        return ecAdd(rec,rec,A,B,p)
    else:
        new_rec = ecAdd(rec,P,A,B,p)
        return ecAdd(rec,new_rec,A,B,p)
    
    
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

    
    m = (yq - yp)*modinv((xq - xp)%p,p)%p
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
