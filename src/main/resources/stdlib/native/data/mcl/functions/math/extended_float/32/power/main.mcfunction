#> mcl:math/extended_float/32/power/main
#   Return the first 32-bit float raised to the power of the second.
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
scoreboard players operation 0 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 1 mcl.math.temp = P1 mcl.math.io
scoreboard players operation 2 mcl.math.temp = P2 mcl.math.io

scoreboard players operation 3 mcl.math.temp = P3 mcl.math.io
scoreboard players operation 4 mcl.math.temp = P4 mcl.math.io
scoreboard players operation 5 mcl.math.temp = P5 mcl.math.io

scoreboard players operation P0 mcl.math.io = P3 mcl.math.io
scoreboard players operation P1 mcl.math.io = P4 mcl.math.io
scoreboard players operation P2 mcl.math.io = P5 mcl.math.io

function mcl:math/extended_float/32/float_type/main

scoreboard players operation P0 mcl.math.io = 0 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 1 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 2 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 3 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 4 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 5 mcl.math.temp

scoreboard players set 0 mcl.math.temp 1
execute if score R0 mcl.math.io matches 2 run function mcl:math/extended_float/32/power/not_integer_exponent/main
execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/integer_exponent/main