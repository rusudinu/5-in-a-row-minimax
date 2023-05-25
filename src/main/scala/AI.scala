import BoardUtils.{Board, complement, next, playedMoves, scoreBoard, winner}
import Trace.time

object AI {

  private trait MinimaxTree

  private case class Node(score: Int, board: Board, children: List[MinimaxTree]) extends MinimaxTree

  private def scoreBoardWrapper(p: Player)(b: Board): Int = time("score-board") {
    if (winner(p)(b)) {
      Int.MaxValue
    } else if (winner(complement(p))(b)) {
      Int.MinValue
    } else {
      scoreBoard(p)(b)
    }
  }

  private def minimax(p: Player)(b: Board, depth: Int, maximizing: Boolean): MinimaxTree = time("minimax") {
    if (depth == 0 || winner(p)(b)) {
      Node(scoreBoardWrapper(p)(b), b, Nil)
    } else {
      lazy val children = next(p)(b).map(b => minimax(complement(p))(b, depth - 1, !maximizing))
      if (maximizing) {
        val maxScore = children.map {
          case Node(score, _, _) => score
        }.max
        Node(maxScore, b, children)
      } else {
        val minScore = children.map {
          case Node(score, _, _) => score
        }.min
        Node(minScore, b, children)
      }
    }
  }

  private def depth(b: Board): Int = time("depth") {
    Math.min(playedMoves(b) / 2 + 1, 4)
  }


  def predictNextBestMove(p: Player)(b: Board): Board = time("predict-next-best-move") {
    val tree = minimax(p)(b, depth(b), maximizing = true)
    tree match {
      case Node(_, _, children) =>
        children.maxBy {
          case Node(score, _, _) => score
        } match {
          case Node(_, board, _) => board
        }
    }
  }

}
