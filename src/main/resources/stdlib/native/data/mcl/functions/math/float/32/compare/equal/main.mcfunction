#> mcl:math/float/32/compare/equal/main
#   Checks for equality between two floating point numbers
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#   mcl.math.io.P[3, 4, 5]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R0
#       0 if numbers are not equal, 1 if they are.
#
scoreboard players set R0 mcl.math.io 1
execute unless score P0 mcl.math.io = P3 mcl.math.io run scoreboard players set R0 mcl.math.io 0
execute unless score P1 mcl.math.io = P4 mcl.math.io run scoreboard players set R0 mcl.math.io 0
execute unless score P2 mcl.math.io = P5 mcl.math.io run scoreboard players set R0 mcl.math.io 0