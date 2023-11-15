#> mcl:math/unit_tests/extended_float/power
#   Test float power
##

data modify storage u_test name set value float_power
function u_test:run/reset

# test 1
# 2^2=4
scoreboard players set expected u_test 1082130432

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
function mcl:math/float/32/add/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score



function u_test:run/end_set
