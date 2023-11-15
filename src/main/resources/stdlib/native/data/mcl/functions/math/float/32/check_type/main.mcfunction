#> mcl:math/float/32/check_type/main
#   Return what a decomposed float is.
#   0 = NaN, 1 = Inf, 2 = -Inf, 3 = 0, 4 = -0, 5 = Proper Number
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit (signed) exponent, 32-bit significand
# @returns
#   mcl.math.io.R0
#       32-bit type as specified above (it would be
#       nice if we could save bits since we really
#       only need 2 but are using 32 :( )
##

scoreboard players set R0 mcl.math.io 5
execute if score P1 mcl.math.io matches 128 run function mcl:math/float/32/check_type/dne
execute if score P1 mcl.math.io matches -127 if score P2 mcl.math.io matches 0 run function mcl:math/float/32/check_type/zero