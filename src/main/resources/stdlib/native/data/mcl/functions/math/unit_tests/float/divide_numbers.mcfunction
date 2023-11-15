#> mcl:math/unit_tests/float/divide_numbers
#   Test float division
##

data modify storage u_test name set value float_division
function u_test:run/reset

# test 1
# 27504.27734375 / NaN = NaN
scoreboard players set expected u_test 2139095041

scoreboard players set P0 mcl.math.io 2139095041
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1199259045
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 2
# NaN / 27504.27734375 = NaN
scoreboard players set expected u_test 2139095041

scoreboard players set P0 mcl.math.io 1199259045
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 2139095041
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 3
# NaN / inf = NaN
scoreboard players set expected u_test 2139095041

scoreboard players set P0 mcl.math.io 2139095040
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 2139095041
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 4
# NaN / -inf = NaN
scoreboard players set expected u_test 2139095041

scoreboard players set P0 mcl.math.io -8388608
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 2139095041
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 5
# inf / -inf = NaN
scoreboard players set expected u_test 2139095041

scoreboard players set P0 mcl.math.io -8388608
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 2139095040
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 6
# inf / -inf = NaN
scoreboard players set expected u_test 2139095041

scoreboard players set P0 mcl.math.io 2139095040
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io -8388608
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 7
# -inf / -inf = NaN
scoreboard players set expected u_test 2139095041

scoreboard players set P0 mcl.math.io -8388608
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io -8388608
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 8
# inf / inf = NaN
scoreboard players set expected u_test 2139095041

scoreboard players set P0 mcl.math.io 2139095040
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 2139095040
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 9
# inf / pos_number = inf
scoreboard players set expected u_test 2139095040

scoreboard players set P0 mcl.math.io 83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 2139095040
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 10
# inf / neg_number = -inf
scoreboard players set expected u_test -8388608

scoreboard players set P0 mcl.math.io -83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 2139095040
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 11
# pos_number / inf = 0
scoreboard players set expected u_test 0

scoreboard players set P0 mcl.math.io 2139095040
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 12
# neg_number / inf = -0
scoreboard players set expected u_test -2147483648

scoreboard players set P0 mcl.math.io 2139095040
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io -83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score



# test 13
# pos_number / -inf = 0
scoreboard players set expected u_test -2147483648

scoreboard players set P0 mcl.math.io -8388608
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 14
# neg_number / -inf = 0
scoreboard players set expected u_test 0

scoreboard players set P0 mcl.math.io -8388608
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io -83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 15
# pos_number / 0 = inf
scoreboard players set expected u_test 2139095040

scoreboard players set P0 mcl.math.io 0
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 16
# neg_number / -0 = inf
scoreboard players set expected u_test 2139095040

scoreboard players set P0 mcl.math.io -2147483648
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io -83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score



# test 17
# pos_number / -0 = -inf
scoreboard players set expected u_test -8388608

scoreboard players set P0 mcl.math.io -2147483648
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 18
# neg_number / 0 = -inf
scoreboard players set expected u_test -8388608

scoreboard players set P0 mcl.math.io 0
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io -83283428
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 19
# 2 / 2 = 1
scoreboard players set expected u_test 1065353216

scoreboard players set P0 mcl.math.io 1073741824
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1073741824
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 20
# 2 / 8 = 16
scoreboard players set expected u_test 1048576000

scoreboard players set P0 mcl.math.io 1090519040
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1073741824
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 21
# 27 / 3 = 9
scoreboard players set expected u_test 1091567616

scoreboard players set P0 mcl.math.io 1077936128
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1104674816
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 22
# 66323852 / 7432.64208984375 = 8923.3212890625
scoreboard players set expected u_test 1175153993

scoreboard players set P0 mcl.math.io 1172849955
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1283260771
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 23
# 66323852 / -7432.64208984375 = -8923.3212890625
scoreboard players set expected u_test -972329655

scoreboard players set P0 mcl.math.io -974633693
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1283260771
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 24
# 66323852 / -8923.3212890625 = -7432.64208984375
scoreboard players set expected u_test -974633692

scoreboard players set P0 mcl.math.io -972329656
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1283260771
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 25
# -53567.515625 / -63.63137054443359375 = 841.84130859375
scoreboard players set expected u_test 1146254808

scoreboard players set P0 mcl.math.io -1031895418
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io -950976636
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 26
# -53567.51953125 / -63.63137054443359375 = 841.84136962890625
scoreboard players set expected u_test 1146254809

scoreboard players set P0 mcl.math.io -1031895418
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io -950976635
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 27
# small number / small number = 1
scoreboard players set expected u_test 1065353216

scoreboard players set P0 mcl.math.io 1
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 28
# 2 * small number / small number = 1
scoreboard players set expected u_test 1073741824

scoreboard players set P0 mcl.math.io 1
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 2
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 29
# small number / 2 = 0
scoreboard players set expected u_test 0

scoreboard players set P0 mcl.math.io 1073741824
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


# test 30
# large number / small number = inf
scoreboard players set expected u_test 2139095040

scoreboard players set P0 mcl.math.io 1
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 2080374784
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score



# test 31
# small number / large number = 0
scoreboard players set expected u_test 0

scoreboard players set P0 mcl.math.io 2080374784
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score



# test 32
# 0.019062407314777374267578125 / 6.0 = 0.00317706796340644359588623046875
scoreboard players set expected u_test 995112539

scoreboard players set P0 mcl.math.io 1086324736
function mcl:math/float/32/decompose/main
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
scoreboard players set P0 mcl.math.io 1016867012
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/divide/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


function u_test:run/end_set
