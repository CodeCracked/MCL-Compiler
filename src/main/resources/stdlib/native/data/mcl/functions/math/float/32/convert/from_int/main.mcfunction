#> mcl:math/float/32/convert/from_int/main
#   Return a 32-bit float representation of an integer
##
# @params
#   mcl.math.io.P0
#       32-bit signed integer
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand

scoreboard players set R0 mcl.math.io 0
execute if score P0 mcl.math.io matches ..-1 run function mcl:math/float/32/convert/from_int/negative

scoreboard players operation P2 mcl.math.io = P0 mcl.math.io
function mcl:math/float/32/convert/from_int/b/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io

scoreboard players remove P2 mcl.math.io 8388608

scoreboard players operation R1 mcl.math.io = P1 mcl.math.io
scoreboard players operation R2 mcl.math.io = P2 mcl.math.io