#> mcl:math/float/32/add/branch1112
# remove sign from significand and put it in R0
scoreboard players operation R2 mcl.math.io *= -1 mcl.math.constant
scoreboard players set R0 mcl.math.io 1