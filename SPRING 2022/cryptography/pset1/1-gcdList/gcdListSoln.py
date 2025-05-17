'''
Write a function gcdList(ls) which takes a list of positive integers, whose sizes may be as large as 1024 bits, and returns the greatest common divisor of the entire list. Half of the test cases will consists of lists of exactly two elements, so you can begin by writing a function to efficiently compute the gcd of two numbers, which will receive at least half of the points.
Submit your solution to the Gradescope assignment “PSet 1 gcdList.”
'''
"""
def gcdList(ls):
    res = ls[0]
    for e in ls[1:]:
        if res < e:
            tmp = res
            res = e
            e = tmp
        while e != 0:
            tmp = e
            e = res % e
            res = tmp
    return res 
"""
def gcdList(ls):
    # Your code here; compute the gcd g of the list ls
    g = ls[0]
    for ele in ls:
        g = gcd(g,ele)
    return g

def gcd(a,b):
    if a == 0:
        return b
    if b == 0:
        return a
    
    if a <= b:
        return gcd(a,b%a)
    elif a >= b:
        return gcd(b,a%b)
    else:
        return a
        
