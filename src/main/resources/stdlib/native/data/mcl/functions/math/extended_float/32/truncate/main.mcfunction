#> mcl:math/extended_float/32/truncate/main
#   Returns a truncated float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#

scoreboard players operation R0 mcl.math.io = P0 mcl.math.io
scoreboard players operation R1 mcl.math.io = P1 mcl.math.io
scoreboard players operation R2 mcl.math.io = P2 mcl.math.io

function mcl:math/extended_float/32/truncate/b/main

execute if score P1 mcl.math.io matches 1..22 run scoreboard players operation R2 mcl.math.io -= P2 mcl.math.io
execute if score P1 mcl.math.io matches ..0 run function mcl:math/extended_float/32/truncate/zero