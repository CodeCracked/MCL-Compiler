#> mcl:math/extended_float/32/power/integer_exponent/main
#   Case when the exponent is an integer
#
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#   mcl.math.io.P[3, 4, 5]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand

scoreboard players set 0 mcl.math.temp 1
execute if score P4 mcl.math.io matches ..-1 run function mcl:math/extended_float/32/power/integer_exponent/zero
execute if score 0 mcl.math.temp matches 1 if score P3 mcl.math.io matches 1 run function mcl:math/extended_float/32/power/integer_exponent/negative
execute if score 0 mcl.math.temp matches 1 if score P4 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/integer_exponent/one
execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/integer_exponent/check_even

scoreboard players set 0 mcl.math.temp 0