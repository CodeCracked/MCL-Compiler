#> mcl:math/float/32/add/branch11
# check for exponent equivalence


# swap floats if F1 is larger
execute if score 1 mcl.math.temp < 5 mcl.math.temp run function mcl:math/float/32/add/branch1100


# enter loop if exponents are not equal
execute unless score 1 mcl.math.temp = 5 mcl.math.temp unless score 7 mcl.math.temp matches 0 if score 5 mcl.math.temp matches -127 run scoreboard players set 5 mcl.math.temp -126

execute if score 1 mcl.math.temp = 5 mcl.math.temp unless score 7 mcl.math.temp matches 0 run function mcl:math/float/32/add/branch111
execute unless score 1 mcl.math.temp = 5 mcl.math.temp unless score 7 mcl.math.temp matches 0 run function mcl:math/float/32/add/branch110