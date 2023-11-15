#> mcl:math/float/32/divide/branch2103
#   Shift mcl.math.temp.2 left until the leading bit is 1.
#

scoreboard players operation 2 mcl.math.temp *= 2 mcl.math.constant
scoreboard players remove R1 mcl.math.io 1

execute unless score 2 mcl.math.temp matches 8388608.. run function mcl:math/float/32/divide/branch2103