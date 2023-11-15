#> mcl:math/extended_float/32/root/negative_radicand
#   Case when the radicand is negative
#

scoreboard players operation 0 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 1 mcl.math.temp = P1 mcl.math.io
scoreboard players operation 2 mcl.math.temp = P2 mcl.math.io

scoreboard players operation P0 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 13 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 14 mcl.math.temp

function mcl:math/extended_float/32/float_type/main
scoreboard players operation 3 mcl.math.temp = R0 mcl.math.io
execute unless score 3 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/nan
execute if score 3 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/int
