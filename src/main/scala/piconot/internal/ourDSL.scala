package piconot.internal

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

object ourDSL extends App {
  def state(stateName: State)(moves: ShortRule*): Seq[Rule] = {
    moves.map(r => Rule(stateName, r.s, r.m, r.state))
  }

  def testRules(rules: Seq[Rule]){
    println(rules.length)
  }
  
  //def move(): MoveDirection = return Nil
   implicit def tupletoShortRule(tuple: (MoveDirection, Surroundings, State)): ShortRule = {
    ShortRule(m = tuple._1, s = tuple._2, state = tuple._3)
  }
  implicit def stringToState(state: String): State = State(state)

  // Testing purposes
  val defaultSurrounding: Surroundings = Surroundings(Anything, Anything, Anything, Anything)
  val defaultMove: MoveDirection = StayHere
  val defaultState: State = State("Default")
  val defaultRule: Rule = Rule(
    defaultState,
    defaultSurrounding,
    defaultMove,
    defaultState)
    
  val test1 = state("asdf")(
        (defaultMove, defaultSurrounding, defaultState),
        (defaultMove, defaultSurrounding, defaultState)
  )
  testRules(test1)
}

case class ShortRule(m: MoveDirection, s: Surroundings, state: State)