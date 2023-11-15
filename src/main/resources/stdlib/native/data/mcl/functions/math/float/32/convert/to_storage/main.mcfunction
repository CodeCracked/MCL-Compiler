#> mcl:math/float/32/convert/to_storage/main
#   Return a 32-bit float representation as a storage float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   {io.R0}
#       storage float type
##

# scoreboard players set 0 mcl.math.temp 1
# execute if score P1 mcl.math.io matches 128 run function mcl:math/float/32/convert/to_storage/special
# execute if score 0 mcl.math.temp matches 1 unless score P1 mcl.math.io matches 128 run function mcl:math/float/32/convert/to_storage/number

function mcl:math/float/32/convert/to_storage/number
