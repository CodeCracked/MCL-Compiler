#> mcl:math/unit_tests/float/convert_from_int
#   Test float addition
##

data modify storage u_test name set value float_cast_from_int
function u_test:run/reset

# test 1
# 1 = 1.0
scoreboard players set expected u_test 1065353216

scoreboard players set P0 mcl.math.io 1
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 2
# 2 = 2.0
scoreboard players set expected u_test 1073741824

scoreboard players set P0 mcl.math.io 2
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 3
# 4 = 4.0
scoreboard players set expected u_test 1082130432

scoreboard players set P0 mcl.math.io 4
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 4
# 8 = 8.0
scoreboard players set expected u_test 1090519040

scoreboard players set P0 mcl.math.io 8
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 5
# 16 = 16.0
scoreboard players set expected u_test 1098907648

scoreboard players set P0 mcl.math.io 16
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 6
# 32 = 32.0
scoreboard players set expected u_test 1107296256

scoreboard players set P0 mcl.math.io 32
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 6
# 1073741824 = 1073741824.0
scoreboard players set expected u_test 1317011456

scoreboard players set P0 mcl.math.io 1073741824
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 7
# 0 = 0.0
scoreboard players set expected u_test 0

scoreboard players set P0 mcl.math.io 0
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 8
# 3 = 3.0
scoreboard players set expected u_test 1077936128

scoreboard players set P0 mcl.math.io 3
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 9
# 641353 = 641353.0
scoreboard players set expected u_test 1226609808

scoreboard players set P0 mcl.math.io 641353
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 9
# 2147483647 = 2147483647.0
scoreboard players set expected u_test 1325400063

scoreboard players set P0 mcl.math.io 2147483647
function mcl:math/float/32/convert/from_int/main

scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


function u_test:run/end_set
