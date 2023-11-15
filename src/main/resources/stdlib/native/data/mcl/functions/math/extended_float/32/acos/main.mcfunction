#> mcl:math/extended_float/32/acos/main
#   Return the arccos of the 32-bit float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#
# @modifies mcl.math.temp.[0..19]

scoreboard players add P0 mcl.math.io 1
execute if score P0 mcl.math.io matches 2 run scoreboard players set P0 mcl.math.io 0
function mcl:math/extended_float/32/asin/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 0
scoreboard players set P5 mcl.math.io 4788187

function mcl:math/float/32/add/main