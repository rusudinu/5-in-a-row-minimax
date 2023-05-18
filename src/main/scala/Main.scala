import scala.io.StdIn.readLine
import scala.util.control.Breaks.{break, breakable}

object Main {
  type Line = List[Player]
  type Board = List[Line]

  // creates a board from a string
  def makeBoard(s: String): Board = {
    def toPos(c: Char): Player =
      c match {
        case 'X' => One
        case '0' => Two
        case _ => Empty
      }

    s.split(Constants.newline).toList.map(_.toList.map(toPos))
  }

  // checks if the position (x,y) board b is free
  def isFree(x: Int, y: Int, b: Board): Boolean = b(x)(y).equals(Empty)

  // returns the "other" player from a position, if it exists
  def complement(p: Player): Player = {
    p match {
      case One => Two
      case Two => One
      case _ => Empty
    }
  }

  def show(b: Board): String = {
    def toChar(p: Player): Char =
      p match {
        case One => 'X'
        case Two => '0'
        case _ => '.'
      }

    b.map(_.map(toChar).mkString).mkString(Constants.newline)
  }

  // Returns a list of columns from a board
  def getColumns(b: Board): Board =
    (for (i <- b.indices) yield (for (j <- b.indices) yield b(j)(i)).toList).toList


  //returns the first diagonal as a line
  def getFstDiag(b: Board): Line =
    (for (i <- b.indices) yield b(i)(i)).toList

  //returns the second diagonal as a line
  def getSndDiag(b: Board): Line =
    (for (i <- b.indices) yield b(i)(b.length - i - 1)).toList

  // retrieves all the diagonals above the first line
  def getAboveFstDiag(b: Board): List[Line] =
    (for (i <- 1 until b.length)
      yield (for (j <- 0 until b.length - i)
        yield b(j)(j + i)).toList).toList

  def getBelowFstDiag(b: Board): List[Line] =
    getAboveFstDiag(b.transpose)

  def getAboveSndDiag(b: Board): List[Line] =
    getAboveFstDiag(b.transpose.reverse.transpose)

  def getBelowSndDiag(b: Board): List[Line] =
    getAboveFstDiag(b.transpose.reverse)


  //write a function which checks if a given player is a winner
  //hints: patterns and exists
  def winner(p: Player)(b: Board): Boolean =
    b.exists(_.count(_ == p) >= 5) ||
      getColumns(b).exists(_.count(_ == p) >= 5) ||
      getFstDiag(b).count(_ == p) >= 5 ||
      getSndDiag(b).count(_ == p) >= 5 ||
      getAboveFstDiag(b).exists(_.count(_ == p) >= 5) ||
      getBelowFstDiag(b).exists(_.count(_ == p) >= 5) ||
      getAboveSndDiag(b).exists(_.count(_ == p) >= 5) ||
      getBelowSndDiag(b).exists(_.count(_ == p) >= 5)

  /*
   * Write a function which updates a position (with a player) at given indices from the board.
   * Your function need not check if the position is empty.
   * Partial stub - you can remove it if you want to implement it another way
   */

  def update(p: Player)(ln: Int, col: Int, b: Board): Board =
    b.updated(ln, b(ln).updated(col, p))


  /*
   * generates one possible next move for player p. Hint - use "isFree" and "update"
   *
   * */
  def next(p: Player)(b: Board): List[Board] =
    (for (i <- b.indices; j <- b(i).indices; if isFree(i, j, b)) yield update(p)(i, j, b)).toList

  def sequences(p: Player)(b: Board): Map[Int, Int] =
    (for (i <- 2 to 5) yield (i, b.count(_.count(_ == p) == i))).toMap

  def makeBoard(size: Int): Board = {
    List.fill(size)(List.fill(size)(Empty))
  }

  def predictNextBestMove(p: Player)(b: Board): Board = {
    next(p)(b).head
  }

  def game(): Unit = {
    print(Constants.welcomeMessage + Constants.newline + Constants.boardSizePrompt)
    val boardSize = readLine().toInt

    print(Constants.choseStartingPlayer)
    val startingPlayer = readLine().toInt
    val aiStarts = startingPlayer == 2

    if (boardSize < 5 && boardSize < Constants.maxBoardSize) {
      println(Constants.boardSizeError)
      game()
    }

    var board = makeBoard(boardSize)
    var canPredict = true
    while (!winner(One)(board) && !winner(Two)(board)) {
      breakable {
        if (aiStarts && canPredict) {
          board = predictNextBestMove(One)(board)
          println(show(board))
        }
        canPredict = true
        print(Constants.movePrompt)
        // TODO allow the user to use 'natural' coordinates, and just subtract 1 from both of them
        val input: String = readLine()
        if (!input.contains(" ")) {
          println(Constants.invalidInput)
          println(show(board))
          canPredict = false
          break
        }
        val Array(x, y) = input.split(" ").map(_.toInt)
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
          println(Constants.outOfBoundsError)
          println(show(board))
          canPredict = false
          break
        }
        if (!isFree(x, y, board)) {
          println(Constants.notFreeError)
          println(show(board))
          canPredict = false
          break
        }
        board = update(if (aiStarts) Two else One)(x, y, board)
        if (!aiStarts) {
          board = predictNextBestMove(Two)(board)
          println(show(board))
        }
      }
    }

    if (winner(One)(board)) {
      println(Constants.playerOneWon)
    } else if (winner(Two)(board)) {
      println(Constants.playerTwoWon)
    } else {
      println(Constants.draw)
    }
  }

  def main(args: Array[String]): Unit = {
    game()
  }
}

