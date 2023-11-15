execute if score P1 mcl.math.io matches 1..2 run function mcl:math/extended_float/32/truncate/b/5
execute if score P1 mcl.math.io matches 3 run scoreboard players operation P2 mcl.math.io %= 1048576 mcl.math.constant
execute if score P1 mcl.math.io matches 4 run scoreboard players operation P2 mcl.math.io %= 524288 mcl.math.constant
execute if score P1 mcl.math.io matches 5..6 run function mcl:math/extended_float/32/truncate/b/8
