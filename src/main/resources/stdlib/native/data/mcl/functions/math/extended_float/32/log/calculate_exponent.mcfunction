#> mcl:math/extended_float/32/log/calculate_exponent
#   Calculate the next exponent by multiplying the previous exponent by (ax-1)

scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 17 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 18 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 19 mcl.math.temp
function mcl:math/float/32/multiply/main

# copy result to mcl.math.temp.[17..19]
scoreboard players operation 17 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 18 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 19 mcl.math.temp = R2 mcl.math.io