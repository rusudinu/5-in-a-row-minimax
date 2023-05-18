import scala.io.StdIn.readLine

object GameUtils {
  def init: (Int, Boolean, Boolean) = {
    // todo add a quickstart method that will start the game with default values
    // or from a string, such as "5 y 2" (board size 5, natural coordinates, computer starts)
    val boardSize: Int = initBoardSize
    val usingNaturalCoordinates: Boolean = initNaturalCoordinates
    val aiStarts = initStartingPlayer
    (boardSize, usingNaturalCoordinates, aiStarts)
  }

  def initBoardSize(boardSize: String): Int = {
    boardSize.toInt
  }

  def initBoardSize: Int = {
    print(Constants.welcomeMessage + Constants.newline + Constants.boardSizePrompt)
    readLine().toInt
  }

  def initNaturalCoordinates: Boolean = {
    print(Constants.naturalCoordinatesPrompt)
    readLine().toLowerCase == "y"
  }

  def initStartingPlayer: Boolean = {
    print(Constants.choseStartingPlayer)
    readLine().toInt == 2
  }
}
