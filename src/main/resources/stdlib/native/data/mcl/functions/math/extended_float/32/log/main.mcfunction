#> mcl:math/extended_float/32/log/main
#   Return the natural logarithm of a float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
##
#   F0 = mcl.math.temp.[0, 1, 2], mcl.math.io.P[0, 1, 2]
#   F1 = mcl.math.temp.[3, 4, 5], mcl.math.io.P[3, 4, 5]
##
# @modifies mcl.math.temp.[0..22]


# copy variables mcl.math.io.P[0..5], mcl.math.temp.[0..10]
# execute store result storage hmmm:variables mcl.math.temp int 1 run scoreboard players get P0 mcl.math.io
# data modify storage hmmm:variables mcl.math.temp_stack append from storage hmmm:variables mcl.math.temp
# execute store result storage hmmm:variables mcl.math.temp int 1 run scoreboard players get P1 mcl.math.io
# data modify storage hmmm:variables mcl.math.temp_stack append from storage hmmm:variables mcl.math.temp
# execute store result storage hmmm:variables mcl.math.temp int 1 run scoreboard players get P2 mcl.math.io
# data modify storage hmmm:variables mcl.math.temp_stack append from storage hmmm:variables mcl.math.temp
scoreboard players set 0 mcl.math.temp 1
execute if score P0 mcl.math.io matches 1 run function mcl:math/extended_float/32/log/negative
execute if score 0 mcl.math.temp matches 1 if score P0 mcl.math.io matches 0 if score P1 mcl.math.io matches -127 if score P2 mcl.math.io matches 0 run function mcl:math/extended_float/32/log/zero
execute if score 0 mcl.math.temp matches 1 if score P0 mcl.math.io matches 0 run function mcl:math/extended_float/32/log/positive