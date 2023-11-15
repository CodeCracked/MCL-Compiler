#> mcl:math/float/32/add/branch110
# set larger float to F0, smaller float to F1

# increment smaller exponent, shift significant right
function mcl:math/float/32/add/branch1101

# if significand is 0, return F0
scoreboard players operation R0 mcl.math.io = 0 mcl.math.temp
scoreboard players operation R1 mcl.math.io = 1 mcl.math.temp
scoreboard players operation R2 mcl.math.io = 2 mcl.math.temp

# if significand is not 0, repeat from branch11
execute unless score 7 mcl.math.temp matches 0 run function mcl:math/float/32/add/branch11