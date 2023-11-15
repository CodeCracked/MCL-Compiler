#> mcl:math/float/32/negate/main
#   Return the negation of a 32-bit float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
##

scoreboard players operation R0 mcl.math.io = P0 mcl.math.io
scoreboard players operation R1 mcl.math.io = P1 mcl.math.io
scoreboard players operation R2 mcl.math.io = P2 mcl.math.io

scoreboard players add R0 mcl.math.io 1
execute if score R0 mcl.math.io matches 2 run scoreboard players set R0 mcl.math.io 0