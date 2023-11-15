#> mcl:math/extended_float/32/ceiling/add_1
#   Adds 1 to the ceilinged float

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 0
scoreboard players set P5 mcl.math.io 0

function mcl:math/float/32/add/main