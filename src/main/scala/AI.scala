import BoardUtils.{Board, complement, next, scoreBoard, winner}
import Trace.time

object AI {

  trait MinimaxTree

  case class Node(score: Int, board: Board, children: List[MinimaxTree]) extends MinimaxTree

  def minimax(p: Player)(b: Board, depth: Int, maximizing: Boolean): MinimaxTree = {
    if (depth == 0 || winner(p)(b)) {
      Node(scoreBoard(p)(b), b, Nil)
    } else {
      val children = next(p)(b).map(b => minimax(complement(p))(b, depth - 1, !maximizing))
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

  def predictNextBestMove(p: Player)(b: Board): Board = time("predict-next-best-move") {
    val tree = minimax(p)(b, 2, maximizing = true)
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
