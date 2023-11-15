#> mcl:math/extended_float/32/exponential/non_zero
#   e^x

# Convert e^x to 2^((1/ln2)(2^(E-23)(2^23+m)))

# Get (2^(E-23)(2^23+m)) (already loaded into mcl.math.io.P[0..2])

# Multiply this by 1/ln2

scoreboard players set P3 mcl.math.io 0
scoreboard players set P4 mcl.math.io 0
scoreboard players set P5 mcl.math.io 3713595

function mcl:math/float/32/multiply/main

# Set mcl.math.io.P[1..2] from mcl.math.io.R[1..2]
scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

# Now we have sign((1/ln2)(2^(E-23))(2^23+m)

# save this to mcl.math.temp.[0..2]
scoreboard players operation 11 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 12 mcl.math.temp = P1 mcl.math.io
scoreboard players operation 13 mcl.math.temp = P2 mcl.math.io

# Split integer and decimal part (note decimal is in [0,1))

function mcl:math/extended_float/32/floor/main

# I+d
scoreboard players operation P0 mcl.math.io = 11 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 13 mcl.math.temp

# integer part
scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
scoreboard players operation P5 mcl.math.io = R2 mcl.math.io

# Save integer part
scoreboard players operation 11 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 12 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 13 mcl.math.temp = R2 mcl.math.io

function mcl:math/float/32/subtract/main
# save decimal part
scoreboard players operation 14 mcl.math.temp = R0 mcl.math.io
scoreboard players operation 15 mcl.math.temp = R1 mcl.math.io
scoreboard players operation 16 mcl.math.temp = R2 mcl.math.io

# Check if exponent is greater than what floats can handle:
scoreboard players operation P0 mcl.math.io = 11 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 12 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 13 mcl.math.temp

# If positive, check if integer part is greater or equal to 128
execute if score 11 mcl.math.temp matches 0 run function mcl:math/extended_float/32/exponential/positive

# If negative, check if integer part is less or equal to 128
execute if score 11 mcl.math.temp matches 1 run function mcl:math/extended_float/32/exponential/negative

# Exponent is in range:
execute if score 11 mcl.math.temp matches 0..1 run function mcl:math/extended_float/32/exponential/in_range