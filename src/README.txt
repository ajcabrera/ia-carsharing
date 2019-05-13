Instructions to execute the program:
------------------------------------------------------------------------------------------------
Command:

For Hill Climbing:
# java -jar ia-carsharing.jar #passengers #drivers seed HC cfg

For Simulated Annealing:
# java -jar ia-carsharing.jar #passengers #drivers seed SA cfg Iterations iters/Tstep K lambda
------------------------------------------------------------------------------------------------
Info cfg:
cfg is a string of capital letters. Options:

Operators:

'S': If cfg contains 'S' it will use the operator 'SWAP'.
'M': If cfg contains 'M' it will use the operator 'MOVE'.
'R': If cfg contains 'R' it will use the operator 'MOVECONDUCTOR'.

Initializing function:

'E': If cfg contains 'E' it will use 'equitableRandomInit' as initializing function.
'D': If cfg contains 'E' it will use 'InicializacionDesequilibrada' as initializing function.

Output:

'C': If cfg contains 'C' it will print the extended final board (The path of every conductor).
------------------------------------------------------------------------------------------------
Examples:

Hill Climbing execution:
# java -jar ia-carsharing.jar 200 100 38 HC SMREC

Simulated Annealing execution:
# java -jar ia-carsharing.jar  200 100 1234 SA SMREC 100000 1000 10 0.0000001

