#> mcl:math/extended_float/32/float_type/possible_integer
#   Numbers here are possible integers

# magic 1
# copy it over since P0 is useless
scoreboard players operation P0 mcl.math.io = P2 mcl.math.io
execute if score P1 mcl.math.io matches 1.. run function mcl:math/extended_float/32/truncate/b/main
execute if score P2 mcl.math.io matches 1.. run scoreboard players set R0 mcl.math.io 2


execute if score R0 mcl.math.io matches 0 run function mcl:math/extended_float/32/float_type/pi_not_invalid