import Constants.newline

object BoardUtils {
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

  def display(b: Board): Unit =
    println(toReadable(b))

  def display(message: String, b: Board): Unit = {
    println(message)
    display(b)
  }

  def isFree(x: Int, y: Int, b: Board): Boolean = b(x)(y).equals(Empty)

  def complement(p: Player): Player = {
    p match {
      case One => Two
      case Two => One
      case _ => Empty
    }
  }

  def getColumns(b: Board): Board =
    (for (i <- b.indices) yield (for (j <- b.indices) yield b(j)(i)).toList).toList


  def getFstDiag(b: Board): Line =
    (for (i <- b.indices) yield b(i)(i)).toList

  def getSndDiag(b: Board): Line =
    (for (i <- b.indices) yield b(i)(b.length - i - 1)).toList

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


  def winner(p: Player)(b: Board): Boolean =
    sequences(p)(b).keys.exists(_ == 5)

  def update(p: Player)(ln: Int, col: Int, b: Board): Board =
    b.updated(ln, b(ln).updated(col, p))

  def next(p: Player)(b: Board): List[Board] =
    (for (i <- b.indices; j <- b(i).indices; if isFree(i, j, b)) yield update(p)(i, j, b)).toList


  def boardLines(b: Board): List[Line] =
    b ++ getColumns(b) ++ List(getFstDiag(b)) ++ List(getSndDiag(b)) ++
      getAboveFstDiag(b) ++ getBelowFstDiag(b) ++ getAboveSndDiag(b) ++ getBelowSndDiag(b)


  def sequences(p: Player)(b: Board): Map[Int, Int] =
    boardLines(b).foldLeft(Map[Int, Int]())((acc, line) => {
      line.sliding(5).foldLeft(acc)((acc, seq) => {
        val free = seq.count(_ == Empty)
        val k = seq.count(_ == p)
        if (k + free == 5) {
          acc + (k -> (acc.getOrElse(k, 0) + 1))
        } else {
          acc
        }
      })
    })

  def makeBoard(size: Int): Board = {
    List.fill(size)(List.fill(size)(Empty))
  }

  def scoreBoard(p: Player)(b: Board): Int = {
    // val weights = Map(0 -> 0, 1 -> 0, 2 -> 20, 3 -> 80, 4 -> 100, 5 -> 1000)
    val weights = Map(0 -> 0, 1 -> 0, 2 -> 10, 3 -> 100, 4 -> 2000, 5 -> 10000)
    sequences(p)(b).foldLeft(0)((acc, kv) => {
      kv match {
        case (k, v) => acc + weights(k) * v
      }
    })
  }

  def toReadable(b: Board): String = {
    def toChar(p: Player): String =
      p match {
        case One => Console.YELLOW + 'X' + Console.RESET
        case Two => Console.BLUE + '0' + Console.RESET
        case _ => "."
      }

    b.map(_.map(toChar).mkString).mkString(newline)
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
