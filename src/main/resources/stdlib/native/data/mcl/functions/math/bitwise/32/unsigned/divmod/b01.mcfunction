#> mcl:math/bitwise/32/unsigned/divmod/b01
#   branch from mcl:math/bitwise/32/unsigned/divmod/b0
##
# mcl.math.io.R0 = div
# mcl.math.io.R1 = mod
# mcl.math.temp.0 = mod1
# mcl.math.temp.1 = div1
# mcl.math.temp.2 = or
##

scoreboard players set 0 mcl.math.temp 1073741824
scoreboard players operation 0 mcl.math.temp %= P1 mcl.math.io

scoreboard players set 1 mcl.math.temp 1073741824
scoreboard players operation 1 mcl.math.temp /= P1 mcl.math.io
scoreboard players operation 1 mcl.math.temp *= 2 mcl.math.constant
scoreboard players operation R0 mcl.math.io += 1 mcl.math.temp

scoreboard players operation 0 mcl.math.temp *= 2 mcl.math.constant

scoreboard players set 2 mcl.math.temp 0
execute if score 0 mcl.math.temp matches ..-1 run scoreboard players set 2 mcl.math.temp 1
execute if score 0 mcl.math.temp >= P1 mcl.math.io run scoreboard players set 2 mcl.math.temp 1
execute if score 2 mcl.math.temp matches 1 run function mcl:math/bitwise/32/unsigned/divmod/b010

scoreboard players operation R1 mcl.math.io += 0 mcl.math.temp
scoreboard players set 2 mcl.math.temp 0
execute if score R1 mcl.math.io matches ..-1 run scoreboard players set 2 mcl.math.temp 1
execute if score R1 mcl.math.io >= P1 mcl.math.io run scoreboard players set 2 mcl.math.temp 1
execute if score 2 mcl.math.temp matches 1 run function mcl:math/bitwise/32/unsigned/divmod/b011