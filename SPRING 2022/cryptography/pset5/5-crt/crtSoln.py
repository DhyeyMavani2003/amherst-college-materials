# Suppose that you are given two integers m_1, m_2 with gcd(m_1,m_2) = 1, and two integers a_1, a_2. Write a function crt(a1,m1,a2,m2) that efficiently determines the unique integer x such that x = a_1 mod{m_1} and x = a_2 mod{m_2} and 0 <= x < m_1 m_2. The fact that x exists and is unique comes from the Chinese Remainder Theorem.

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