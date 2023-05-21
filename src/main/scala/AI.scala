import BoardUtils.{Board, complement, next, scoreBoard, winner}
import Trace.time

object AI {

  trait MinimaxTree

  case class MinimaxNode(score: Int, board: Board, children: List[MinimaxTree]) extends MinimaxTree

  case class MinimaxLeaf(score: Int, board: Board) extends MinimaxTree

  def minimax(p: Player)(b: Board): MinimaxTree = time("minimax") {
    def minimaxHelper(p: Player)(b: Board, depth: Int): MinimaxTree = {
      if (winner(p)(b) || winner(complement(p))(b) || depth == 0) {
        MinimaxLeaf(scoreBoard(p)(b), b)
      } else {
        val children = next(p)(b).map(minimaxHelper(complement(p))(_, depth - 1))
        MinimaxNode(scoreBoard(p)(b), b, children)
      }
    }

    minimaxHelper(p)(b, 3)
  }

  def predictNextBestMove(p: Player)(b: Board): Board = time("predict-next-best-move") {
    val tree = minimax(p)(b)
    tree match {
      case MinimaxNode(_, _, children) =>
        val bestChild = children.maxBy {
          case MinimaxLeaf(score, _) => score
          case MinimaxNode(score, _, _) => score
        }
        bestChild match {
          case MinimaxLeaf(_, board) => board
          case MinimaxNode(_, board, _) => board
        }
      case MinimaxLeaf(_, board) => board
    }
  }
}
