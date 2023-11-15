#> mcl:math/float/32/add/branch1
##

# store F0 state
scoreboard players operation 9 mcl.math.temp = R0 mcl.math.io

# check whether F1 is 0
scoreboard players operation P0 mcl.math.io = 4 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 5 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 6 mcl.math.temp
function mcl:math/float/32/check_type/main

# store F1 state
scoreboard players operation 10 mcl.math.temp = R0 mcl.math.io

# nan exception
execute if score R0 mcl.math.io matches 0 run function mcl:math/float/32/add/exception/nan

# return F0 if F1 is 0
execute if score R0 mcl.math.io matches 3..4 run function mcl:math/float/32/add/branch10

# If F0 is an infinity, check if F1 a different infinity. Otherwise return F0
execute if score 8 mcl.math.temp matches 0 if score 9 mcl.math.temp matches 1..2 run function mcl:math/float/32/add/branch2
execute if score 8 mcl.math.temp matches 0 if score 10 mcl.math.temp matches 1..2 run function mcl:math/float/32/add/branch3


# other case
execute if score 8 mcl.math.temp matches 0 run function mcl:math/float/32/add/branch11