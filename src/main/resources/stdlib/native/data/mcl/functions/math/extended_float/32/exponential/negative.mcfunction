scoreboard players set P3 mcl.math.io 1
scoreboard players set P4 mcl.math.io 7
scoreboard players set P5 mcl.math.io 0

function mcl:math/float/32/compare/less_equal/main

# if it is less, set to 0
execute if score R0 mcl.math.io matches 1 run function mcl:math/extended_float/32/exponential/zero
# if it isn't, do nothing (in this function)