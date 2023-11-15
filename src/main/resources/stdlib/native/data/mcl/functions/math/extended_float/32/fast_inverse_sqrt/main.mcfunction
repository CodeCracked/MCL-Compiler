#> mcl:math/extended_float/32/fast_inverse_sqrt/main
#   Return the inverse square root of the 32-bit float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#
# @modifies mcl.math.temp.[11..16]

scoreboard players set 11 mcl.math.temp 1
execute if score P0 mcl.math.io matches 1 run function mcl:math/extended_float/32/fast_inverse_sqrt/nan
execute if score P0 mcl.math.io matches 0 if score P1 mcl.math.io matches -127 if score P2 mcl.math.io matches 0 run function mcl:math/extended_float/32/fast_inverse_sqrt/inf
execute if score 11 mcl.math.temp matches 1 run function mcl:math/extended_float/32/fast_inverse_sqrt/positive