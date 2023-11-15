#> mcl:math/float/32/divide/branch2102
#   Recursive long division
#

scoreboard players operation R2 mcl.math.io *= 2 mcl.math.constant
execute if score 0 mcl.math.temp >= 1 mcl.math.temp run function mcl:math/float/32/divide/branch2100

scoreboard players operation 0 mcl.math.temp *= 2 mcl.math.constant
