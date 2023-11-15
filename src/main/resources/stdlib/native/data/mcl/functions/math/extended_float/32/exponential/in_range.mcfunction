#> mcl:math/extended_float/32/exponential/in_range
# Handles exponentation when the exponent is in range
#


# integer part
scoreboard players operation P0 mcl.math.io = 11 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 13 mcl.math.temp

scoreboard players set 0 mcl.math.temp 1
execute if score P0 mcl.math.io matches 0 if score P1 mcl.math.io matches -127 if score P2 mcl.math.io matches 0 run function mcl:math/extended_float/32/exponential/floor_zero
execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/exponential/floor_nonzero

# Decimal

# Get e^d using degree 7 taylor polynomial
# decimal part
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp
# subtract 0.5
scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io -1
scoreboard players set P5 mcl.math.io 0

function mcl:math/float/32/subtract/main
# copy output back to input
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

# multiply by ln2
scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io -1
scoreboard players set P5 mcl.math.io 3240472

function mcl:math/float/32/multiply/main

# This is ln(2)(x-0.5). Save it to mcl.math.temp.[14..16]
scoreboard players operation 14 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 15 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 16 mcl.math.temp = R2 mcl.math.io

# Save a copy (^ to the ith power) to mcl.math.temp.[17..19] (insert later)

# Set mcl.math.temp.[20..22] as the accumulator (begins at 1) (insert later)

# degree 1: Just add mcl.math.temp.[14..16] and 1
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

# this is 1
scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 0
scoreboard players set P5 mcl.math.io 0
function mcl:math/float/32/add/main

# save it to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io

# degree 2:
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 16 mcl.math.temp
function mcl:math/float/32/multiply/main

# Save this to mcl.math.temp.[17..19]
scoreboard players operation 17 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 18 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 19 mcl.math.temp = R2 mcl.math.io

# Divide by two and add it to accumulator mcl.math.temp.[9..11] (bitshift right)
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
# bitshift right
scoreboard players remove P1 mcl.math.io 1
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 22 mcl.math.temp

function mcl:math/float/32/add/main
# save it to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io


# degree 3:
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 17 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 18 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 19 mcl.math.temp
function mcl:math/float/32/multiply/main

# Save this to mcl.math.temp.[6..8]
scoreboard players operation 17 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 18 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 19 mcl.math.temp = R2 mcl.math.io

# Divide by 3! and add it to accumulator mcl.math.temp.[9..11]
# divide by six
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 2
scoreboard players set P5 mcl.math.io 4194304
function mcl:math/float/32/divide/main

# add to accumulator
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 22 mcl.math.temp

function mcl:math/float/32/add/main
# save it to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io


# degree 4:
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 17 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 18 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 19 mcl.math.temp
function mcl:math/float/32/multiply/main

# Save this to mcl.math.temp.[6..8]
scoreboard players operation 17 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 18 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 19 mcl.math.temp = R2 mcl.math.io

# Divide by 4! and add it to accumulator mcl.math.temp.[9..11]
# divide by 24
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 4
scoreboard players set P5 mcl.math.io 4194304
function mcl:math/float/32/divide/main

# add to accumulator
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 22 mcl.math.temp

function mcl:math/float/32/add/main
# save it to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io


# degree 5:
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 17 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 18 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 19 mcl.math.temp
function mcl:math/float/32/multiply/main

# Save this to mcl.math.temp.[6..8]
scoreboard players operation 17 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 18 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 19 mcl.math.temp = R2 mcl.math.io

# Divide by 5! and add it to accumulator mcl.math.temp.[9..11]
# divide by five
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 6
scoreboard players set P5 mcl.math.io 7340032
function mcl:math/float/32/divide/main

# add to accumulator
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 22 mcl.math.temp

function mcl:math/float/32/add/main
# save it to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io


# degree 6:
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 17 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 18 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 19 mcl.math.temp
function mcl:math/float/32/multiply/main

# Save this to mcl.math.temp.[6..8]
scoreboard players operation 17 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 18 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 19 mcl.math.temp = R2 mcl.math.io

# Divide by 6! and add it to accumulator mcl.math.temp.[9..11]
# divide by 720
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 9
scoreboard players set P5 mcl.math.io 3407872
function mcl:math/float/32/divide/main

# add to accumulator
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 22 mcl.math.temp

function mcl:math/float/32/add/main
# save it to the accumulator
scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io


# degree 7:
scoreboard players operation P0 mcl.math.io = 14 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 15 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 16 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 17 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 18 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 19 mcl.math.temp
function mcl:math/float/32/multiply/main

# Save this to mcl.math.temp.[6..8]
scoreboard players operation 17 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 18 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 19 mcl.math.temp = R2 mcl.math.io

# Divide by 7! and add it to accumulator mcl.math.temp.[9..11]
# divide by 
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 12
scoreboard players set P5 mcl.math.io 1933312
function mcl:math/float/32/divide/main

# add to accumulator
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players operation P3 mcl.math.io = 20 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 21 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 22 mcl.math.temp

function mcl:math/float/32/add/main
# # save it to the accumulator
# scoreboard players operation 20 mcl.math.temp = R0 mcl.math.io
# scoreboard players operation 21 mcl.math.temp = R1 mcl.math.io
# scoreboard players operation 22 mcl.math.temp = R2 mcl.math.io

# Final step so don't save it to the accumulator, push back to input and multiply by sqrt2
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 0
scoreboard players set P5 mcl.math.io 3474675
function mcl:math/float/32/multiply/main

# Then multiply it to the integer part
scoreboard players operation P0 mcl.math.io = 11 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 13 mcl.math.temp

scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io
function mcl:math/float/32/multiply/main

# And that's it!