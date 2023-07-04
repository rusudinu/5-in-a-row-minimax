# Minimax 5 in a row (Gomoku) game

This is an implementation of minimax with alpha-beta pruning and memoization of the Gomoku game, in Scala.

In the runs.md file you can find the different timings of the solutions, with various tweaks of the algorithm.

The game has a startup sequence, that will prompt the user for various game parameters such as board size and who starts. It can also start without such a sequence, by inserting a set of key characters that are displayed on boot.

The code is written as close as possible to a functional approach.