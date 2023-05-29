import BoardUtils.{Board, complement, next, scoreBoard, winner}
import Constants.treeDepth
import scalaz.Memo

object AI {

  private def scoreBoardWrapper(p: Player)(b: Board)(maximizing: Boolean): Int =
    if (winner(p)(b)) {
      Int.MaxValue
    } else if (winner(complement(p))(b)) {
      Int.MinValue
    } else {
      (if (maximizing) 1 else -1) * (scoreBoard(p)(b) - scoreBoard(complement(p))(b))
    }

  private def minimax(p: Player)(b: Board, depth: Int, maximizing: Boolean, alpha: Int, beta: Int): Int =
    if (depth == 0 || winner(p)(b) || alpha >= beta) {
      memoizedScoreBoard(p, b, maximizing)
    } else {
      next(p)(b).foldLeft(alpha, beta, if (maximizing) Int.MinValue else Int.MaxValue)(
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

  private val memoizedScoreBoard = Memo.mutableHashMapMemo[(Player, Board, Boolean), Int] {
    case (p, b, maximizing) => scoreBoardWrapper(p)(b)(maximizing)
  }

  def predictNextBestMove(p: Player)(b: Board): Board =
    next(p)(b).maxBy(brd => minimax(complement(p))(brd, treeDepth, maximizing = false, Int.MinValue, Int.MaxValue))
}
