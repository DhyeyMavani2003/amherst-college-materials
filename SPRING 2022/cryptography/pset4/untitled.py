def test2(p):
    for a in range(1,10):
        for i in range(0,p):
            print("a is " + str(a) + " a^" + str(i) + "mod(" + str(p) + ") is " + str(a**i%p))
            
def test(p):
    for a in range(2,10):
        count, i = 0, 1
        while a**i%p != 1:
            count += 1
            i += 1
        if count == p-2:
            return a
    return None

        