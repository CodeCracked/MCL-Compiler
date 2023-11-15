#> mcl:math/extended_float/32/power/not_integer_exponent/main
#   Case when the exponent is not an integer
#

scoreboard players set 0 mcl.math.temp 1
execute if score P0 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/positive_base
execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/negative_base
scoreboard players set 0 mcl.math.temp 0