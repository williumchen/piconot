package piconot.external
import piconot.internal._
import picolib.semantics._

package object semantics {
  def eval(expr: Expr): Int = expr match {
    case Num(i)            ⇒ i
    case Plus(left, right) ⇒ eval(left) + eval(right)
  }

  //converts List of (RelativeDescription, MoveDirection) to a Surroundings
  def descDirSetToSurroundings(list: List[(RelativeDescription, MoveDirection)]): Surroundings = {
    var northWall, eastWall, westWall, southWall: RelativeDescription = Anything
    list.foreach { dir =>
      dir match {
        case (Open, North)    => northWall = Open
        case (Open, East)     => eastWall = Open
        case (Open, West)     => westWall = Open
        case (Open, South)    => southWall = Open
        case (Blocked, North) => northWall = Blocked
        case (Blocked, East)  => eastWall = Blocked
        case (Blocked, West)  => westWall = Blocked
        case (Blocked, South) => southWall = Blocked
      }
    }
    Surroundings(northWall, eastWall, westWall, southWall)
  }

  //converts a list of short rules to a list of rules, each with @param state as the initial state.
  def stateShortsToRules(state: State, shortRules: List[ShortRule]): List[Rule] = {
    shortRules.map(s => Rule(state, s.s, s.m, s.state))
  }
}