object Trace {
  private var traces: Map[String, List[Long]] = Map()

  def time[R](traceName: String)(block: => R): R = {
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()
    traces.get(traceName) match {
      case Some(times) => traces += (traceName -> (times :+ (t1 - t0)))
      case None => traces += (traceName -> List(t1 - t0))
    }
    result
  }

  def printTraces(): Unit = {
    println(s"Traces: ${traces.size}")
    traces.foreach { case (traceName, times) =>
      println(s"$traceName: ${(times.sum / times.length) / 1000000}ms = ${(times.sum / times.length)} ns")
    }
  }
}
