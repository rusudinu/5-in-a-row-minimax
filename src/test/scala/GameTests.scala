import BoardUtils.{complement, getAboveFstDiag, getAboveSndDiag, getBelowFstDiag, getBelowSndDiag, getColumns, getFstDiag, getSndDiag, isFree, makeBoard, next, show, update, winner}

class GameTests extends munit.FunSuite {

  val small =
    """0.X
      |0X.
      |X..""".stripMargin.replace("\r\n", "\n")

  val medium1 =
    """00000
      |0000X
      |000..
      |00.0.
      |0X..0""".stripMargin.replace("\r\n", "\n")

  val aboveFstDiag1 =
    """00..
      |00.
      |0X
      |0""".stripMargin.replace("\r\n", "\n")

  val aboveSndDiag1 =
    """0000
      |000
      |00
      |0""".stripMargin.replace("\r\n", "\n")

  val belowSndDiag1 =
    """X..X
      |.0.
      |..
      |0""".stripMargin.replace("\r\n", "\n")

  val medium2 =
    """0X0X0.
      |000.X0
      |0.0X..
      |0..0..
      |0X..0X
      |...X..""".stripMargin.replace("\r\n", "\n")

  val aboveFstDiag2 =
    """X0X.X
      |0...
      |XX.
      |00
      |.""".stripMargin.replace("\r\n", "\n")

  val belowFstDiag2 =
    """0....
      |0..X
      |0X.
      |0.
      |.""".stripMargin.replace("\r\n", "\n")

  val aboveSndDiag2 =
    """0.0.0
      |X0.0
      |000
      |X0
      |0""".stripMargin.replace("\r\n", "\n")

  val belowSndDiag2 =
    """0.0..
      |....
      |.0X
      |X.
      |.""".stripMargin.replace("\r\n", "\n")

  val winnerTest =
    """.X0.XX
      |000X0X
      |XXXX0.
      |.000X.
      |..0..X
      |.00.X.""".stripMargin.replace("\r\n", "\n")

  val winnerTest2 =
    """.X0.XX
      |000X0X
      |XX0X0.
      |.000X.
      |..0..X
      |.00.X.""".stripMargin.replace("\r\n", "\n")

  val winnerTest3 =
    """.X0.XX
      |000X0X
      |XX0X0.
      |.000X.
      |..0..X
      |.0X.X.""".stripMargin.replace("\r\n", "\n")

  test("isFree implementation") {
    assert(isFree(2, 2, makeBoard(small)))
    assert(!isFree(0, 0, makeBoard(small)))
  }

  test("Complement implementation") {
    assert(complement(One) == Two)
    assert(complement(Two) == One)
    assert(complement(Empty) == Empty)
  }

  test("Showing a small board") {

    assert(small == show(makeBoard(small)))
  }

  test("Showing a medium board") {
    assert(medium1 == show(makeBoard(medium1)))
  }

  test("Retrieving the list of columns") {
    assert(getColumns(makeBoard(medium1)) == makeBoard(medium1))
  }

  test("Retrieving the first diagonal") {
    assert(getFstDiag(makeBoard(medium1)) == List(Two, Two, Two, Two, Two))
  }

  test("Retrieving the second diagonal") {
    assert(getSndDiag(makeBoard(medium1)) == List(Two, Two, Two, Two, Two))
  }

  test("(A)Elements above fst diagonal 1") {
    assert(show(getAboveFstDiag(makeBoard(medium1))) == aboveFstDiag1)
  }

  test("(A)Elements above fst diagonal 2") {
    assert(show(getAboveFstDiag(makeBoard(medium2))) == aboveFstDiag2)
  }

  test("(B)Elements below fst diagonal 1") {
    assert(show(getBelowFstDiag(makeBoard(medium1))) == aboveFstDiag1)
  }

  test("(B)Elements below fst diagonal 2") {
    assert(show(getBelowFstDiag(makeBoard(medium2))) == belowFstDiag2)
  }

  test("(C)Elements above snd diagonal 1") {
    //print(show(getAboveSndDiag(makeBoard(medium1))))
    assert(show(getAboveSndDiag(makeBoard(medium1))) == aboveSndDiag1)
  }

  test("(C)Elements above snd diagonal 2") {
    assert(show(getAboveSndDiag(makeBoard(medium2))) == aboveSndDiag2)
  }

  test("(D)Elements below snd diagonal 1") {
    //println(show(getBelowSndDiag(makeBoard(medium1))))
    assert(show(getBelowSndDiag(makeBoard(medium1))) == belowSndDiag1)
  }

  test("(D)Elements below snd diagonal 2") {
    assert(show(getBelowSndDiag(makeBoard(medium2))) == belowSndDiag2)
  }


  test("Winner 1") {
    assert(winner(Two)(makeBoard(medium1)))
    assert(!winner(One)(makeBoard(medium1)))
  }

  test("Winner 2") {
    assert(winner(Two)(makeBoard(medium2)))
    assert(!winner(One)(makeBoard(medium2)))
  }

  val smallUpd1 =
    """0XX
      |0X.
      |X..""".stripMargin.replace("\r\n", "\n")

  test("Update 1") {
    assert(show(update(One)(0, 1, makeBoard(small))) == smallUpd1)
  }

  val smallUpd2 =
    """0.X
      |0X.
      |X.0""".stripMargin.replace("\r\n", "\n")

  test("Update 2") {
    assert(show(update(Two)(2, 2, makeBoard(small))) == smallUpd2)
  }

  val full =
    """0XX
      |0XX
      |XX0""".stripMargin.replace("\r\n", "\n")

  test("Next 1") {
    assert(next(Two)(makeBoard(full)) == Nil)
    assert(next(One)(makeBoard(full)) == Nil)
  }

  val nextTest =
    """0..
      |0.X
      |.X.""".stripMargin.replace("\r\n", "\n")

  val nextTestR1 = Set("00.\n0.X\n.X.", "0.0\n0.X\n.X.", "0..\n00X\n.X.", "0..\n0.X\n0X.", "0..\n0.X\n.X0")
  val nextTestR2 = Set("0X.\n0.X\n.X.", "0.X\n0.X\n.X.", "0..\n0XX\n.X.", "0..\n0.X\nXX.", "0..\n0.X\n.XX")

  test("Next 2") {
    assert(next(Two)(makeBoard(nextTest)).map(show).toSet == nextTestR1)
    assert(next(One)(makeBoard(nextTest)).map(show).toSet == nextTestR2)
  }

  test("Winner test") {
    assert(!winner(Two)(makeBoard(winnerTest)))
    assert(!winner(One)(makeBoard(winnerTest)))
  }

  test("Winner test2") {
    assert(winner(Two)(makeBoard(winnerTest2)))
    assert(!winner(One)(makeBoard(winnerTest2)))
  }

  test("Winner test3") {
    assert(winner(Two)(makeBoard(winnerTest3)))
    assert(!winner(One)(makeBoard(winnerTest3)))
  }
}
