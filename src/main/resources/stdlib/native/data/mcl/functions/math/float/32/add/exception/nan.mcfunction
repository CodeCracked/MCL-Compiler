#> mcl:math/float/32/add/exception/nan
#   Throw a NaN exception
##

scoreboard players set R0 mcl.math.io 0
scoreboard players set R1 mcl.math.io 128
scoreboard players set R2 mcl.math.io 1
scoreboard players set 8 mcl.math.temp 1

tellraw @a[tag=exception] [{"text":"[Exception] NaN Value Computed. Use /tag @s remove exception to ignore these messages.","color":"red"}]