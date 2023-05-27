import BoardUtils.{Board, complement, next, scoreBoard, winner}
import Trace.time

object AI {

  private def scoreBoardWrapper(p: Player)(b: Board)(maximizing: Boolean): Int = time("score-board") {
    if (winner(p)(b)) {
      Int.MaxValue
    } else if (winner(complement(p))(b)) {
      Int.MinValue
    } else {
      (if (maximizing) 1 else -1) * (scoreBoard(p)(b) - scoreBoard(complement(p))(b))
    }
  }

  private def minimax(p: Player)(b: Board, depth: Int, maximizing: Boolean, alpha: Int, beta: Int): Int = time("minimax") {
    if (depth == 0 || winner(p)(b) || alpha >= beta) {
      scoreBoardWrapper(p)(b)(maximizing)
    } else {
      nextWrapper(p)(b)(maximizing).foldLeft(alpha, beta, if (maximizing) Int.MinValue else Int.MaxValue)(
        (acc, child) => {
          val (alpha, beta, score) = acc
          val childScore = minimax(complement(p))(child, depth - 1, !maximizing, alpha, beta)
          if (maximizing) {
            (Math.max(childScore, alpha), beta, Math.max(childScore, score))
          } else {
            (alpha, Math.min(childScore, beta), Math.min(childScore, score))
          }
        }
      )._3
    }
  }

  private def depth(b: Board): Int = time("depth") {
    //Math.min(playedMoves(b) / 2 + 1, 4)
    2
  }

  private def nextWrapper(p: Player)(b: Board)(maximizing: Boolean): List[Board] = time("next-wrapper") {
    // TODO improve this
    // .sortBy(scoreBoardWrapper(p)(_)(maximizing)).take(5)
    next(p)(b).sortBy(scoreBoard(p)(_))
  }

  def predictNextBestMove(p: Player)(b: Board): Board = time("predict-next-best-move") {
    nextWrapper(p)(b)(maximizing = false).maxBy(brd => minimax(complement(p))(brd, depth(brd), maximizing = false, Int.MinValue, Int.MaxValue))
  }

}
