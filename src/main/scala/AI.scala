import BoardUtils.{Board, next}
import Trace.time

object AI {
  def predictNextBestMove(p: Player)(b: Board): Board = time("predictNextBestMove") {
    next(p)(b).head
  }
}
