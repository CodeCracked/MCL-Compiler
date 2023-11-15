#> mcl:math/extended_float/32/atan/main
#   Return the arctan of the 32-bit float
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#

function mcl:math/float/32/convert/to_storage_double/main
execute as fef32a78-71bf-4588-9c82-115070685847 run function mcl:math/extended_float/32/atan/as_entity

function mcl:math/float/32/convert/from_int/main
scoreboard players remove R1 mcl.math.io 28
execute if score R1 mcl.math.io matches ..-128 run function mcl:math/extended_float/32/sin/zero