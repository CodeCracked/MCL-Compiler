#> mcl:math/bitwise/32/unsigned/divmod/b010
#   branch from mcl:math/bitwise/32/unsigned/divmod/b01
##
# mcl.math.io.R0 = div
# mcl.math.io.R1 = mod
# mcl.math.temp.0 = mod1
# mcl.math.temp.1 = div1
# mcl.math.temp.2 = or
##

scoreboard players operation 0 mcl.math.temp -= P1 mcl.math.io
scoreboard players add R0 mcl.math.io 1