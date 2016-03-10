package piconot.internal

import java.io.File
import scalafx.application.JFXApp
import scala.language.postfixOps
import picolib.maze.Maze
import picolib.semantics._

/**
 * Notes on combining blocked + open (from tues night):
 * can combine with "+" in the form "this.+(open(..), blocked(..))" without making our own wrapper class (simply unpack Surroundings and create new one),
 * However, to get proper binary operator (with the form, "open(..) + blocked(..)", we need to create our own class that mimics Surrounding because we'd need to define the operator inside it.
 * (^Unless I'm forgetting about something obvious)
 * Now our tuples might contain Surroundings or OurSurroundings (if users only pass in an 'open', for example, the '+' method will never be called, so the OurSurroundings will never get converted to a Surroundings).
 * So, we must cast both types of tuples to ShortRule.
 */

class ourDSL extends JFXApp {

  // Initializes the picobot and runs graphics display
  def picobot(mazeName: String)(rules: Seq[Rule]*) = {
    val rulesList = rules.flatten.toList
    val maze = Maze("resources" + File.separator + mazeName + ".txt")
    object EmptyBot extends Picobot(maze, rulesList)
      with TextDisplay with GUIDisplay
    stage = EmptyBot.mainStage
    EmptyBot.run()
  }

  // Allows users to group by state a series of rules
  def state(stateName: State)(moves: ShortRule*): Seq[Rule] = {
    moves.map(r => Rule(stateName, r.s, r.m, r.state))
  }

  // Natural language method to set move direction
  def move(dir: MoveDirection): MoveDirection = dir

  // Natural language method to set end state
  def to(end: String): State = end

  // Passes variatic MoveDirection and uses pattern matching to check using relative description
  // which direction is blocked
  def blocked(walls: MoveDirection*): OurSurroundings = {
    var northWall, eastWall, westWall, southWall: RelativeDescription = Anything
    walls.foreach { dir =>
      dir match {
        case North => northWall = Blocked
        case East  => eastWall = Blocked
        case West  => westWall = Blocked
        case South => southWall = Blocked
      }
    }
    OurSurroundings(northWall, eastWall, westWall, southWall)
  }

  // Similar to blocked, but checks if the relative description is open
  def open(walls: MoveDirection*): OurSurroundings = {
    var northWall, eastWall, westWall, southWall: RelativeDescription = Anything
    walls.foreach { dir =>
      dir match {
        case North => northWall = Open
        case East  => eastWall = Open
        case West  => westWall = Open
        case South => southWall = Open
      }
    }
    OurSurroundings(northWall, eastWall, westWall, southWall)
  }

  // Casts a tuple to ShortRule
  implicit def tupletoShortRule(tuple: (Surroundings, MoveDirection, State)): ShortRule = {
    ShortRule(s = tuple._1, m = tuple._2, state = tuple._3)
  }

  // ShortRule expects a Surroundings, so we explicitly construct one from OurSurroundings.
  // Making an alternate constructor for ShortRule that takes an OurSurroundings didn't work for some reason (even though I tried using the 'new' keyword).
  implicit def tupletoShortRule2(tuple: (OurSurroundings, MoveDirection, State)): ShortRule = {
    ShortRule(s = Surroundings(tuple._1.north, tuple._1.east, tuple._1.west, tuple._1.south), m = tuple._2, state = tuple._3)
  }

  // Implicit cast from string to State
  implicit def stringToState(state: String): State = State(state)

  // Default vals for natural language replacement of cardinal directions
  val left, west, w = West
  val up, north, n = North
  val right, east, e = East
  val down, south, s = South
  val stay, stop = StayHere

  // Testing purposes
  val defaultSurrounding: Surroundings = Surroundings(Anything, Anything, Anything, Anything)
  val defaultMove: MoveDirection = StayHere
  val defaultState: State = State("Default")
  val defaultRule: Rule = Rule(
    defaultState,
    defaultSurrounding,
    defaultMove,
    defaultState)

  //  def testRules(rules: Seq[Rule]) {
  //    println("number of rules: " + rules.length)
  //    println("first rule's surroundings: " + rules(0).surroundings)
  //  }
  //
  //  val test1 = state("start")(
  //    (blocked(East, West) & open(North), move(left), to("corner")),
  //    (open(North, West), move(left), to("corner")))
  //
  //  testRules(test1)
}

case class ShortRule(s: Surroundings, m: MoveDirection, state: State) {
  def this(s2: OurSurroundings, m2: MoveDirection, state2: State) {
    this(Surroundings(s2.north, s2.east, s2.west, s2.south), m2, state2)
  }
}

//By creating our own class that mimics Surroundings, we can have binary operators.  
case class OurSurroundings(north: RelativeDescription, east: RelativeDescription, west: RelativeDescription, south: RelativeDescription) {
  def +(other: OurSurroundings): Surroundings = {
    /*
     * @return whichever RelativeDescription is more specific (ie not Anything), or Anything if neither is specific.
     */
    def pickDir(d1: RelativeDescription, d2: RelativeDescription): RelativeDescription = {
      if (!d1.equals(Anything)) {
        println("d1")
        return d1
      } else if (!d2.equals(Anything)) {
        println("d2")
        return d2
      }
      Anything
    }

    Surroundings(pickDir(this.north, other.north), pickDir(this.east, other.east), pickDir(this.west, other.west), pickDir(this.south, other.south))
  }

  // Leverages + to allow for & binary operator
  def &(other: OurSurroundings): Surroundings = {
    this + other
  }
}