import BoardUtils.{makeBoard, scoreBoard, sequences}
import Game.start

object Main {
  def main(args: Array[String]): Unit = {
    /*
    println(scoreLine(One)(List(One, One, One, One, One)))
    println(scoreLine(One)(List(One, One, One, Empty, Empty)))
    println(scoreLine(One)(List(One, Empty, One, One, One, One)))
     */
    val test =
      """00000
        |0000X
        |000..
        |00.0.
        |0X..0""".stripMargin.replace("\r\n", "\n")
    val test2 =
      """00X00
        |0000X
        |0X0..
        |00X0.
        |0X..0""".stripMargin.replace("\r\n", "\n")
    val test3 =
      """00X00
        |0000X
        |0X0..
        |00X0.
        |XX..X""".stripMargin.replace("\r\n", "\n")
    val test4 =
      """00X00
        |0000X
        |0X0..
        |00X0.
        |XXXX.""".stripMargin.replace("\r\n", "\n")
    val test5 =
      """00X00
        |0000X
        |0X0..
        |00X0.
        |XXX.X""".stripMargin.replace("\r\n", "\n")
    val test6 =
      """00X00
        |0000X
        |0X0..
        |00X0.
        |XX0.X""".stripMargin.replace("\r\n", "\n")
    println(scoreBoard(One)(makeBoard(test)))
    println(scoreBoard(One)(makeBoard(test2)))
    println(scoreBoard(One)(makeBoard(test3)))
    println(scoreBoard(One)(makeBoard(test4)))
    println(scoreBoard(One)(makeBoard(test5)))
    println(scoreBoard(One)(makeBoard(test6)))
    start()
  }
}

