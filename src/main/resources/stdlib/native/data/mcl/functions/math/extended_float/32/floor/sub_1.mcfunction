#> mcl:math/extended_float/32/floor/sub_1
#   Subtracts 1 from the floored float

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 0
scoreboard players set P5 mcl.math.io 0

function mcl:math/float/32/subtract/main