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
