#> mcl:math/extended_float/32/power/not_zero
#   Return not one
#

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io


scoreboard players operation P3 mcl.math.io = 23 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 24 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 25 mcl.math.temp

function mcl:math/float/32/multiply/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/exponential/main