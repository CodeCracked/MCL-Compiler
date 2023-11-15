#> mcl:math/float/32/divide/branch2104
#   Shift mcl.math.temp.2 left until the leading bit is 1.
#

scoreboard players operation 5 mcl.math.temp *= 2 mcl.math.constant
scoreboard players add R1 mcl.math.io 1

execute unless score 5 mcl.math.temp matches 8388608.. run function mcl:math/float/32/divide/branch2104