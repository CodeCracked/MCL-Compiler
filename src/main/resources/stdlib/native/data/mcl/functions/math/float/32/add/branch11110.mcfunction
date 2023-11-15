#> mcl:math/float/32/add/branch11110
# check exponent overflow

scoreboard players add R1 mcl.math.io 1
# report overflow
execute if score R1 mcl.math.io matches 129.. run function mcl:math/float/32/add/exception/overflow
# no overflow
execute unless score R1 mcl.math.io matches 129.. run scoreboard players operation R2 mcl.math.io /= 2 mcl.math.constant
execute unless score R1 mcl.math.io matches 129.. run function mcl:math/float/32/add/branch11111