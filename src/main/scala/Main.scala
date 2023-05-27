import BoardUtils.{makeBoard, winner}
import Game.start

object Main {
  def main(args: Array[String]): Unit = {
    //    val medium2 =
    //      """0X0X0.
    //        |000.X0
    //        |0.0X..
    //        |0..0..
    //        |0X..0X
    //        |...X..""".stripMargin.replace("\r\n", "\n")
    //
    //    println(BoardUtils.sequences(One)(BoardUtils.makeBoard(medium2)))

    //    val falseWinner =
    //      """.X0.XX
    //        |000X0X
    //        |XXXX0.
    //        |.000X.
    //        |..0..X
    //        |.00.X.""".stripMargin.replace("\r\n", "\n")
    //    println(winner(Two)(makeBoard(falseWinner)))
    //    println(winner(One)(makeBoard(falseWinner)))
    start()
  }
}

