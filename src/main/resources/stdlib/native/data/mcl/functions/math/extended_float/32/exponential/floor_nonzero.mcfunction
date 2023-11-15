#> mcl:math/extended_float/32/exponential/floor_nonzero
# Sets the exponent of a float to another float expressed as an integer
#

# Add implicit bit to mantissa
scoreboard players add P2 mcl.math.io 8388608
# Turn float exponent into integer
execute if score P1 mcl.math.io matches 6 run scoreboard players operation P2 mcl.math.io /= 131072 mcl.math.constant
execute if score P1 mcl.math.io matches 5 run scoreboard players operation P2 mcl.math.io /= 262144 mcl.math.constant
execute if score P1 mcl.math.io matches 4 run scoreboard players operation P2 mcl.math.io /= 524288 mcl.math.constant
execute if score P1 mcl.math.io matches 3 run scoreboard players operation P2 mcl.math.io /= 1048576 mcl.math.constant
execute if score P1 mcl.math.io matches 2 run scoreboard players operation P2 mcl.math.io /= 2097152 mcl.math.constant
execute if score P1 mcl.math.io matches 1 run scoreboard players operation P2 mcl.math.io /= 4194304 mcl.math.constant
execute if score P1 mcl.math.io matches 0 run scoreboard players operation P2 mcl.math.io /= 8388608 mcl.math.constant
# Now P2 is an integer
execute if score P0 mcl.math.io matches 1 run scoreboard players operation P2 mcl.math.io *= -1 mcl.math.constant

# Set P2 as the exponent for a new number mcl.math.temp.[11..13]
scoreboard players set 11 mcl.math.temp 0
scoreboard players operation 12 mcl.math.temp = P2 mcl.math.io
scoreboard players set 13 mcl.math.temp 0