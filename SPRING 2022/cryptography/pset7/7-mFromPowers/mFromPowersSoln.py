def mFromPowers(N,e,f,me,mf):
    # Your code here
    r,u,v = bezout(e,f)
    if (u>0):
        U = pow(me,u,N)
    if (v>0):
        V = pow(mf,v,N)
    if (u<0):
        u *= -1
        U = pow(modinv(me,N),u,N)
    if (v<0):
        v *= -1
        V = pow(modinv(mf,N),v,N)
    m = (U*V)%N
    return m

def bezout(a,b):
    # Your code here
    #if (a<b):
    #    a,b=b,a
    
    r_0,u_0,v_0 = a,1,0
    r_1,u_1,v_1 = b,0,1
    while r_1 > 0:
        q = r_0 // r_1
        r_0,r_1 = r_1, (r_0 % r_1)
        u_0,u_1 = u_1, (u_0 - q * u_1)
        v_0,v_1 = v_1, (v_0 - q * v_1)     
    
    return r_0,u_0,v_0

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

