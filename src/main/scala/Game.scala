import AI.predictNextBestMove
import BoardUtils.{display, isFree, makeBoard, scoreBoard, sequences, update, winner}
import Constants.{boardSizeError, draw, invalidInput, maxBoardSize, movePrompt, notFreeError, outOfBoundsError, playerOneWon, playerTwoWon, stoppedEarly}
import GameUtils.init

import scala.io.StdIn.readLine
import scala.util.control.Breaks.{break, breakable}

object Game {
  def start(): Unit = {
    val (boardSize, usingNaturalCoordinates, aiStarts) = init

    var canPredict = true
    var stop = false

    if (boardSize < 5 && boardSize < maxBoardSize) {
      println(boardSizeError)
      start()
    }

    //    val medium1 =
    //      """X00.0X
    //        |XXXX0.
    //        |0.X0..
    //        |.X0.0.
    //        |0X..00
    //        |.....0""".stripMargin.replace("\r\n", "\n")
    //    var board = makeBoard(medium1)
    //    println(sequences(One)(board))
    //    println(scoreBoard(One)(board))
    //    println(scoreBoard(Two)(board))

    var board = makeBoard(boardSize)

    while (!winner(One)(board) && !winner(Two)(board) && !stop) {
      breakable {
        if (aiStarts && canPredict) {
          board = predictNextBestMove(One)(board)(aiStarts)
          display(board)
        }
        canPredict = true
        print(movePrompt)

        val input: String = readLine()

        if (input == "exit" || input == "quit" || input == "q" || input == "e") {
          stop = true
          break
        }

        if (!input.contains(" ")) {
          display(invalidInput, board)
          canPredict = false
          break
        }

        val Array(x, y) = input.split(" ").map(
          if (usingNaturalCoordinates)
            _.toInt - 1
          else _.toInt
        )

        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
          display(outOfBoundsError, board)
          canPredict = false
          break
        }
        if (!isFree(x, y, board)) {
          display(notFreeError, board)
          canPredict = false
          break
        }
        board = update(if (aiStarts) Two else One)(x, y, board)
        if (!aiStarts) {
          board = predictNextBestMove(Two)(board)(aiStarts)
          display(board)
        }
      }
    }

    if (stop) {
      println(stoppedEarly)
      return
    }
    if (winner(One)(board)) {
      println(playerOneWon)
    } else {
      println(if (winner(Two)(board)) playerTwoWon else draw)
    }
  }
}
