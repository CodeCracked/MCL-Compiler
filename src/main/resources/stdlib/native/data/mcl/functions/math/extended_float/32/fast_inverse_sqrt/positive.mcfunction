#> mcl:math/extended_float/32/fast_inverse_sqrt/positive
#   Positive case

# save x2
scoreboard players operation 11 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 12 mcl.math.temp = P1 mcl.math.io
scoreboard players remove 12 mcl.math.temp 1
scoreboard players operation 13 mcl.math.temp = P2 mcl.math.io

# evil floating point bit hack
function mcl:math/float/32/recompose/main
scoreboard players operation P1 mcl.math.io = R0 mcl.math.io

# huh
scoreboard players operation P1 mcl.math.io /= 2 mcl.math.constant
scoreboard players set P0 mcl.math.io 1597463007
scoreboard players operation P0 mcl.math.io -= P1 mcl.math.io
function mcl:math/float/32/decompose/main

# save y
scoreboard players operation 14 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 15 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 16 mcl.math.temp = R2 mcl.math.io

# 1st iteration

# y^2
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 16 mcl.math.temp
function mcl:math/float/32/multiply/main

# x2*y^2
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 11 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 13 mcl.math.temp
function mcl:math/float/32/multiply/main

# 1.5 - x2*y^2
scoreboard players set P0 mcl.math.io 0
scoreboard players set P1 mcl.math.io 0
scoreboard players set P2 mcl.math.io 4194304

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/subtract/main

# y * (1.5 - x2*y^2)
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 16 mcl.math.temp
function mcl:math/float/32/multiply/main



# second iteration (optional)

# save y
scoreboard players operation 14 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 15 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 16 mcl.math.temp = R2 mcl.math.io

# 1st iteration

# y^2
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 16 mcl.math.temp
function mcl:math/float/32/multiply/main

# x2*y^2
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 11 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 13 mcl.math.temp
function mcl:math/float/32/multiply/main

# 1.5 - x2*y^2
scoreboard players set P0 mcl.math.io 0
scoreboard players set P1 mcl.math.io 0
scoreboard players set P2 mcl.math.io 4194304

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/subtract/main

# y * (1.5 - x2*y^2)
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 16 mcl.math.temp
function mcl:math/float/32/multiply/main

scoreboard players set 11 mcl.math.temp 0