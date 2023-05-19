import BoardUtils.{Board, next, scoreBoard}
import Trace.time

object AI {
  def predictNextBestMove(p: Player)(b: Board): Board = time("predictNextBestMove") {
    next(p)(b).maxBy(scoreBoard(p))
  }
}
