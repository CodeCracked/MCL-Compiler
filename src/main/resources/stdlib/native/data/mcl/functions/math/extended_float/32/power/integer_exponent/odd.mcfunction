#> mcl:math/extended_float/32/power/integer_exponent/odd
#   Case when the exponent is odd
#

scoreboard players operation 11 mcl.math.temp = P3 mcl.math.io
scoreboard players operation 12 mcl.math.temp = P4 mcl.math.io
scoreboard players operation 13 mcl.math.temp = P5 mcl.math.io

data modify storage calculate stack append value [0,0,0]
execute store result storage calculate stack[-1][0] int 1 run scoreboard players operation P3 mcl.math.io = P0 mcl.math.io
execute store result storage calculate stack[-1][1] int 1 run scoreboard players operation P4 mcl.math.io = P1 mcl.math.io
execute store result storage calculate stack[-1][2] int 1 run scoreboard players operation P5 mcl.math.io = P2 mcl.math.io


function mcl:math/float/32/multiply/main

# n
scoreboard players operation P0 mcl.math.io = 11 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 13 mcl.math.temp

# x^2
scoreboard players operation 11 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 12 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 13 mcl.math.temp = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 0
scoreboard players set P5 mcl.math.io 0

function mcl:math/float/32/subtract/main

scoreboard players operation P0 mcl.math.io = 11 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 13 mcl.math.temp
# (n-1)/2
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players remove P4 mcl.math.io 1
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io

function mcl:math/extended_float/32/power/integer_exponent/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

execute store result score P3 mcl.math.io run data get storage calculate stack[-1][0]
execute store result score P4 mcl.math.io run data get storage calculate stack[-1][1]
execute store result score P5 mcl.math.io run data get storage calculate stack[-1][2]
data remove storage calculate stack[-1]
function mcl:math/float/32/multiply/main

scoreboard players set 0 mcl.math.temp 0