def gcd(a,b):
    # Delete this comment and replace with your code.
    while b != 0:
        a,b=b,a%b
    return a
