#> mcl:math/bitwise/32/signed/to_bits/main
#   Convert integer into 32-bit boolean array (little endian)
##
# @params
#   mcl.math.io.P0
#       32-bit integer
# @returns
#   mcl.math.io.R[0..31]
#       32-bit array of boolean values
##

scoreboard players operation 0 mcl.math.temp = P0 mcl.math.io

scoreboard players set R0 mcl.math.io 0
scoreboard players set R1 mcl.math.io 0
scoreboard players set R2 mcl.math.io 0
scoreboard players set R3 mcl.math.io 0
scoreboard players set R4 mcl.math.io 0
scoreboard players set R5 mcl.math.io 0
scoreboard players set R6 mcl.math.io 0
scoreboard players set R7 mcl.math.io 0
scoreboard players set R8 mcl.math.io 0
scoreboard players set R9 mcl.math.io 0
scoreboard players set R10 mcl.math.io 0
scoreboard players set R11 mcl.math.io 0
scoreboard players set R12 mcl.math.io 0
scoreboard players set R13 mcl.math.io 0
scoreboard players set R14 mcl.math.io 0
scoreboard players set R15 mcl.math.io 0
scoreboard players set R16 mcl.math.io 0
scoreboard players set R17 mcl.math.io 0
scoreboard players set R18 mcl.math.io 0
scoreboard players set R19 mcl.math.io 0
scoreboard players set R20 mcl.math.io 0
scoreboard players set R21 mcl.math.io 0
scoreboard players set R22 mcl.math.io 0
scoreboard players set R23 mcl.math.io 0
scoreboard players set R24 mcl.math.io 0
scoreboard players set R25 mcl.math.io 0
scoreboard players set R26 mcl.math.io 0
scoreboard players set R27 mcl.math.io 0
scoreboard players set R28 mcl.math.io 0
scoreboard players set R29 mcl.math.io 0
scoreboard players set R30 mcl.math.io 0
scoreboard players set R31 mcl.math.io 0

execute if score 0 mcl.math.temp matches ..-1 run function mcl:math/bitwise/32/signed/to_bits/r31
execute if score 0 mcl.math.temp matches 1073741824.. run function mcl:math/bitwise/32/signed/to_bits/r30
execute if score 0 mcl.math.temp matches 536870912.. run function mcl:math/bitwise/32/signed/to_bits/r29
execute if score 0 mcl.math.temp matches 268435456.. run function mcl:math/bitwise/32/signed/to_bits/r28
execute if score 0 mcl.math.temp matches 134217728.. run function mcl:math/bitwise/32/signed/to_bits/r27
execute if score 0 mcl.math.temp matches 67108864.. run function mcl:math/bitwise/32/signed/to_bits/r26
execute if score 0 mcl.math.temp matches 33554432.. run function mcl:math/bitwise/32/signed/to_bits/r25
execute if score 0 mcl.math.temp matches 16777216.. run function mcl:math/bitwise/32/signed/to_bits/r24
execute if score 0 mcl.math.temp matches 8388608.. run function mcl:math/bitwise/32/signed/to_bits/r23
execute if score 0 mcl.math.temp matches 4194304.. run function mcl:math/bitwise/32/signed/to_bits/r22
execute if score 0 mcl.math.temp matches 2097152.. run function mcl:math/bitwise/32/signed/to_bits/r21
execute if score 0 mcl.math.temp matches 1048576.. run function mcl:math/bitwise/32/signed/to_bits/r20
execute if score 0 mcl.math.temp matches 524288.. run function mcl:math/bitwise/32/signed/to_bits/r19
execute if score 0 mcl.math.temp matches 262144.. run function mcl:math/bitwise/32/signed/to_bits/r18
execute if score 0 mcl.math.temp matches 131072.. run function mcl:math/bitwise/32/signed/to_bits/r17
execute if score 0 mcl.math.temp matches 65536.. run function mcl:math/bitwise/32/signed/to_bits/r16
execute if score 0 mcl.math.temp matches 32768.. run function mcl:math/bitwise/32/signed/to_bits/r15
execute if score 0 mcl.math.temp matches 16384.. run function mcl:math/bitwise/32/signed/to_bits/r14
execute if score 0 mcl.math.temp matches 8192.. run function mcl:math/bitwise/32/signed/to_bits/r13
execute if score 0 mcl.math.temp matches 4096.. run function mcl:math/bitwise/32/signed/to_bits/r12
execute if score 0 mcl.math.temp matches 2048.. run function mcl:math/bitwise/32/signed/to_bits/r11
execute if score 0 mcl.math.temp matches 1024.. run function mcl:math/bitwise/32/signed/to_bits/r10
execute if score 0 mcl.math.temp matches 512.. run function mcl:math/bitwise/32/signed/to_bits/r9
execute if score 0 mcl.math.temp matches 256.. run function mcl:math/bitwise/32/signed/to_bits/r8
execute if score 0 mcl.math.temp matches 128.. run function mcl:math/bitwise/32/signed/to_bits/r7
execute if score 0 mcl.math.temp matches 64.. run function mcl:math/bitwise/32/signed/to_bits/r6
execute if score 0 mcl.math.temp matches 32.. run function mcl:math/bitwise/32/signed/to_bits/r5
execute if score 0 mcl.math.temp matches 16.. run function mcl:math/bitwise/32/signed/to_bits/r4
execute if score 0 mcl.math.temp matches 8.. run function mcl:math/bitwise/32/signed/to_bits/r3
execute if score 0 mcl.math.temp matches 4.. run function mcl:math/bitwise/32/signed/to_bits/r2
execute if score 0 mcl.math.temp matches 2.. run function mcl:math/bitwise/32/signed/to_bits/r1
execute if score 0 mcl.math.temp matches 1.. run scoreboard players set R0 mcl.math.io 1