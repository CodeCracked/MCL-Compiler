#> mcl:math/float/32/decompose/main
#   Return the exponent and significand of mcl.math.io.P0
##
# @params
#   mcl.math.io.P0
#       32-bit float
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
##

# extract the leftmost bit (sign), leave R0 as 31-bits
scoreboard players set R0 mcl.math.io 0
scoreboard players operation R1 mcl.math.io = P0 mcl.math.io
execute if score R1 mcl.math.io matches ..-1 run function mcl:math/float/32/decompose/extract_sign


scoreboard players operation R2 mcl.math.io = R1 mcl.math.io

# slice the significand off
scoreboard players operation R1 mcl.math.io /= 8388608 mcl.math.constant
# sign the exponent
scoreboard players remove R1 mcl.math.io 127

# slice the exponent off
scoreboard players operation R2 mcl.math.io %= 8388608 mcl.math.constant