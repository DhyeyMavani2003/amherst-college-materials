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
 