import Constants.{boardSizePrompt, choseStartingPlayer, naturalCoordinatesPrompt, newline, welcomeMessage}

import scala.io.StdIn.readLine

object GameUtils {
  def init: (Int, Boolean, Boolean) = {
    print(welcomeMessage + newline + boardSizePrompt)
    val input = readLine()
    if (input.trim.contains(" ")) {
      val Array(boardSizeS, usingNaturalCoordinatesB, aiStartsB) = input.split(" ")
      (boardSizeS.toInt, usingNaturalCoordinatesB.toLowerCase == "y", aiStartsB.toInt == 2)
    } else {
      (input.toInt, initNaturalCoordinates, initStartingPlayer)
    }
  }

  private def initNaturalCoordinates: Boolean = {
    print(naturalCoordinatesPrompt)
    readLine().toLowerCase == "y"
  }

  private def initStartingPlayer: Boolean = {
    print(choseStartingPlayer)
    readLine().toInt == 2
  }
}
