#> mcl:math/float/32/divide/main
#   Return the quotient of two 32-bit floats
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#   mcl.math.io.P[3, 4, 5]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
##
# @modifies mcl.math.temp.[0..8]

# make a copy of everything
scoreboard players operation 0 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 1 mcl.math.temp = P1 mcl.math.io
scoreboard players operation 2 mcl.math.temp = P2 mcl.math.io

scoreboard players operation 3 mcl.math.temp = P3 mcl.math.io
scoreboard players operation 4 mcl.math.temp = P4 mcl.math.io
scoreboard players operation 5 mcl.math.temp = P5 mcl.math.io

# check types for both
function mcl:math/float/32/check_type/main
scoreboard players operation 6 mcl.math.temp = R0 mcl.math.io

scoreboard players operation P0 mcl.math.io = 3 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 4 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 5 mcl.math.temp

function mcl:math/float/32/check_type/main
scoreboard players operation 7 mcl.math.temp = R0 mcl.math.io

# if...else handler
scoreboard players set 8 mcl.math.temp 0

# check both are not NaN
execute if score 6 mcl.math.temp matches 0 run scoreboard players set 8 mcl.math.temp 1
execute if score 7 mcl.math.temp matches 0 run scoreboard players set 8 mcl.math.temp 1

execute if score 8 mcl.math.temp matches 1 run function mcl:math/float/32/divide/exception/nan
execute if score 8 mcl.math.temp matches 0 run function mcl:math/float/32/divide/branch0


