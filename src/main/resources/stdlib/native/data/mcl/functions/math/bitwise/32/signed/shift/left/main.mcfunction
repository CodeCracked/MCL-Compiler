#> mcl:math/bitwise/32/signed/shift/left/main
#   Performs a leftwards bitwise shift
##
# @params
#   mcl.math.io.P0
#       32-bit integers
# @returns
#   mcl.math.io.R0
#       32-bit integer
##

scoreboard players set 0 mcl.math.temp 0
execute if score P0 mcl.math.io matches ..-1 run function mcl:math/bitwise/32/signed/shift/remove_sign

scoreboard players operation R0 mcl.math.io = P0 mcl.math.io
scoreboard players operation R0 mcl.math.io *= 2 mcl.math.constant