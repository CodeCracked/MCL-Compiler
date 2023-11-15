#> mcl:math/bitwise/32/unsigned/divmod/b1
#   branch from mcl:math/bitwise/32/unsigned/divmod/main
##
# mcl.math.io.R0 = div
# mcl.math.io.R1 = mod
##

scoreboard players set R0 mcl.math.io 0
execute if score P1 mcl.math.io <= R0 mcl.math.io if score P1 mcl.math.io matches ..-1 run scoreboard players set R0 mcl.math.io 1

scoreboard players operation R1 mcl.math.io = P0 mcl.math.io
execute if score R0 mcl.math.io matches 0 run scoreboard players operation R0 mcl.math.io -= P1 mcl.math.io