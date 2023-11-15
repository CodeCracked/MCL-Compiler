# Use inverse of fast inverse sqrt
scoreboard players operation P0 mcl.math.io = P3 mcl.math.io
scoreboard players operation P1 mcl.math.io = P4 mcl.math.io
scoreboard players operation P2 mcl.math.io = P5 mcl.math.io

function mcl:math/extended_float/32/fast_inverse_sqrt/main

scoreboard players set P0 mcl.math.io 0
scoreboard players set P1 mcl.math.io 0
scoreboard players set P2 mcl.math.io 0

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io

function mcl:math/float/32/divide/main

scoreboard players set 0 mcl.math.temp 0