#> mcl:math/extended_float/32/root/main
#   Return the first 32-bit float-th root of the second 32-bit float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#   mcl.math.io.P[3, 4, 5]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#
# @modifies mcl.math.temp.[0..25]

scoreboard players set 0 mcl.math.temp 1
execute if score P0 mcl.math.io matches 0 if score P1 mcl.math.io matches 1 if score P2 mcl.math.io matches 0 run function mcl:math/extended_float/32/root/sqrt
execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/root/not_sqrt