import Constants.newline

// TODO AT THE END FILTER OUT LEFTOVER UNUSED FUNCTIONS
object BoardUtils {
  var latestX: Int = -1
  var latestY: Int = -1

  type Line = List[Player]
  type Board = List[Line]

  def makeBoard(s: String): Board = {
    def toPos(c: Char): Player =
      c match {
        case 'X' => One
        case '0' => Two
        case _ => Empty
      }

    s.split(newline).toList.map(_.toList.map(toPos))
  }

  def display(b: Board, colorLatestMove: Boolean = true, colorPlayers: Boolean = true): Unit =
    println(toReadable(b, colorLatestMove, colorPlayers))

  def display(message: String, b: Board): Unit = {
    println(message)
    display(b)
  }

  def setLastMove(x: Int, y: Int): Unit = {
    // can make some sort of diff between the last board and current board
    latestX = x
    latestY = y
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
    sequences(p)(b).keys.exists(_ == 5)

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

  def sequences(p: Player)(b: Board): Map[Int, Int] = {
    val lines = b ++ getColumns(b) ++ List(getFstDiag(b)) ++ List(getSndDiag(b)) ++
      getAboveFstDiag(b) ++ getBelowFstDiag(b) ++ getAboveSndDiag(b) ++ getBelowSndDiag(b)

    lines.foldLeft(Map[Int, Int]())((acc, line) => {
      line.sliding(5).foldLeft(acc)((acc, seq) => {
        val free = seq.count(_ == Empty)
        val k = seq.count(_ == p)
        //        val other = 5 - k - free
        if (k + free == 5) {
          acc + (k -> (acc.getOrElse(k, 0) + 1))
        } else {
          acc
        }
      })
    })
  }

  def playedMoves(b: Board): Int = b.flatten.count(_ != Empty)

  def scoreLine(p: Player)(l: Line): Int = {
    val k = l.count(_ == p)
    val free = l.count(_ == Empty)
    val kInARowWithFreeSpace = l.foldLeft(0)((acc, pos) => if (pos == p || pos == Empty) acc + 1 else 0)
    if (kInARowWithFreeSpace >= 5) {
      kInARowWithFreeSpace * 5 + k * 2 + free
    } else {
      0
    }
  }

  def makeBoard(size: Int): Board = {
    List.fill(size)(List.fill(size)(Empty))
  }

  def scoreBoard(p: Player)(b: Board): Int = {
    val weights = Map(0 -> 0, 1 -> 0, 2 -> 20, 3 -> 80, 4 -> 100, 5 -> 1000)
    sequences(p)(b).foldLeft(0)((acc, kv) => {
      kv match {
        case (k, v) => acc + weights(k) * v
      }
    })
  }


  def toReadable(b: Board, colorLatestMove: Boolean = true, colorPlayers: Boolean = true): String = {
    def toChar(p: Player, omitColoring: Boolean = false): String =
      p match {
        case One => if (colorPlayers && !omitColoring) Console.YELLOW + 'X' + Console.RESET else "X"
        case Two => if (colorPlayers && !omitColoring) Console.BLUE + '0' + Console.RESET else "0"
        case _ => "."
      }

    if (colorLatestMove) {
      b.zipWithIndex.map {
        case (line, x) =>
          line.zipWithIndex.map {
            case (player, y) =>
              if (x == latestX && y == latestY) Console.RED + toChar(player, omitColoring = true) + Console.RESET
              else toChar(player)
          }.mkString
      }.mkString(newline)
    } else
      b.map(_.map(toChar(_)).mkString).mkString(newline)
  }

  def show(b: Board): String = {
    def toChar(p: Player): Char =
      p match {
        case One => 'X'
        case Two => '0'
        case _ => '.'
      }

    b.map(_.map(toChar).mkString).mkString(newline)
  }
}
