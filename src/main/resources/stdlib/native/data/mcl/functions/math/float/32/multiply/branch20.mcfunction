#> mcl:math/float/32/multiply/branch20
#   Set to infinity
#

scoreboard players set R0 mcl.math.io 0
# neg infinity if signs are different
execute unless score 0 mcl.math.temp = 3 mcl.math.temp run scoreboard players set R0 mcl.math.io 1
scoreboard players set R1 mcl.math.io 128
scoreboard players set R2 mcl.math.io 0