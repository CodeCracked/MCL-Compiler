#> mcl:math/float/32/multiply/branch2
#   Check for infinity
#

execute if score 6 mcl.math.temp matches 1..2 run scoreboard players set 8 mcl.math.temp 1
execute if score 7 mcl.math.temp matches 1..2 run scoreboard players set 8 mcl.math.temp 1

execute if score 8 mcl.math.temp matches 1 run function mcl:math/float/32/multiply/branch20
execute if score 8 mcl.math.temp matches 0 run function mcl:math/float/32/multiply/branch21