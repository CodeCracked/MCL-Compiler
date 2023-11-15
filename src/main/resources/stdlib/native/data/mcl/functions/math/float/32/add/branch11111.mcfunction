#> mcl:math/float/32/add/branch11111
# check if result is normalized

# is not normalized
execute unless score R1 mcl.math.io matches -127 unless score R2 mcl.math.io matches 8388608..16777215 run function mcl:math/float/32/add/branch111110


# is normalized
execute if score R2 mcl.math.io matches 8388608..16777215 run scoreboard players remove R2 mcl.math.io 8388608

