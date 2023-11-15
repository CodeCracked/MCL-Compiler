#> mcl:math/bitwise/32/unsigned/divmod/main
#   Return the unsigned quotient and modulo of mcl.math.io.P[0, 1]
##
# @params
#   mcl.math.io.P[0, 1]
#       Two 32-bit integers
# @returns
#   mcl.math.io.R[0, 1]
#       Two 32-bit integers, quotient and modulo
##
# mcl.math.io.R0 = div
# mcl.math.io.R1 = mod
##

scoreboard players operation R0 mcl.math.io = P0 mcl.math.io
execute if score P1 mcl.math.io matches 1.. run function mcl:math/bitwise/32/unsigned/divmod/b0
execute if score P1 mcl.math.io matches ..0 run function mcl:math/bitwise/32/unsigned/divmod/b1