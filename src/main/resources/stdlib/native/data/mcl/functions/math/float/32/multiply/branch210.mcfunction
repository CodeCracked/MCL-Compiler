#> mcl:math/float/32/multiply/branch210
#   Multiply significands
#

# fix sign
scoreboard players set R0 mcl.math.io 0
execute unless score 0 mcl.math.temp = 3 mcl.math.temp run scoreboard players set R0 mcl.math.io 1


# set exponent
scoreboard players operation R1 mcl.math.io = 1 mcl.math.temp

# add implicit bit

execute unless score 4 mcl.math.temp matches 1 run scoreboard players add 2 mcl.math.temp 8388608
execute unless score 4 mcl.math.temp matches 2 run scoreboard players add 5 mcl.math.temp 8388608

# swap such that mcl.math.temp.2 is the denormalized mantissa
execute if score 4 mcl.math.temp matches 2 run function mcl:math/float/32/multiply/branch2101


execute if score R1 mcl.math.io matches -125.. if score 4 mcl.math.temp matches 1..2 run function mcl:math/float/32/multiply/branch2102


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

# bd/2^12
# set mcl.math.temp.5 to bd/2^12
scoreboard players operation 6 mcl.math.temp = 1 mcl.math.temp
scoreboard players operation 6 mcl.math.temp *= 3 mcl.math.temp
scoreboard players operation 6 mcl.math.temp /= 4096 mcl.math.constant
# add to mcl.math.temp.5
scoreboard players operation 5 mcl.math.temp += 6 mcl.math.temp

# right bitshift shift by 3
scoreboard players operation 5 mcl.math.temp /= 2048 mcl.math.constant
scoreboard players operation R2 mcl.math.io += 5 mcl.math.temp

execute if score R2 mcl.math.io matches 16777216.. run function mcl:math/float/32/multiply/branch2100

# fix denormalized numbers
execute if score R1 mcl.math.io matches -125.. unless score R2 mcl.math.io matches 8388608.. run function mcl:math/float/32/multiply/branch2103

execute if score R1 mcl.math.io matches -126 unless score R2 mcl.math.io matches 8388608.. run scoreboard players set R1 mcl.math.io -127

# remove implicit bit
execute if score 8 mcl.math.temp matches 0 if score R2 mcl.math.io matches 8388608.. run scoreboard players remove R2 mcl.math.io 8388608