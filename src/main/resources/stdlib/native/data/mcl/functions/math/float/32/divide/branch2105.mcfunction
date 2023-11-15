#> mcl:math/float/32/divide/branch2105
#   Shift mcl.math.io.R2 left until the leading bit is 1.
#

scoreboard players operation R2 mcl.math.io *= 2 mcl.math.constant
scoreboard players add R1 mcl.math.io 1

execute unless score R2 mcl.math.io matches 8388608.. if score R1 mcl.math.io matches ..-127 run function mcl:math/float/32/divide/branch2105