#> mcl:math/float/32/add/branch3
#   Unless F0 is a different infinity than F1, return F1. Throw a NaN exception overwise.
##

function mcl:math/float/32/add/branch12
execute if score 9 mcl.math.temp matches 1..2 unless score 10 mcl.math.temp = 9 mcl.math.temp run function mcl:math/float/32/add/exception/nan