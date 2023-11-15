#> mcl:math/float/32/multiply/branch21
#   Add exponents
#

# add exponents, adjust for bias
execute if score 1 mcl.math.temp matches -127 run scoreboard players set 8 mcl.math.temp 1
execute if score 1 mcl.math.temp matches -127 run scoreboard players add 1 mcl.math.temp 1
execute if score 4 mcl.math.temp matches -127 run scoreboard players set 8 mcl.math.temp 2
execute if score 4 mcl.math.temp matches -127 run scoreboard players add 4 mcl.math.temp 1
scoreboard players operation 1 mcl.math.temp += 4 mcl.math.temp

scoreboard players operation 4 mcl.math.temp = 8 mcl.math.temp
scoreboard players set 8 mcl.math.temp 0

# overflow
execute if score 1 mcl.math.temp matches 129.. run function mcl:math/float/32/multiply/exception/overflow
# underflow
execute if score 1 mcl.math.temp matches ..-128 run function mcl:math/float/32/multiply/exception/underflow

# if working
execute if score 8 mcl.math.temp matches 0 run function mcl:math/float/32/multiply/branch210