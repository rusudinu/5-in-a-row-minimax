import scala.language.experimental.macros
// import scala.reflect.macros.blackbox

object Constants {
  val welcomeMessage = "Welcome to the 5 in a row game! To stop the game, you can type exit, quit, q or e.\nTo quick-start the game, you can type the size of the board, whether you want to use natural coordinates (y/n)\nand who do you want to start (1 - you, 2 - computer), separated by spaces. (e.g 5 y 2).\nIf you want to start and use natural coords use 5 y 1, if you want the computer to start, use 5 y 2 (you can replace 5 with the desired board size).\nIf not, just follow the prompts."
  val boardSizePrompt = "Please enter the board size: "
  val boardSizeError = "The board size must be at least 5."
  val movePrompt = "Please enter your move: "
  val outOfBoundsError = "The position that you entered is out of bounds. Please enter another one."
  val notFreeError = "The position that you entered is not free. Please enter another one."
  val playerOneWon = "Player One won!"
  val playerTwoWon = "Player Two won!"
  val draw = "It's a draw or I wrote bad code!"
  val maxBoardSize = 15
  val newline = "\n"
  val choseStartingPlayer = "Who starts? (1 - you, 2 - computer): "
  val invalidInput = "Your input is not valid. It should be in the form of X Y, where X is the X index and Y is the Y index, with a space between."
  val naturalCoordinatesPrompt = "Do you want to use natural coordinates for the values that you will enter? (the first index of X and Y will be 1 instead of 0) y/n "
  val stoppedEarly = "You stopped the game early."

  // tried to define a macro that will build all the functions that print the text above
  /*
  def show(c: blackbox.Context)(text: c.Expr[String]): c.Expr[Unit] = {
    import c.universe._
    val Literal(Constant(textStr: String)) = text.tree
    val name = TermName("show" + textStr.split(" ").map(_.capitalize).mkString(""))
    c.Expr[Unit](q"""def $name: Unit = println($textStr)""")
  }

  def showImpl(text: String): Unit = macro show
   */
}
