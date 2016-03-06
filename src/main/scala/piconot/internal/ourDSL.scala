package piconot.internal

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

object ourDSL extends App {

  state("0") {
    /**
     * N*** go West state 3
     *
     */
    List(
      ShortRule(
        Surroundings(Anything, Anything, Open, Anything),
        West,
        State("0")),
      ShortRule(
        Surroundings(Anything, Anything, Blocked, Anything),
        StayHere,
        State("1")))
  }

  val s = (Surroundings(Anything, Anything, Open, Anything),
    StayHere,
    State("0"))


  def state(state: => String)(function: => List[ShortRule]): List[Rule] = {

    for (i <- 0 to function.length) {

    }

    return Nil
  }

  def rule(state: String, short: ShortRule): Rule = {
    Rule(State(state), short.surr, short.action, short.state)
  }

}

case class ShortRule(surr: Surroundings, action: MoveDirection, state: State) {

  implicit def stuffToShortRule(surr: Surroundings, action: MoveDirection, state: State) = ShortRule(surr, action, state)

  def isItShort(): Boolean = {
    return true
  }
}