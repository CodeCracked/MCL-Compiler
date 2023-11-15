#> mcl:math/float/32/add/branch2
#   Unless F1 is a different infinity than F0, return F0. Throw a NaN exception overwise.
##

function mcl:math/float/32/add/branch10
execute if score 10 mcl.math.temp matches 1..2 unless score 10 mcl.math.temp = 9 mcl.math.temp run function mcl:math/float/32/add/exception/nan