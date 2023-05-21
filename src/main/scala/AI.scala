import BoardUtils.{Board, display, next, scoreBoard}
import Trace.time

object AI {
  // using minimax algorithm
  def predictNextBestMove(p: Player)(b: Board): Board = time("predict-next-best-move") {
    val nextMoves = next(p)(b)
    val scores = nextMoves.map(scoreBoard(p))
    println(s"Scores: $scores")
    nextMoves.maxBy(scoreBoard(p))
  }
}
