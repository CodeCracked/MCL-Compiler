scoreboard players operation 12 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 13 mcl.math.temp = P1 mcl.math.io
scoreboard players operation 14 mcl.math.temp = P2 mcl.math.io

scoreboard players operation 9 mcl.math.temp = P3 mcl.math.io
scoreboard players operation 10 mcl.math.temp = P4 mcl.math.io
scoreboard players operation 11 mcl.math.temp = P5 mcl.math.io

scoreboard players operation P3 mcl.math.io = P0 mcl.math.io
scoreboard players operation P4 mcl.math.io = P1 mcl.math.io
scoreboard players operation P5 mcl.math.io = P2 mcl.math.io

scoreboard players set P0 mcl.math.io 0
scoreboard players set P1 mcl.math.io 0
scoreboard players set P2 mcl.math.io 0

function mcl:math/float/32/divide/main

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io

scoreboard players operation P0 mcl.math.io = 9 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 10 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 11 mcl.math.temp

scoreboard players set 0 mcl.math.temp 1
execute if score 0 mcl.math.temp matches 1 if score P0 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/positive_base
execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/root/negative_radicand