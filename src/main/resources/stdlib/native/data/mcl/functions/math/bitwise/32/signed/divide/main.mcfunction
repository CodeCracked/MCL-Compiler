#> mcl:math/bitwise/32/signed/divide/main
#   Return the signed quotient of mcl.math.io.P[0, 1]
##
# @params
#   mcl.math.io.P[0, 1]
#       Two 32-bit integers
# @returns
#   mcl.math.io.R0
#       32-bit integer
##

scoreboard players operation R0 mcl.math.io = P0 mcl.math.io
scoreboard players operation R0 mcl.math.io /= P1 mcl.math.io