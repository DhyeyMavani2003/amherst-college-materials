def decipherNTRU(N,p,q,d,h,f,g,Fp,Fq,e):
    # your code here
    
    # step 1: convolving f and e
    conv = convolveR(f,e,N)
    
    # step 2: centerlifting the convolved (f*e) wrt q to result in a
    a = centerliftR(conv,q,N)
    
    # step 3: convolving a and Fp and storing as m_ temporarily
    m_ = convolveR(a,Fp,N)
    
    # step 4: centerlifting m_ (convolved (a*Fp)) wrt p to find message (m)
    m = centerliftR(m_,p,N)
    
    # finally returning the message (decrypted)
    return m

# Polynomial addition in R
def addR(f,g,N):
    return [f[i]+g[i] for i in range(N)]

# Scalar multiplication in R
def scaleR(c,f,N):
    return [c*f[i] for i in range(N)]

# convolution product in R
def convolveR(f,g,N):
    conv = [0]*N
    for i in range(N):
        for j in range(N):
            conv[(i+j)%N] += f[i]*g[j]
    return conv

# Reduce f(x) modulo p
def reduceR(f,p,N):
    f_reduced_mod_p = [f[i]%p for i in range(N)]
    f = f_reduced_mod_p
    return f

# Centerlift f(x) modulo p
def centerliftR(f,p,N):
    f_reduced_mod_p = [f[i]%p for i in range(N)]
    for i in range(N):
        if (f_reduced_mod_p[i] > p//2):
            f_reduced_mod_p[i] -= p
    return f_reduced_mod_p

