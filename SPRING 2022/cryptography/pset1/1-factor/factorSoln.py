'''
Write a function factor(n) which takes a composite integer n (in fact, your may assume that n is a product of two prime numbers) and returns two proper factors p, q with p, q > 1 and pq = n. Your function should be efficient enough to finish in less that one second when n is 40 bits long or less. Half of the test cases will consist of integers 20 bits long or less, so you will receive at least half credit if your implementation can factor these in less than a second each. Submit your solution to the Gradescope assignment “PSet 1 factor.”
'''

def factor(n):
    # Your code here. Find p and q with p,q > 1 and pq = n.
    # The order of p and q does not matter (e.g. if n=35, either 3,5 or 5,3 will be accepted)
    
    if (n%2==0):
        p = 2
        q = n//2
        if (q%6 == 1) or (q%6 == 5) or q == 3:
            return p,q
    else:
        if (n%3==0):
            p = 3
            q = n//3
            if (q%6 == 1) or (q%6 == 5) or q == 2:
                return p,q
        for num in range(5,n+1):
            if (n%num==0)and ((num%6 == 1) or (num%6 == 5)):
                p = num
                q = n//num
                if ((q%6 == 1) or (q%6 == 5)):
                    return p,q
