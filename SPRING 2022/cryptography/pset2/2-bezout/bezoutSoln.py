'''
Write a function bezout(a,b) that takes two positive integers a, b and returns three integers g, u, v, where g = gcd(a, b) and au + bv = g. The numbers in the test bank will range up to 256 bits in size, but there will also be smaller case that can be solved by a naive approach. I recommend that you implement the extended Euclidean algorithm (either the way we outlined it in class, or following one of the methods in the text), but other methods may also work.
'''
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
