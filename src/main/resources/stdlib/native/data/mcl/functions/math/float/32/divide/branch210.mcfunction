#> mcl:math/float/32/divide/branch210
#   divide significands
#

# fix sign
scoreboard players set R0 mcl.math.io 0
execute unless score 0 mcl.math.temp = 3 mcl.math.temp run scoreboard players set R0 mcl.math.io 1

# set exponent


scoreboard players operation R1 mcl.math.io = 1 mcl.math.temp
# add implicit bit
execute unless score 4 mcl.math.temp matches 1 unless score 4 mcl.math.temp matches 3 run scoreboard players add 2 mcl.math.temp 8388608
execute unless score 4 mcl.math.temp matches 2 unless score 4 mcl.math.temp matches 3 run scoreboard players add 5 mcl.math.temp 8388608

execute unless score 4 mcl.math.temp matches 0 unless score 4 mcl.math.temp matches 2 run function mcl:math/float/32/divide/branch2103
execute unless score 4 mcl.math.temp matches 0 unless score 4 mcl.math.temp matches 1 run function mcl:math/float/32/divide/branch2104

# divide using long division

scoreboard players operation 0 mcl.math.temp = 2 mcl.math.temp
scoreboard players operation 1 mcl.math.temp = 5 mcl.math.temp
scoreboard players set R2 mcl.math.io 0
scoreboard players operation 0 mcl.math.temp *= 64 mcl.math.constant
scoreboard players operation 1 mcl.math.temp *= 64 mcl.math.constant

# do this 24 times
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102
function mcl:math/float/32/divide/branch2102

execute if score R2 mcl.math.io matches ..8388608 run function mcl:math/float/32/divide/branch2101
execute if score 0 mcl.math.temp >= 1 mcl.math.temp run scoreboard players add R2 mcl.math.io 1

# fix denormalized numbers
execute if score R1 mcl.math.io matches ..-127 run function mcl:math/float/32/divide/branch2105
# return 0 if number is very small
execute if score R1 mcl.math.io matches ..-127 run function mcl:math/float/32/divide/branch1
# return infinity if number is very large
execute if score R1 mcl.math.io matches 129.. run function mcl:math/float/32/divide/branch20

execute if score 8 mcl.math.temp matches 0 if score R1 mcl.math.io matches -126 unless score R0 mcl.math.io matches 8388608.. run scoreboard players set R1 mcl.math.io -127

# remove implicit bit
execute if score 8 mcl.math.temp matches 0 if score R1 mcl.math.io matches -126.. run scoreboard players remove R2 mcl.math.io 8388608

execute if score R2 mcl.math.io matches 8388608.. run function mcl:math/float/32/divide/branch2106