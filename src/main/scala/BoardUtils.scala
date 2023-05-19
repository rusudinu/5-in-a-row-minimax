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
    println(toString(b, colorLatestMove, colorPlayers))

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

  def sequences(p: Player)(b: Board): Map[Int, Int] = {
    // se uita la fiecare segment de 5 pozitii consecutive pe fiecare linie
    // am k pozitii, restul de 5-k sunt libere? => k secventa
    // for (i <- 2 until 5)
    // if b(j).count(_ == p) == i && b(j).count(_ == Empty) == 5 - i
    // return an empty map
    Map()
  }

  /*
  b.exists(_.count(_ == p) >= 5) ||
    getColumns(b).exists(_.count(_ == p) >= 5) ||
    getFstDiag(b).count(_ == p) >= 5 ||
    getSndDiag(b).count(_ == p) >= 5 ||
    getAboveFstDiag(b).exists(_.count(_ == p) >= 5) ||
    getBelowFstDiag(b).exists(_.count(_ == p) >= 5) ||
    getAboveSndDiag(b).exists(_.count(_ == p) >= 5) ||
    getBelowSndDiag(b).exists(_.count(_ == p) >= 5)
   */

  def scoreBoard(p: Player)(b: Board): Int = {
    b.foldLeft(0) {
      case (score, line) => score + scoreLine(p)(line)
    } + getColumns(b).foldLeft(0) {
      case (score, line) => score + scoreLine(p)(line)
    } + scoreLine(p)(getFstDiag(b)) + scoreLine(p)(getSndDiag(b)) +
      getAboveFstDiag(b).foldLeft(0) {
        case (score, line) => score + scoreLine(p)(line)
      } + getBelowFstDiag(b).foldLeft(0) {
      case (score, line) => score + scoreLine(p)(line)
    } + getAboveSndDiag(b).foldLeft(0) {
      case (score, line) => score + scoreLine(p)(line)
    } + getBelowSndDiag(b).foldLeft(0) {
      case (score, line) => score + scoreLine(p)(line)
    }
  }

  def scoreLine(p: Player)(l: Line): Int = {
    l.sliding(5).foldLeft(0) {
      case (score, window) =>
        val consecutive = window.foldLeft(0) {
          case (consecutive, p) =>
            if (p == Empty) consecutive
            else if (p == complement(p)) 0
            else consecutive + 1
        } * 2
        val playerPositions = window.count(_ == p)
        val empty = window.count(_ == Empty)
        val enemyConsecutive = window.count(_ == complement(p))
        val emptyNearConsecutive = 0 // TODO compute this
        score + consecutive + empty + playerPositions + emptyNearConsecutive - enemyConsecutive
    }
  }

  def makeBoard(size: Int): Board = {
    List.fill(size)(List.fill(size)(Empty))
  }

  def toString(b: Board, colorLatestMove: Boolean = true, colorPlayers: Boolean = true): String = {
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
}
