import json, sys, ppFactorSoln
soln = ppFactorSoln.ppFactor
testFile = 'samples.json'
default_timelimit = 1

# Helper function to convert lists to tuples (recursively)
def tuplify(a):
    if isinstance(a,list) or isinstance(a,tuple):
        return tuple( map(tuplify, a) )
    else:
        return a

# Load tests from testFile
with open(testFile) as inf:
    tests = json.load(inf)

# Extract test numbers from command line arguments
# If none are provided, all test cases are run
casenums = []
for arg in sys.argv[1:]:
    try:
        n = int(arg)
        assert(1 <= n <= len(tests))
        casenums += [n]
    except:
        print('Invalid testcase: %s'%arg)
if len(casenums) == 0:
    casenums = range(1,len(tests)+1)

for casenum in casenums:
    test = tests[casenum-1]
    inputTuple = tuplify(test['input'])
    keyTuple = tuplify(test['key'])
    if 'timelimit' in test:
        timelimit = test['timelimit']
    else:
        timelimit = default_timelimit
    print('Running testcase %2d (%s)'%(casenum,test['name']))
    print('\tInput:',*inputTuple)
    print('\tTime limit: %ds (not enforced by this script)'%timelimit)
    result = soln( *test['input'] ) # Replace "soln" with the function name
    print ('\tReceived:',tuplify(result))
    print ('\tExpected:',keyTuple)
