def extractKey(p,g,A,d1,s11,s12,d2,s21,s22):
    # Your code here
    x = ((s21*s12) - (s11*s22))%(p-1)
    y = ((d2*s12) - (d1*s22))%(p-1)
    # xa = y (mod p - 1)
    a = linearCong(x,y,p-1)[0]
    m = linearCong(x,y,p-1)[1]
    if (pow(g,a,p) == A):
        return a
    
    while (pow(g,a,p) != A):
        a += m
        if (pow(g,a,p) == A):
            return a
    return None

def ExtendedEuclidAlgo(a, b):

    if a == 0 :
        return b, 0, 1
         
    gcd, x1, y1 = ExtendedEuclidAlgo(b % a, a)
    
    x = y1 - (b // a) * x1
    y = x1
     
    return gcd, x, y

def linearCong(m, b, N):
     
    m = m % N
    b = b % N
    u = 0
    v = 0
    
    d, u, v = ExtendedEuclidAlgo(m, N)
    
    if (b % d != 0):
        return None

    x0 = (u * (b // d)) % N
    if (x0 < 0):
        x0 += N
        
    M = N//d
    r = x0%(M)
    
    
    
    return r,M
 