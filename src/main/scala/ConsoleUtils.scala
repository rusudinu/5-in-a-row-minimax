object ConsoleUtils {

  def makeConsoleGreen(): Unit = {
    print(Console.GREEN)
  }

  def makeConsoleRed(): Unit = {
    print(Console.RED)
  }

  def resetConsole(): Unit = {
    print(Console.RESET)
  }
}
