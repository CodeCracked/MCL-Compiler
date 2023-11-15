#> mcl:math/extended_float/32/power/int
#   Case when the base is negative and exponent is an integer
#

scoreboard players operation 26 mcl.math.temp = 3 mcl.math.temp

# make a positive
scoreboard players set P0 mcl.math.io 0
scoreboard players operation P1 mcl.math.io = 1 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 2 mcl.math.temp

# copy b
scoreboard players operation 23 mcl.math.temp = P3 mcl.math.io
scoreboard players operation 24 mcl.math.temp = P4 mcl.math.io
scoreboard players operation 25 mcl.math.temp = P5 mcl.math.io

# ln -a
function mcl:math/extended_float/32/log/main

scoreboard players set 0 mcl.math.temp 1
execute if score R0 mcl.math.io matches 1 if score R1 mcl.math.io matches 128 if score R2 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/zero
execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/not_zero

scoreboard players set 0 mcl.math.temp 0

execute if score 26 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/odd