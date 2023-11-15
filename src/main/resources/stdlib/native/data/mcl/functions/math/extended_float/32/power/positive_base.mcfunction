#> mcl:math/extended_float/32/power/positive_base
#   Case when the base is positive
#

scoreboard players operation 23 mcl.math.temp = P3 mcl.math.io
scoreboard players operation 24 mcl.math.temp = P4 mcl.math.io
scoreboard players operation 25 mcl.math.temp = P5 mcl.math.io

function mcl:math/extended_float/32/log/main

scoreboard players set 0 mcl.math.temp 1
execute if score R0 mcl.math.io matches 1 if score R1 mcl.math.io matches 128 if score R2 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/zero
execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/not_zero

scoreboard players set 0 mcl.math.temp 0