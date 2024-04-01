# Minimax 5 in a row (Go-Moku) game with Alpha-Beta pruning

This is an implementation of minimax with alpha-beta pruning and memoization of the Gomoku game, in Scala.

In the runs.md file you can find the different timings of the solutions, with various tweaks of the algorithm.

The game has a startup sequence, that will prompt the user for various game parameters such as board size and who starts. It can also start without such a sequence, by inserting a set of key
characters that are displayed on boot.

The code is written as close as possible to a functional approach.

## Strengths

It's pretty hard to beat, tested against other AIs of similar nature (minimax).

The AI will provide the next move within a maximum of 3 seconds.

## Faults

The AI seems to be a bit too defensive, and will often not take the opportunity to win the game, but rather block the opponent from winning. This is most notable in the end games, when if for example
the bottom row remains empty, it will not take the opportunity to win, but rather think that the opponent will block it, and therefore just continues to block the opponent.

## Other

Performance tracking in its current form, provides a huge overhead (in terms of overall execution time), and that's why it is present on a separate branch (master-with-traces) instead of on master.
This is due to the fact that traces are kept in a map, and minimax calls a lot some functions such as the scoring one.
