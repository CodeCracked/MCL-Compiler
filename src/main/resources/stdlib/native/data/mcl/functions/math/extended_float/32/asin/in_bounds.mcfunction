#> mcl:math/extended_float/32/asin/in_bounds
#   Case when in bounds
#

scoreboard players operation 17 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 18 mcl.math.temp = P1 mcl.math.io
scoreboard players operation 19 mcl.math.temp = P2 mcl.math.io

scoreboard players operation P3 mcl.math.io = P0 mcl.math.io
scoreboard players operation P4 mcl.math.io = P1 mcl.math.io
scoreboard players operation P5 mcl.math.io = P2 mcl.math.io

function mcl:math/float/32/multiply/main

scoreboard players set P0 mcl.math.io 0
scoreboard players set P1 mcl.math.io 0
scoreboard players set P2 mcl.math.io 0

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io

function mcl:math/float/32/subtract/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

function mcl:math/extended_float/32/fast_inverse_sqrt/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 17 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 18 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 19 mcl.math.temp

function mcl:math/float/32/multiply/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

function mcl:math/extended_float/32/atan/main