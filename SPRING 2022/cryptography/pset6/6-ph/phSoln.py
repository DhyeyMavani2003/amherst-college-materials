# Make sure to include your code for the other three functions on this PSet here!

import math

def ph(g,h,p):
    # Your code here
    ls_p = ppFactor(p-1)
    g_ = 0
    h_ = 0
    x_list = [(bsgsBoundedOrder(pow(g,((p-1)//pr),p),pow(h,((p-1)//pr),p),p,pr),pr) for pr in ls_p]
    crt = crtList(x_list)
    return crt[0]

def ppFactor(p):
    
    d, primeFactors = 2, []
    while d*d <= p:
        j = 0
        while (p % d) == 0:
            p //= d
            j += 1
        if j>0:
            primeFactors.append(d**j)
        d += 1
    if p > 1:
        primeFactors.append(p)
    return primeFactors

def bsgsBoundedOrder(g,h,p,q):
    # your code here
    N = int(math.ceil(math.sqrt(q))) 
    t = dict()
    # Baby step.
    b = 1
    for i in range(N):
        #t[pow(g, i, p)]=i
        t[b]=i
        b = (b*g)%p
    # Fermat’s Little Theorem
    c = pow(g, N * (p - 2), p)
    y = h%p
    for j in range(N):
        # y = (h * pow(c, j, p)) % p
        if y in t: 
            return j * N + t[y]
        y = (y*c) % p
    return None

def crtList(ls):
    # Your code here
    a1 = ls[0][0]
    m1 = ls[0][1]
    for i in range(1,len(ls)):
        a1 = crt(a1,m1,ls[i][0],ls[i][1])
        m1 = m1*ls[i][1]
    return (a1,m1)

def crt(a1,m1,a2,m2):
    # Your code here
    # m1_inv = pow(m1,m2-2,m2)
    # x = (a1 + ((m1_inv * (a1 - a2) % m2)*m1)%(m1*m2)) % (m1*m2)
    gcd_m1_m2,u,v = bezout(m1,m2)
    x = ((m2*v*a1) + (m1*u*a2))%(m1*m2)
    return x

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