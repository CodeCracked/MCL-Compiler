#> mcl:math/float/32/compare/less/branch0
#   Signs are the same
#

execute if score P1 mcl.math.io < P4 mcl.math.io run scoreboard players set R0 mcl.math.io 1
execute if score P1 mcl.math.io = P4 mcl.math.io if score P2 mcl.math.io < P5 mcl.math.io run scoreboard players set R0 mcl.math.io 1