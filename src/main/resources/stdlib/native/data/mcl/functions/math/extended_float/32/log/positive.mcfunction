#> mcl:math/extended_float/32/log/positive
#   Compute the logarithm

# save for later

scoreboard players operation 9 mcl.math.temp = P2 mcl.math.io

# turn exponent int into float
scoreboard players operation P0 mcl.math.io = P1 mcl.math.io
function mcl:math/float/32/convert/from_int/main

# set mcl.math.io.P[0..2] from mcl.math.io R[0..2] if the main function changes

# ln2 -> 1060205080
scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io -1
scoreboard players set P5 mcl.math.io 3240472

# multiply exponent by ln2
function mcl:math/float/32/multiply/main

# save result of multiplying exponent to mcl.math.temp.[11..13]
scoreboard players operation 11 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 12 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 13 mcl.math.temp = R2 mcl.math.io

# turn mantissa to float (io.P[0,1]=0, mcl.math.io.P2=mantissa)
scoreboard players set P0 mcl.math.io 0
scoreboard players set P1 mcl.math.io 0
scoreboard players operation P2 mcl.math.io = 9 mcl.math.temp

# a = 0.67311669056
scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io -1
scoreboard players set P5 mcl.math.io 2904416

# multiply these together
function mcl:math/float/32/multiply/main

# save result to mcl.math.io.P[0..2]
scoreboard players set P0 mcl.math.io 0
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

# subtract 1
scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 0
scoreboard players set P5 mcl.math.io 0

function mcl:math/float/32/subtract/main

# initial mcl.math.constants

# save this to mcl.math.temp.[14..16] This is (ax-1)
scoreboard players operation 14 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 15 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 16 mcl.math.temp = R2 mcl.math.io

# make a duplicate copy at mcl.math.temp.[17..19]. This is (ax-1)^i
scoreboard players operation 17 mcl.math.temp = 14 mcl.math.temp
scoreboard players operation 18 mcl.math.temp = 15 mcl.math.temp
scoreboard players operation 19 mcl.math.temp = 16 mcl.math.temp

# accumulator at mcl.math.temp.[20..22] (copy mcl.math.temp.[14..16])) This is the sum
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io

# ✅ correct up to here

# well, 0.00967503584 vs 0.00967502593994

# i=2

# calculate exponent for i=2
function mcl:math/extended_float/32/log/calculate_exponent

# divide it by 2 (subtract 1 from exponent)
scoreboard players remove R1 mcl.math.io 1

# subtract it from accumulator
scoreboard players operation P0 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 22 mcl.math.temp

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/subtract/main

# set this to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io

# i=3

# ✅ correct up to here

# calculate exponent for i=3
function mcl:math/extended_float/32/log/calculate_exponent

# divide it by 3
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 1
scoreboard players set P5 mcl.math.io 4194304
function mcl:math/float/32/divide/main

# add it to accumulator
scoreboard players operation P0 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 22 mcl.math.temp

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/add/main

# set this to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io

# i=4

# calculate exponent for i=4
function mcl:math/extended_float/32/log/calculate_exponent

# divide it by 4 (subtract 2 from exponent)
scoreboard players remove R1 mcl.math.io 2

# subtract it from accumulator
scoreboard players operation P0 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 22 mcl.math.temp

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/subtract/main

# set this to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io

# i=5

# calculate exponent for i=5
function mcl:math/extended_float/32/log/calculate_exponent

# divide it by 5
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 2
scoreboard players set P5 mcl.math.io 2097152
function mcl:math/float/32/divide/main

# add it to accumulator
scoreboard players operation P0 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 22 mcl.math.temp

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/add/main

# set this to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io


# i=6

# calculate exponent for i=6
function mcl:math/extended_float/32/log/calculate_exponent

# divide it by 6
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 2
scoreboard players set P5 mcl.math.io 4194304
function mcl:math/float/32/divide/main

# subtract it from accumulator
scoreboard players operation P0 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 22 mcl.math.temp

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/subtract/main

# set this to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io

# i=7

# calculate exponent for i=7
function mcl:math/extended_float/32/log/calculate_exponent

# divide it by 7
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 2
scoreboard players set P5 mcl.math.io 6291456
function mcl:math/float/32/divide/main

# add it to accumulator
scoreboard players operation P0 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 22 mcl.math.temp

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/add/main

# set this to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io

# i=8

# calculate exponent for i=4
function mcl:math/extended_float/32/log/calculate_exponent

# divide it by 8 (subtract 3 from exponent)
scoreboard players remove R1 mcl.math.io 3

# subtract it from accumulator
scoreboard players operation P0 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 22 mcl.math.temp

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/subtract/main

# set this to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io

# If you need even more precision

# # i=9

# # calculate exponent for i=9
# function mcl:math/extended_float/32/log/calculate_exponent

# # divide it by 9
# scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
# scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
# scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

# scoreboard players set P3 mcl.math.io 0
# scoreboard players set P4 mcl.math.io 3
# scoreboard players set P5 mcl.math.io 1048576
# function mcl:math/float/32/divide/main

# # add it to accumulator
# scoreboard players operation P0 mcl.math.io = 20 mcl.math.temp
# scoreboard players operation P1 mcl.math.io = 21 mcl.math.temp
# scoreboard players operation P2 mcl.math.io = 22 mcl.math.temp

# scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
# scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
# scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
# function mcl:math/float/32/add/main

# # set this to the accumulator
# scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
# scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
# scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io


# # i=10

# # calculate exponent for i=10
# function mcl:math/extended_float/32/log/calculate_exponent

# # divide it by 10
# scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
# scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
# scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

# scoreboard players set P3 mcl.math.io 0
# scoreboard players set P4 mcl.math.io 3
# scoreboard players set P5 mcl.math.io 2097152
# function mcl:math/float/32/divide/main

# # subtract it from accumulator
# scoreboard players operation P0 mcl.math.io = 20 mcl.math.temp
# scoreboard players operation P1 mcl.math.io = 21 mcl.math.temp
# scoreboard players operation P2 mcl.math.io = 22 mcl.math.temp

# scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
# scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
# scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
# function mcl:math/float/32/subtract/main



# This is the final result so don't set it to mcl.math.temp.[20..22] but rather set it to mcl.math.io P.[0..2]
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

# ln(a), a=0.67311669056: 
scoreboard players set P3 mcl.math.io 1
scoreboard players set P4 mcl.math.io -2
scoreboard players set P5 mcl.math.io 4893463

# subtract to finally yield the logarithm of the mantissa + implicit bit
function mcl:math/float/32/subtract/main

# add the log of exponent and mantissa together

scoreboard players operation P0 mcl.math.io = 11 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 13 mcl.math.temp

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io

function mcl:math/float/32/add/main

# this is a monster function