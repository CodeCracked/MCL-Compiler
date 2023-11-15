#> mcl:math/extended_float/32/tan/real
#   Case when the angle is real
#

# see sine implementation for comments
function mcl:math/float/32/convert/to_storage/main
data modify entity fef32a78-71bf-4588-9c82-115070685847 Rotation[0] set from storage mcl:math R0
execute store result entity fef32a78-71bf-4588-9c82-115070685847 Rotation[0] float 0.00005464151336010201 run data get entity fef32a78-71bf-4588-9c82-115070685847 Rotation[0] 1048576

execute positioned 0.0 0.0 0.0 rotated as fef32a78-71bf-4588-9c82-115070685847 run tp fef32a78-71bf-4588-9c82-115070685847 ^ ^ ^1
execute store result score P0 mcl.math.io run data get entity fef32a78-71bf-4588-9c82-115070685847 Pos[2] 1073741824
function mcl:math/float/32/convert/from_int/main
scoreboard players remove R1 mcl.math.io 30
execute if score R1 mcl.math.io matches ..-128 run function mcl:math/extended_float/32/sin/zero
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io

execute store result score P0 mcl.math.io run data get entity fef32a78-71bf-4588-9c82-115070685847 Pos[0] -1073741824
function mcl:math/float/32/convert/from_int/main
scoreboard players remove R1 mcl.math.io 30
execute if score R1 mcl.math.io matches ..-128 run function mcl:math/extended_float/32/sin/zero

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

function mcl:math/float/32/divide/main