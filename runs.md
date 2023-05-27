### no-tricks run, 5x5 board
moves: 3 3 | 2 3 | 1 2 | 1 5

winner: 0ms = 6512 ns | 68036 times, total time = 443ms
next-wrapper: 0ms = 24967 ns | 1788 times, total time = 44ms
score-board: 1ms = 1100201 ns | 33120 times, total time = 36438ms
sequences: 0ms = 187879 ns | 66240 times, total time = 12445ms
minimax: 3ms = 3617594 ns | 34904 times, total time = 126268ms
board-lines: 0ms = 5769 ns | 66240 times, total time = 382ms
predict-next-best-move: 10863ms = 10863635175 ns | 4 times, total time = 43454ms
next: 0ms = 17356 ns | 1788 times, total time = 31ms
depth: 0ms = 702 ns | 84 times, total time = 0ms

Please enter your move: 3 3
0....
.....
..X..
.....
.....
Please enter your move: 2 3
0.0..
..X..
..X..
.....
.....
Please enter your move: 1 2
0X00.
..X..
..X..
.....
.....
Please enter your move: 1 5
0X00X
..X0.
..X..
.....
.....

### next-wrapper returns sorted moves by score

winner: 0ms = 6854 ns | 43836 times, total time = 300ms
next-wrapper: 119ms = 119587253 ns | 1062 times, total time = 127001ms
score-board: 2ms = 2211180 ns | 21384 times, total time = 47283ms
sequences: 0ms = 495084 ns | 172430 times, total time = 85367ms
minimax: 17ms = 17676274 ns | 22444 times, total time = 396726ms
board-lines: 0ms = 5751 ns | 172430 times, total time = 991ms
predict-next-best-move: 88654ms = 88654314150 ns | 2 times, total time = 177308ms
next: 0ms = 26001 ns | 1062 times, total time = 27ms
depth: 0ms = 945 ns | 46 times, total time = 0ms

### next-wrapper returns sorted moves by score, reversed

winner: 0ms = 6784 ns | 43836 times, total time = 297ms
next-wrapper: 114ms = 114311239 ns | 1062 times, total time = 121398ms
score-board: 2ms = 2133503 ns | 21384 times, total time = 45622ms
sequences: 0ms = 482441 ns | 169268 times, total time = 81661ms
minimax: 16ms = 16963791 ns | 22444 times, total time = 380735ms
board-lines: 0ms = 5845 ns | 169268 times, total time = 989ms
predict-next-best-move: 84999ms = 84999526500 ns | 2 times, total time = 169999ms
next: 0ms = 26642 ns | 1062 times, total time = 28ms
depth: 0ms = 682 ns | 46 times, total time = 0ms

### Memoized score-board
winner: 0ms = 7891 ns | 34918 times, total time = 275ms
score-board: 0ms = 577208 ns | 16560 times, total time = 9558ms
sequences: 0ms = 101101 ns | 33120 times, total time = 3348ms
minimax: 1ms = 1084379 ns | 34904 times, total time = 37849ms
board-lines: 0ms = 6799 ns | 33120 times, total time = 225ms
predict-next-best-move: 3458ms = 3458977375 ns | 4 times, total time = 13835ms
next: 0ms = 16736 ns | 1788 times, total time = 29ms
depth: 0ms = 667 ns | 84 times, total time = 0ms
