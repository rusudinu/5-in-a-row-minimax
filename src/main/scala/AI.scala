import BoardUtils.{Board, next}

object AI {
  def predictNextBestMove(p: Player)(b: Board): Board = {
    next(p)(b).head
  }
}
