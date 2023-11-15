#> mcl:math/bitwise/32/unsigned/divmod/b0
#   branch from mcl:math/bitwise/32/unsigned/divmod/main
##
# mcl.math.io.R0 = div
# mcl.math.io.R1 = mod
##

execute if score R0 mcl.math.io matches ..-1 run function mcl:math/bitwise/32/unsigned/divmod/b00
scoreboard players operation R1 mcl.math.io = R0 mcl.math.io
scoreboard players operation R1 mcl.math.io %= P1 mcl.math.io
scoreboard players operation R0 mcl.math.io /= P1 mcl.math.io

execute if score P0 mcl.math.io matches ..-1 run function mcl:math/bitwise/32/unsigned/divmod/b01