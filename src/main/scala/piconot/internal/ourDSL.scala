package piconot.internal

import java.io.File
import scalafx.application.JFXApp
import scala.language.postfixOps
import picolib.maze.Maze
import picolib.semantics._

object ourDSL extends App {
  def state(stateName: State)(moves: ShortRule*): Seq[Rule] = {
    moves.map(r => Rule(stateName, r.s, r.m, r.state))
  }
  
  def move(dir: MoveDirection): MoveDirection = dir
  
  def to(end: String): State = end
 
  def blocked(walls: MoveDirection*): Surroundings = {
    var northWall, eastWall, westWall, southWall: RelativeDescription = Anything
    walls.foreach{ dir =>
      dir match {
        case North => northWall = Blocked
        case East => eastWall = Blocked
        case West => westWall = Blocked
        case South => southWall = Blocked
      }
    }
    Surroundings(northWall, eastWall, westWall, southWall)
  }
  
  def open(walls: MoveDirection*): Surroundings = {  
    var northWall, eastWall, westWall, southWall: RelativeDescription = Anything
    walls.foreach{ dir =>
      dir match {
        case North => northWall = Open
        case East => eastWall = Open
        case West => westWall = Open
        case South => southWall = Open
      }
    }
    Surroundings(northWall, eastWall, westWall, southWall)
  }
  
  implicit def tupletoShortRule(tuple: (Surroundings, MoveDirection, State)): ShortRule = {
    ShortRule(s = tuple._1, m = tuple._2, state = tuple._3)
  }
  implicit def stringToState(state: String): State = State(state)
  
  val left, west, w = West
  val up, north, n = North
  val right, east, e = East
  val down, south, s = South
  
  // Testing purposes
  val defaultSurrounding: Surroundings = Surroundings(Anything, Anything, Anything, Anything)
  val defaultMove: MoveDirection = StayHere
  val defaultState: State = State("Default")
  val defaultRule: Rule = Rule(
    defaultState,
    defaultSurrounding,
    defaultMove,
    defaultState)
  
  def testRules(rules: Seq[Rule]){
    println(rules.length)
  }
  
  val test1 = state("start")(
        (blocked (East, West) , move (left), to ("corner")),
        (open (North, West), move (left), to ("corner"))
  )
  testRules(test1)
}

case class ShortRule(s: Surroundings, m: MoveDirection, state: State)