#> mcl:math/float/32/check_type/dne
#   branch for numbers that do not exist
##

scoreboard players set R0 mcl.math.io 0
execute if score P2 mcl.math.io matches 0 run function mcl:math/float/32/check_type/inf