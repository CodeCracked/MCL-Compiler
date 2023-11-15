#> mcl:math/float/32/add/branch111110
# shift significand left, decrease exponent

scoreboard players operation R2 mcl.math.io *= 2 mcl.math.constant
scoreboard players remove R1 mcl.math.io 1

# check for underflow
execute if score R1 mcl.math.io matches ..-128 run function mcl:math/float/32/add/exception/underflow
execute unless score R1 mcl.math.io matches ..-128 run function mcl:math/float/32/add/branch11111