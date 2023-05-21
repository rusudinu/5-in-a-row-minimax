import BoardUtils.{Board, complement, display, next, scoreBoard, winner}
import Trace.time

object AI {

  def minimax(p: Player)(b: Board): Board = time("minimax") {
    def aux(shouldMaximize: Boolean)(b: Board): Int = {
      if (winner(p)(b)) 1
      else if (winner(complement(p))(b)) -1
      else if (next(p)(b).isEmpty) 0
      else {
        val scores = for (nextBoard <- next(p)(b)) yield aux(!shouldMaximize)(nextBoard)
        if (shouldMaximize) scores.max else scores.min
      }
    }

    next(p)(b).maxBy(aux(shouldMaximize = false))
  }

  // create a history map, with the board as key and the score as value

  var historyMap: Map[Board, Int] = Map()

  def minimaxWithHistoryMapAndMaxDepth(p: Player)(b: Board): Board = time("minimax-with-history") {
    def aux(shouldMaximize: Boolean, depth: Int)(b: Board): Int = {
      if (winner(p)(b)) 1
      else if (winner(complement(p))(b)) -1
      else if (next(p)(b).isEmpty) 0
      else if (depth == 0) scoreBoard(p)(b)
      else {
        val scores = for (nextBoard <- next(p)(b))
          yield {
            if (historyMap.contains(nextBoard)) historyMap(nextBoard)
            else {
              val score = aux(!shouldMaximize, depth - 1)(nextBoard)
              historyMap += (nextBoard -> score)
              score
            }
          }
        if (shouldMaximize) scores.max else scores.min
      }
    }

    next(p)(b).maxBy(aux(shouldMaximize = false, 8))
  }

  def minimaxWithDepth(p: Player)(b: Board): Board = time("minimax2") {
    def aux(shouldMaximize: Boolean, depth: Int)(b: Board): Int = {
      if (winner(p)(b)) 1
      else if (winner(complement(p))(b)) -1
      else if (next(p)(b).isEmpty) 0
      else if (depth == 0) scoreBoard(p)(b)
      else {
        val scores = for (nextBoard <- next(p)(b)) yield aux(!shouldMaximize, depth - 1)(nextBoard)
        if (shouldMaximize) scores.max else scores.min
      }
    }

    next(p)(b).maxBy(aux(shouldMaximize = false, 4))
  }

  // try and make minimax that keeps in mind the previous board scores such that they can be reused in the next call of minimax

  def predictNextBestMove(p: Player)(b: Board): Board = time("predict-next-best-move") {
    // minimaxWithDepth(p)(b) // 23866 ms average, 4 depth
    minimaxWithHistoryMapAndMaxDepth(p)(b) // 519 ms average, 4 depth | 15984 ms average, 8 depth
  }
}
