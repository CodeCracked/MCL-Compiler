#> mcl:math/float/32/multiply/branch2103
#   Shift mcl.math.io.R2 left until the leading bit is 1.
#

scoreboard players set 8 mcl.math.temp 0
scoreboard players operation R2 mcl.math.io *= 2 mcl.math.constant
scoreboard players remove R1 mcl.math.io 1


execute if score R1 mcl.math.io matches ..-126 run function mcl:math/float/32/multiply/exception/underflow
execute if score 8 mcl.math.temp matches 0 if score R1 mcl.math.io matches -125.. unless score R2 mcl.math.io matches 8388608.. run function mcl:math/float/32/multiply/branch2103