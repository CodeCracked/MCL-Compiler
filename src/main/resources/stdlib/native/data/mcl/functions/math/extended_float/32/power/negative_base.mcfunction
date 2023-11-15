#> mcl:math/extended_float/32/power/negative_base
#   Case when the base is negative
#

scoreboard players operation 0 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 1 mcl.math.temp = P1 mcl.math.io
scoreboard players operation 2 mcl.math.temp = P2 mcl.math.io

scoreboard players operation P0 mcl.math.io = P3 mcl.math.io
scoreboard players operation P1 mcl.math.io = P4 mcl.math.io
scoreboard players operation P2 mcl.math.io = P5 mcl.math.io

function mcl:math/extended_float/32/float_type/main
scoreboard players operation 3 mcl.math.temp = R0 mcl.math.io
execute if score 3 mcl.math.temp matches 2 run function mcl:math/extended_float/32/power/nan
execute if score 3 mcl.math.temp matches 0..1 run function mcl:math/extended_float/32/power/int
