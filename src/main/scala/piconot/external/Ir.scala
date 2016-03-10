package piconot.external
import piconot.internal._
import picolib.semantics._

sealed abstract class Expr

case class Num(n: Int) extends Expr
case class Plus(left: Expr, right: Expr) extends Expr
case class MySurroundings(a: List[(RelativeDescription, MoveDirection)]) {

  //this implicit def wont work for some reason, so i just used the method "semantics.descDirSetToSurroundings" in the parser.
  //probably just doing something dumb here, but I tried a few diff things and none seemed to work..
  implicit def mySurrToSurroundings: Surroundings = {
    Surroundings(Anything, Anything, Anything, Anything)
  }
}
