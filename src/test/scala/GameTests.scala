import BoardUtils.{makeBoard, next, sequences, update}
import Main._

import scala.reflect.runtime.universe.show

class GameTests extends munit.FunSuite {

  val small =
    """0.X
      |0X.
      |X..""".stripMargin.replace("\r\n", "\n")

  val medium1 =
    """00000
      |0000X
      |000..
      |00.0.
      |0X..0""".stripMargin.replace("\r\n", "\n")

  val aboveFstDiag1 =
    """00..
      |00.
      |0X
      |0""".stripMargin.replace("\r\n", "\n")

  val aboveSndDiag1 =
    """0000
      |000
      |00
      |0""".stripMargin.replace("\r\n", "\n")

  val belowSndDiag1 =
    """X..X
      |.0.
      |..
      |0""".stripMargin.replace("\r\n", "\n")

  val medium2 =
    """0X0X0.
      |000.X0
      |0.0X..
      |0..0..
      |0X..0X
      |...X..""".stripMargin.replace("\r\n", "\n")

  val aboveFstDiag2 =
    """X0X.X
      |0...
      |XX.
      |00
      |.""".stripMargin.replace("\r\n", "\n")

  val belowFstDiag2 =
    """0....
      |0..X
      |0X.
      |0.
      |.""".stripMargin.replace("\r\n", "\n")

  val aboveSndDiag2 =
    """0.0.0
      |X0.0
      |000
      |X0
      |0""".stripMargin.replace("\r\n", "\n")

  val belowSndDiag2 =
    """0.0..
      |....
      |.0X
      |X.
      |.""".stripMargin.replace("\r\n", "\n")


  test("Sequence") {
    val board = makeBoard(medium1)
    val seq = sequences(Two)(board)
  }

  test("Sequence 2") {
    val board = makeBoard(medium2)
    val seq = sequences(Two)(board)
  }
}
