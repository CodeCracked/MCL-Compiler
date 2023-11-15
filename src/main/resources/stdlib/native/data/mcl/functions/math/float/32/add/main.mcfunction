#> mcl:math/float/32/add/main
#   Return the sum of two 32-bit floats
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
#   F0 = mcl.math.temp.[0, 1, 2], mcl.math.io.P[0, 1, 2]
#   F1 = mcl.math.temp.[3, 4, 5], mcl.math.io.P[3, 4, 5]
##
# @modifies mcl.math.temp.[0..10]

# maintain a copy of the inputs
scoreboard players operation 0 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 1 mcl.math.temp = P1 mcl.math.io
scoreboard players operation 2 mcl.math.temp = P2 mcl.math.io
scoreboard players operation 3 mcl.math.temp = P2 mcl.math.io

scoreboard players operation 4 mcl.math.temp = P3 mcl.math.io
scoreboard players operation 5 mcl.math.temp = P4 mcl.math.io
scoreboard players operation 6 mcl.math.temp = P5 mcl.math.io
scoreboard players operation 7 mcl.math.temp = P5 mcl.math.io

# add the implicit leading bit if the exponent is not -127. If it is, add 1 to exponent and end. TODO did I do this? <-
execute unless score 1 mcl.math.temp matches -127 run scoreboard players add 3 mcl.math.temp 8388608

execute unless score 5 mcl.math.temp matches -127 run scoreboard players add 7 mcl.math.temp 8388608

# if... else handler. If mcl.math.temp.8 = 1, end.
scoreboard players set 8 mcl.math.temp 0

# check whether F0 is NaN, infinity, or 0
function mcl:math/float/32/check_type/main

# nan exception
execute if score R0 mcl.math.io matches 0 run function mcl:math/float/32/add/exception/nan

# return F1 if F0 is 0
execute if score R0 mcl.math.io matches 3..4 run function mcl:math/float/32/add/branch0

# other case
execute if score 8 mcl.math.temp matches 0 run function mcl:math/float/32/add/branch1