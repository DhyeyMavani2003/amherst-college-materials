def makeQP(qbits,pbits):
    # Your code here
    return q,p

# This function can be used to test whether your function is working.
# You can use this function as-is to make sure you have the right bit lengths.
# Once you provide an ``isPrime'' function, uncomment the last 8 lines, which will check that your output is prime.
def checkQP(q,p,qbits,pbits):
    if q.bit_length() == qbits:
        print('q has the correct number of bits.')
    else:
        print('q is %d bits long, but should be %d bits long'%(q.bit_length(),qbits))

    if p.bit_length() == pbits:
        print('p has the correct number of bits.')
    else:
        print('p is %d bits long, but should be %d bits long'%(p.bit_length(),pbits))

    if p%q == 1:
        print('p is 1 mod q, as desired.')
    else:
        print('p is not 1 mod q.')

    # Uncomment these lines once you provide an isPrime function.
    #if isPrime(q):
    #    print('q is prime.')
    #else:
    #    print('q is not prime.')
    #if isPrime(p):
    #    print('p is prime.')
    #else:
    #    print('p is not prime.')
