#> mcl:math/float/32/subtract/main
#   Return the difference of two 32-bit floats
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#   mcl.math.io.P[3, 4, 5]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
##
# @modifies mcl.math.temp.[0..10]

scoreboard players add P3 mcl.math.io 1
execute if score P3 mcl.math.io matches 2 run scoreboard players set P3 mcl.math.io 0

function mcl:math/float/32/add/main