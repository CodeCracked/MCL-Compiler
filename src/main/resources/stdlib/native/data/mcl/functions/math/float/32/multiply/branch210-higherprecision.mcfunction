#> mcl:math/float/32/multiply/branch210-higherprecision
#   Multiply significands with higher precision. Seems to be identical.
#

# fix sign
scoreboard players set R0 mcl.math.io 0
execute unless score 0 mcl.math.temp = 3 mcl.math.temp run scoreboard players set R0 mcl.math.io 1

# add implicit bit
scoreboard players add 2 mcl.math.temp 8388608
scoreboard players add 5 mcl.math.temp 8388608

# set exponent
scoreboard players operation R1 mcl.math.io = 1 mcl.math.temp

# multiply using the factoring thing

# calculate ac
# get a and b (temp.0, mcl.math.temp.1)
scoreboard players operation 0 mcl.math.temp = 2 mcl.math.temp
scoreboard players operation 1 mcl.math.temp = 2 mcl.math.temp
scoreboard players operation 0 mcl.math.temp /= 4096 mcl.math.constant
scoreboard players operation 1 mcl.math.temp %= 4096 mcl.math.constant

# get c and d (temp.2, mcl.math.temp.3)
scoreboard players operation 2 mcl.math.temp = 5 mcl.math.temp
scoreboard players operation 3 mcl.math.temp = 5 mcl.math.temp
scoreboard players operation 2 mcl.math.temp /= 4096 mcl.math.constant
scoreboard players operation 3 mcl.math.temp %= 4096 mcl.math.constant

# set mcl.math.io.R2 to ac
scoreboard players operation R2 mcl.math.io = 0 mcl.math.temp
scoreboard players operation R2 mcl.math.io *= 2 mcl.math.temp
scoreboard players operation R2 mcl.math.io *= 2 mcl.math.constant

# bc
# set mcl.math.temp.5 to bc
scoreboard players operation 5 mcl.math.temp = 1 mcl.math.temp
scoreboard players operation 5 mcl.math.temp *= 2 mcl.math.temp

# ad
# set mcl.math.temp.5 to ad
scoreboard players operation 6 mcl.math.temp = 0 mcl.math.temp
scoreboard players operation 6 mcl.math.temp *= 3 mcl.math.temp
# add to mcl.math.temp.0
scoreboard players operation 5 mcl.math.temp += 6 mcl.math.temp
scoreboard players operation 5 mcl.math.temp *= 64 mcl.math.constant

# bd/2^12
# set mcl.math.temp.5 to bd/2^12
scoreboard players operation 6 mcl.math.temp = 1 mcl.math.temp
scoreboard players operation 6 mcl.math.temp *= 3 mcl.math.temp
scoreboard players operation 6 mcl.math.temp /= 64 mcl.math.constant
# add to mcl.math.temp.5
scoreboard players operation 5 mcl.math.temp += 6 mcl.math.temp

# right bitshift shift by 3
scoreboard players operation 5 mcl.math.temp /= 131072 mcl.math.constant
scoreboard players operation R2 mcl.math.io += 5 mcl.math.temp

execute if score R2 mcl.math.io matches 16777216.. run function mcl:math/float/32/multiply/branch2100


# remove implicit bit
scoreboard players remove R2 mcl.math.io 8388608