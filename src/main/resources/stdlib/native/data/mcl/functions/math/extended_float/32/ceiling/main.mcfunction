#> mcl:math/extended_float/32/ceiling/main
#   Returns a ceilinged float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#
# @modifies mcl.math.temp.[0..10] (if input is negative)

scoreboard players operation R0 mcl.math.io = P0 mcl.math.io
scoreboard players operation R1 mcl.math.io = P1 mcl.math.io
scoreboard players operation R2 mcl.math.io = P2 mcl.math.io

execute if score P1 mcl.math.io matches 0..22 run function mcl:math/extended_float/32/floor/truncate

scoreboard players set 0 mcl.math.temp 1
execute if score P0 mcl.math.io matches 0 if score P1 mcl.math.io matches ..-1 run function mcl:math/extended_float/32/ceiling/one
execute if score P0 mcl.math.io matches 1 if score P1 mcl.math.io matches ..-1 run function mcl:math/extended_float/32/ceiling/zero
execute if score 0 mcl.math.temp matches 1 if score R0 mcl.math.io matches 0 if score P1 mcl.math.io matches ..22 unless score P2 mcl.math.io matches 0 run function mcl:math/extended_float/32/ceiling/add_1