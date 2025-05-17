
def break1TRU(q,h,e):
    
    # DEBUG: Example from the Textbook: 
    # Expected Output: (2280, -1001, -1324, -2376)
    # print(gaussMethod(1446860,1420089,6513996,6393464))
    
    # Defining the vector parameters to simulate the 1TRU conditions
    v1x = 1
    v1y = h
    v2x = 0 
    v2y = q 
    
    # Storing the reduced vectors after performing the gauss lattice reduction
    v1x_reduced,v1y_reduced,v2x_reduced,v2y_reduced = gaussMethod(v1x,v1y,v2x,v2y)
    
    # Since now we have the reduced vectors,
    # We can proceed breaking 1TRU
    # Using the smaller vector of the two
    # (which is reduced vector no. 1 always)
    
    # x and y comps of reduced v1 would work as f and g
    f = abs(v1x_reduced)
    g = abs(v1y_reduced)
    
    # calculating inverse of f
    f_inv = modinv(f,g)
    
    # calculating a mod q (as per the decryption method in 1TRU)
    a = (f*e)%q
    
    # calculating b mod g (as per the decryption method in 1TRU)
    b = (f_inv*a)%g
    
    # return b as it will be the message mod g
    return b

# Gauss lattice space reduction algorithm
def gaussMethod(v1x,v1y,v2x,v2y):
    while True:
        if (v1x*v1x + v1y*v1y > v2x*v2x + v2y*v2y):
            v1x,v2x = v2x,v1x
            v1y,v2y = v2y,v1y
        m = round((v1x*v2x +v1y*v2y)/(v1x*v1x + v1y*v1y))
        if m == 0:
            return v1x,v1y,v2x,v2y
        v2x = v2x - m*v1x
        v2y = v2y - m*v1y
    return None

# modular inverse function
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