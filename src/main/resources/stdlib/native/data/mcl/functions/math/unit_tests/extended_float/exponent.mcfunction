#> mcl:math/unit_tests/extended_float/exponent
#   Test float exponentation
##

data modify storage u_test name set value float_exponentation
function u_test:run/reset

# test 1
# e^0 = 1
scoreboard players set expected u_test 1065353216

scoreboard players set P0 mcl.math.io 0
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/exponential/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 2
# e^1 = e
scoreboard players set expected u_test 1076754516

scoreboard players set P0 mcl.math.io 1065353216
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/exponential/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 3
# e^2 = 7.3890561
scoreboard players set expected u_test 1089237793

scoreboard players set P0 mcl.math.io 1073741824
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/exponential/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 4
# e^46.23 = 1.1951816e+20 
scoreboard players set expected u_test 1624200315

scoreboard players set P0 mcl.math.io 1111026565
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/exponential/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 5
# e^-2 = 0.13533528323
scoreboard players set expected u_test 1040880981

scoreboard players set P0 mcl.math.io -1073741824
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/exponential/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score

# test 6
# e^-31.7413 = 1.6403199e-14 
scoreboard players set expected u_test 680771390

scoreboard players set P0 mcl.math.io -1040323025
function mcl:math/float/32/decompose/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/extended_float/32/exponential/main
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/recompose/main
scoreboard players operation actual u_test = R0 mcl.math.io

function u_test:run/score



function u_test:run/end_set
