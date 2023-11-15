#> mcl:math/unit_tests/extended_float/truncate
#   Test float truncation
##

data modify storage u_test name set value float_truncation
function u_test:run/reset

# test 1
# 361.632 -> 361
scoreboard players set expected u_test 1135902720

scoreboard players set P0 mcl.math.io 1135923429
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/truncate/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 2
# 689432.4375 -> 689432
scoreboard players set expected u_test 1227379072

scoreboard players set P0 mcl.math.io 1227379079
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/truncate/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 3
# -361.632 -> -361
scoreboard players set expected u_test -1011580928

scoreboard players set P0 mcl.math.io -1011560219
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/truncate/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 4
# -689432.4375 -> -689432
scoreboard players set expected u_test -920104576

scoreboard players set P0 mcl.math.io -920104569
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/truncate/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 5
# 0 -> 0
scoreboard players set expected u_test 0

scoreboard players set P0 mcl.math.io 0
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/truncate/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

# test 6
# 700 -> 700
scoreboard players set expected u_test 1143930880

scoreboard players set P0 mcl.math.io 1143930880
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/truncate/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score


function u_test:run/end_set
