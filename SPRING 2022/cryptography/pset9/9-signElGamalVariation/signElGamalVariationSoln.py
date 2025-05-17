import random
def signElGamalVariation(p,g,a,d):
    # Your code here
    k = random.randint(0,p-1)
    s1 = pow(g,k,p)
    s2 = (a*d - k*s1)%(p-1)
    return (s1,s2)

# This function can be used to check if your made a valid signature
def checkAnswer(p,g,a,d,s1,s2):
    A = pow(g,a,p)
    isValid = ((pow(s1,s1,p) * pow(g,s2,p) - pow(A,d,p))%p == 0)
    if isValid:
        print("This is a valid signature!")
    else:
        print("Invalid signature.")
