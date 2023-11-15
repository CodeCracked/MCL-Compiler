#> mcl:math/float/32/absolute_value/main
#   Return the absolute value of a 32-bit float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
##

scoreboard players set R0 mcl.math.io 0
scoreboard players operation R1 mcl.math.io = P1 mcl.math.io
scoreboard players operation R2 mcl.math.io = P2 mcl.math.io