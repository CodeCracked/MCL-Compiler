#> mcl:math/float/32/add/branch1111
# check for significand overflow

# F0 = mcl.math.temp.[0, 1, 2]

# no overflow
execute unless score R2 mcl.math.io matches 16777216.. run function mcl:math/float/32/add/branch11111

# fix significand overflow
execute if score R2 mcl.math.io matches 16777216.. run function mcl:math/float/32/add/branch11110
