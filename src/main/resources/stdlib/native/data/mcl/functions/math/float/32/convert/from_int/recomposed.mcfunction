scoreboard players set R0 mcl.math.io 0
execute if score P0 mcl.math.io matches ..-1 run function mcl:math/float/32/convert/from_int/negative

scoreboard players operation P2 mcl.math.io = P0 mcl.math.io
function mcl:math/float/32/convert/from_int/b/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io

scoreboard players remove P2 mcl.math.io 8388608

function mcl:math/float/32/recompose/main