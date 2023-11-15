#> mcl:math/bitwise/32/unsigned/divmod/b011
#   branch from mcl:math/bitwise/32/unsigned/divmod/b01
##
# mcl.math.io.R0 = div
# mcl.math.io.R1 = mod
# mcl.math.temp.0 = mod1
# mcl.math.temp.1 = div1
# mcl.math.temp.2 = or
##

scoreboard players operation R1 mcl.math.io -= P1 mcl.math.io
scoreboard players add R0 mcl.math.io 1