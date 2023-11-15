#> mcl:math/float/32/add/branch111
# exponents are equal

# F0 = mcl.math.io.R[0, 1, 2], mcl.math.temp.[0, 1, 2], F1 = mcl.math.temp.[3, 4, 5]

# set mcl.math.io.R[-, 1, 3] to F0. Set R0 to 0
scoreboard players set R0 mcl.math.io 0
scoreboard players operation R1 mcl.math.io = 1 mcl.math.temp
scoreboard players operation R2 mcl.math.io = 3 mcl.math.temp

# add signs
execute if score 0 mcl.math.temp matches 1 run scoreboard players operation R2 mcl.math.io *= -1 mcl.math.constant
execute if score 4 mcl.math.temp matches 1 run scoreboard players operation 7 mcl.math.temp *= -1 mcl.math.constant


# add significands
scoreboard players operation R2 mcl.math.io += 7 mcl.math.temp

# remove sign from significands
execute if score R2 mcl.math.io matches ..-1 run function mcl:math/float/32/add/branch1112


# if sum of significands are 0, output 0
execute if score R2 mcl.math.io matches 0 run function mcl:math/float/32/add/branch1110

# check for significand overflow
execute unless score R2 mcl.math.io matches 0 run function mcl:math/float/32/add/branch1111

scoreboard players set 7 mcl.math.temp 0