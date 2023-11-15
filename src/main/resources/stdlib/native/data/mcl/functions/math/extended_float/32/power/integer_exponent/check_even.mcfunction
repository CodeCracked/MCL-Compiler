#> mcl:math/extended_float/32/power/integer_exponent/check_even
#   Case when the exponent is even or odd
#
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
# assume decimals are even
execute if score R0 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/integer_exponent/even
execute if score 0 mcl.math.temp matches 1 if score R0 mcl.math.io matches 1 run function mcl:math/extended_float/32/power/integer_exponent/odd
execute if score 0 mcl.math.temp matches 1 if score R0 mcl.math.io matches 2 run say ERROR: exponent somehow not an integer
scoreboard players set 0 mcl.math.temp 0