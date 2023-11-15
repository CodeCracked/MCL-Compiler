#> mcl:math/float/32/convert/to_storage_double/number
#   Branch for when the float is a normal number
scoreboard players add P2 mcl.math.io 8388608
execute if score P0 mcl.math.io matches 1 run scoreboard players operation P2 mcl.math.io *= -1 mcl.math.constant

# quaternary search tree for scale

function mcl:math/float/32/convert/to_storage_double/b/main