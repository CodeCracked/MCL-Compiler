#> mcl:math/extended_float/32/power/integer_exponent/even
#   Case when the exponent is even
#

scoreboard players operation 9 mcl.math.temp = P3 mcl.math.io
scoreboard players operation 10 mcl.math.temp = P4 mcl.math.io
scoreboard players operation 11 mcl.math.temp = P5 mcl.math.io

scoreboard players operation P3 mcl.math.io = P0 mcl.math.io
scoreboard players operation P4 mcl.math.io = P1 mcl.math.io
scoreboard players operation P5 mcl.math.io = P2 mcl.math.io

function mcl:math/float/32/multiply/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 9 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 10 mcl.math.temp
scoreboard players remove P4 mcl.math.io 1
scoreboard players operation P5 mcl.math.io = 11 mcl.math.temp

function mcl:math/extended_float/32/power/integer_exponent/main

scoreboard players set 0 mcl.math.temp 0