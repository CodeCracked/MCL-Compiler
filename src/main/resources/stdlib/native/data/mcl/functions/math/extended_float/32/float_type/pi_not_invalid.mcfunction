#> mcl:math/extended_float/32/float_type/pi_not_invalid
#   Numbers here are possible integers

# magic 2
# copy it back
scoreboard players operation P2 mcl.math.io = P0 mcl.math.io
scoreboard players remove P1 mcl.math.io 1
function mcl:math/extended_float/32/truncate/b/main
execute if score P2 mcl.math.io matches 1.. run scoreboard players set R0 mcl.math.io 1