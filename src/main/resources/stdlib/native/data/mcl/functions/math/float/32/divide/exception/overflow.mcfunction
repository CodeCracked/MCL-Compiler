#> mcl:math/float/32/divide/exception/overflow
#   Throw a overflow exception, set output to infinity
##

function mcl:math/float/32/divide/branch20
scoreboard players set 8 mcl.math.temp 1

tellraw @a[tag=exception] [{"text":"[Exception] Exponent Overflow. Use /tag @s remove exception to ignore these messages.","color":"red"}]