#> mcl:math/float/32/compare/greater_equal/main
#   Check whether the first number is greater or equal to the second
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#   mcl.math.io.P[3, 4, 5]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R0
#       0 if false, 1 if true
#
scoreboard players set R0 mcl.math.io 0
execute if score P0 mcl.math.io matches 0 if score P3 mcl.math.io matches 1 run scoreboard players set R0 mcl.math.io 1
execute if score P0 mcl.math.io = P3 mcl.math.io run function mcl:math/float/32/compare/greater_equal/branch0