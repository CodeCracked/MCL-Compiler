#> mcl:math/float/32/recompose/main
#   Return the 32-bit floating-point representation using
#   mcl.math.io.P0 as a sign, mcl.math.io.P1 as an (signed) exponent and
#   mcl.math.io.P2 as a (unsigned) significand
#   Essentially the inverse of mcl:math/float/32/decompose/main
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R0
#       32-bit float
##

# set mcl.math.io.R0 to the exponent
scoreboard players operation R0 mcl.math.io = P1 mcl.math.io
# add 127 to remove sign
scoreboard players add R0 mcl.math.io 127
# shift mcl.math.io.R0 to put it in the right place
scoreboard players operation R0 mcl.math.io *= 8388608 mcl.math.constant

# add the significand to exponent to yield last 31-bits of the float
scoreboard players operation R0 mcl.math.io += P2 mcl.math.io

# add the sign (add 2^31) if needed
execute if score P0 mcl.math.io matches 1 run function mcl:math/float/32/recompose/replace_sign