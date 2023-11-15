scoreboard players set P0 mcl.math.io 0
scoreboard players set P1 mcl.math.io 1
scoreboard players set P2 mcl.math.io 0

scoreboard players set P3 mcl.math.io 1
scoreboard players set P4 mcl.math.io 1
scoreboard players set P5 mcl.math.io 4194304


#> mcl:math/extended_float/32/power/main
#   Return the first 32-bit float raised to the power of the second.
##
# @params
#   mcl.math.io.P[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#   mcl.math.io.P[3, 4, 5]
#       32-bit sign, 32-bit exponent, 32-bit significand
# @returns
#   mcl.math.io.R[0, 1, 2]
#       32-bit sign, 32-bit exponent, 32-bit significand
#
# @modifies mcl.math.temp.[0..25]
scoreboard players operation 0 mcl.math.temp = P0 mcl.math.io
scoreboard players operation 1 mcl.math.temp = P1 mcl.math.io
scoreboard players operation 2 mcl.math.temp = P2 mcl.math.io

scoreboard players operation 3 mcl.math.temp = P3 mcl.math.io
scoreboard players operation 4 mcl.math.temp = P4 mcl.math.io
scoreboard players operation 5 mcl.math.temp = P5 mcl.math.io

scoreboard players operation P0 mcl.math.io = P3 mcl.math.io
scoreboard players operation P1 mcl.math.io = P4 mcl.math.io
scoreboard players operation P2 mcl.math.io = P5 mcl.math.io

function mcl:math/extended_float/32/float_type/main

scoreboard players operation P0 mcl.math.io = 0 mcl.math.temp
scoreboard players operation P1 mcl.math.io = 1 mcl.math.temp
scoreboard players operation P2 mcl.math.io = 2 mcl.math.temp

scoreboard players operation P3 mcl.math.io = 3 mcl.math.temp
scoreboard players operation P4 mcl.math.io = 4 mcl.math.temp
scoreboard players operation P5 mcl.math.io = 5 mcl.math.temp

scoreboard players set 0 mcl.math.temp 1
execute if score R0 mcl.math.io matches 2 run function mcl:math/extended_float/32/power/not_integer_exponent/main
## execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/integer_exponent/main

    scoreboard players set 0 mcl.math.temp 1
    # execute if score P4 mcl.math.io matches ..-1 run function mcl:math/extended_float/32/power/integer_exponent/zero
    ##execute if score 0 mcl.math.temp matches 1 if score P3 mcl.math.io matches 1 run function mcl:math/extended_float/32/power/integer_exponent/negative
    
        scoreboard players operation 9 mcl.math.temp = P3 mcl.math.io
        scoreboard players operation 10 mcl.math.temp = P4 mcl.math.io
        scoreboard players operation 11 mcl.math.temp = P5 mcl.math.io

        scoreboard players operation P3 mcl.math.io = P0 mcl.math.io
        scoreboard players operation P4 mcl.math.io = P1 mcl.math.io
        scoreboard players operation P5 mcl.math.io = P2 mcl.math.io

        scoreboard players set P0 mcl.math.io 0
        scoreboard players set P1 mcl.math.io 0
        scoreboard players set P2 mcl.math.io 0

        function mcl:math/float/32/divide/main

        scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
        scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
        scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

        scoreboard players set P3 mcl.math.io 0
        scoreboard players operation P4 mcl.math.io = 10 mcl.math.temp
        scoreboard players operation P5 mcl.math.io = 11 mcl.math.temp

        ## function mcl:math/extended_float/32/power/integer_exponent/main
            scoreboard players set 0 mcl.math.temp 1
            execute if score P4 mcl.math.io matches ..-1 run function mcl:math/extended_float/32/power/integer_exponent/zero
            execute if score 0 mcl.math.temp matches 1 if score P3 mcl.math.io matches 1 run function mcl:math/extended_float/32/power/integer_exponent/negative
            execute if score 0 mcl.math.temp matches 1 if score P4 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/integer_exponent/one
            ## execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/integer_exponent/check_even
                scoreboard players operation 0 mcl.math.temp = P0 mcl.math.io
                scoreboard players operation 1 mcl.math.temp = P1 mcl.math.io
                scoreboard players operation 2 mcl.math.temp = P2 mcl.math.io

                scoreboard players operation 3 mcl.math.temp = P3 mcl.math.io
                scoreboard players operation 4 mcl.math.temp = P4 mcl.math.io
                scoreboard players operation 5 mcl.math.temp = P5 mcl.math.io

                scoreboard players operation P0 mcl.math.io = P3 mcl.math.io
                scoreboard players operation P1 mcl.math.io = P4 mcl.math.io
                scoreboard players operation P2 mcl.math.io = P5 mcl.math.io

                function mcl:math/extended_float/32/float_type/main

                scoreboard players operation P0 mcl.math.io = 0 mcl.math.temp
                scoreboard players operation P1 mcl.math.io = 1 mcl.math.temp
                scoreboard players operation P2 mcl.math.io = 2 mcl.math.temp

                scoreboard players operation P3 mcl.math.io = 3 mcl.math.temp
                scoreboard players operation P4 mcl.math.io = 4 mcl.math.temp
                scoreboard players operation P5 mcl.math.io = 5 mcl.math.temp

                scoreboard players set 0 mcl.math.temp 1
                # assume decimals are even
                execute if score R0 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/integer_exponent/even
                ## execute if score 0 mcl.math.temp matches 1 if score R0 mcl.math.io matches 1 run function mcl:math/extended_float/32/power/integer_exponent/odd
                    scoreboard players operation 11 mcl.math.temp = P3 mcl.math.io
                    scoreboard players operation 12 mcl.math.temp = P4 mcl.math.io
                    scoreboard players operation 13 mcl.math.temp = P5 mcl.math.io

                    data modify storage calculate stack append value [0,0,0]
                    execute store result storage calculate stack[-1][0] int 1 run scoreboard players operation P3 mcl.math.io = P0 mcl.math.io
                    execute store result storage calculate stack[-1][1] int 1 run scoreboard players operation P4 mcl.math.io = P1 mcl.math.io
                    execute store result storage calculate stack[-1][2] int 1 run scoreboard players operation P5 mcl.math.io = P2 mcl.math.io


                    function mcl:math/float/32/multiply/main

                    # n
                    scoreboard players operation P0 mcl.math.io = 11 mcl.math.temp
                    scoreboard players operation P1 mcl.math.io = 12 mcl.math.temp
                    scoreboard players operation P2 mcl.math.io = 13 mcl.math.temp

                    # x^2
                    scoreboard players operation 11 mcl.math.temp = R0 mcl.math.io
                    scoreboard players operation 12 mcl.math.temp = R1 mcl.math.io
                    scoreboard players operation 13 mcl.math.temp = R2 mcl.math.io

                    scoreboard players set P3 mcl.math.io 0
                    scoreboard players set P4 mcl.math.io 0
                    scoreboard players set P5 mcl.math.io 0

                    function mcl:math/float/32/subtract/main

                    scoreboard players operation P0 mcl.math.io = 11 mcl.math.temp
                    scoreboard players operation P1 mcl.math.io = 12 mcl.math.temp
                    scoreboard players operation P2 mcl.math.io = 13 mcl.math.temp
                    # (n-1)/2
                    scoreboard players operation P3 mcl.math.io = R0 mcl.math.io
                    scoreboard players operation P4 mcl.math.io = R1 mcl.math.io
                    scoreboard players remove P4 mcl.math.io 1
                    scoreboard players operation P5 mcl.math.io = R2 mcl.math.io

                    ## function mcl:math/extended_float/32/power/integer_exponent/main
                        scoreboard players set 0 mcl.math.temp 1
                        execute if score P4 mcl.math.io matches ..-1 run function mcl:math/extended_float/32/power/integer_exponent/zero
                        execute if score 0 mcl.math.temp matches 1 if score P3 mcl.math.io matches 1 run function mcl:math/extended_float/32/power/integer_exponent/negative
                        ## execute if score 0 mcl.math.temp matches 1 if score P4 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/integer_exponent/one
                            scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
                            scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
                            scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

                            scoreboard players set 0 mcl.math.temp 0
                        # execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/integer_exponent/check_even

                        # scoreboard players set 0 mcl.math.temp 0
                    # scoreboard players operation P0 mcl.math.io = R0 mcl.math.io
                    # scoreboard players operation P1 mcl.math.io = R1 mcl.math.io
                    # scoreboard players operation P2 mcl.math.io = R2 mcl.math.io

                    # execute store result score P3 mcl.math.io run data get storage calculate stack[-1][0]
                    # execute store result score P4 mcl.math.io run data get storage calculate stack[-1][1]
                    # execute store result score P5 mcl.math.io run data get storage calculate stack[-1][2]
                    # data remove storage calculate stack[-1]
                    # function mcl:math/float/32/multiply/main

                    # scoreboard players set 0 mcl.math.temp 0



                # execute if score 0 mcl.math.temp matches 1 if score R0 mcl.math.io matches 2 run say ERROR: exponent somehow not an integer
                # scoreboard players set 0 mcl.math.temp 0

            # scoreboard players set 0 mcl.math.temp 0

        # scoreboard players set 0 mcl.math.temp 0
    # execute if score 0 mcl.math.temp matches 1 if score P4 mcl.math.io matches 0 run function mcl:math/extended_float/32/power/integer_exponent/one
    # execute if score 0 mcl.math.temp matches 1 run function mcl:math/extended_float/32/power/integer_exponent/check_even

    # scoreboard players set 0 mcl.math.temp 0