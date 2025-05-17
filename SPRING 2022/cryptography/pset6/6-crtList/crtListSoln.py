# Write a function crtList(ls) that takes a list ls of pairs (a_i, m_i) of integers, with any two of the values m_i relatively prime, and returns a pair (a,m) such that the system of congruences x = a_i mod{m_i} is equivalent the single congruence x = a mod{m}, and 0 <= a < m (i.e. a is reduced modulo m).

# For example, crtList( [(2,3), (3,5), (0,2)] ) should return (8, 30), since the system of three congruences x = 2 mod{3}, x = 3 mod{5}, x = 0 mod{2} is equivalent to the single congruence x = 8 mod{30} 

# The integer a should be reduced modulo m, i.e. 0 <= a < m. The moduli m_i will be integers up to 256 bits in length, and the list will contain up to 128 entries.

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