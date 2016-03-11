package piconot.external
import scala.util.parsing.combinator._
import picolib.semantics._
import piconot.internal._
import scala.tools.nsc.EvalLoop

object PiconotParser extends JavaTokenParsers with PackratParsers with App {

  def apply(s: String): ParseResult[List[Rule]] = parseAll(program, s)

  def program: PackratParser[List[Rule]] =
    (ruleSetSingle ~ program ^^ { case l ~ r => l ::: r }
      | ruleSetSingle)

  lazy val surr: PackratParser[List[Rule]] =
    ruleSetSingle

  def ruleSetSingle: PackratParser[List[Rule]] =
    "state(" ~ state ~ ")" ~ "(" ~ shortRules ~ ")" ^^ { case "state(" ~ st ~ ")" ~ "(" ~ sR ~ ")" => semantics.stateShortsToRules(st, sR) }

  def shortRules: Parser[List[ShortRule]] =
    (shortRuleSingle ~ "," ~ shortRules ^^ { case l ~ "," ~ r => l ::: r }
      | shortRuleSingle)

  // converts "blocked(..) & open(...), 
  def shortRuleSingle: Parser[List[ShortRule]] =
    ("if" ~ descDirSets ~ "," ~ "move(" ~ direction ~ ")" ~ "," ~ "to(" ~ state ~ ")" ^^ { case "if" ~ dirSets ~ "," ~ "move(" ~ dir ~ ")" ~ "," ~ "to(" ~ st ~ ")" => ShortRule(dirSets, dir, st) :: Nil }
      | "move(" ~ direction ~ ")" ~ "," ~ "to(" ~ state ~ ")" ^^ { case "move(" ~ dir ~ ")" ~ "," ~ "to(" ~ st ~ ")" => ShortRule(Surroundings(Anything, Anything, Anything, Anything), dir, st) :: Nil })

  def state: Parser[State] =
    """[a-zA-Z0-9]+""".r ^^ { s => State(s) }

  def direction: Parser[MoveDirection] =
    ("""North|north|up|Up""".r ^^ { s => North }
      | """East|east|right|Right""".r ^^ { s => East }
      | """West|west|left|Left""".r ^^ { s => West }
      | """South|south|down|Down""".r ^^ { s => South }
      | """stay|Stay|stop|Stop""".r ^^ { s => StayHere })

  // converts "blocked(West, x) & open(x)" => Surroundings
  def descDirSets: Parser[Surroundings] =
    (descDirSetSingle ~ "&" ~ descDirSetSingle ^^ { case l ~ "&" ~ r => semantics.descDirSetToSurroundings(l ::: r) }
      | descDirSetSingle ^^ { case n => semantics.descDirSetToSurroundings(n) })

  // converts "blocked(x, x)" OR "open(x, x)" => List of (RelativeDescription, MoveDirection)
  def descDirSetSingle: Parser[List[(RelativeDescription, MoveDirection)]] =
    ("blocked(" ~ blockedDirs ~ ")" ^^ { case """blocked(""" ~ n ~ """)""" ⇒ n }
      | "open(" ~ openDirs ~ ")" ^^ { case "open(" ~ n ~ ")" ⇒ n })

  //converts "North, West, etc" to List of (Blocked, MoveDirection)
  def blockedDirs: Parser[List[(RelativeDescription, MoveDirection)]] =
    (blockedDirSingle ~ """,""" ~ blockedDirs ^^ { case l ~ "," ~ r => l ::: r }
      | blockedDirSingle)

  //converts "North" OR "West", etc to List of (Blocked, MoveDirection)
  def blockedDirSingle: Parser[List[(RelativeDescription, MoveDirection)]] =
    ("""North|north|up|Up""".r ^^ { s => (Blocked, North) :: Nil }
      | """East|east|right|Right""".r ^^ { s => (Blocked, East) :: Nil }
      | """West|west|left|Left""".r ^^ { s => (Blocked, West) :: Nil }
      | """South|south|down|Down""".r ^^ { s => (Blocked, South) :: Nil })

  //converts "North, West, etc" to List of (Open, MoveDirection)
  def openDirs: Parser[List[(RelativeDescription, MoveDirection)]] =
    (openDirSingle ~ """,""" ~ openDirs ^^ { case l ~ "," ~ r => l ::: r }
      | openDirSingle)

  //converts "North" OR "West", etc to List of (Open, MoveDirection)
  def openDirSingle: Parser[List[(RelativeDescription, MoveDirection)]] =
    ("""North|north|up|Up""".r ^^ { s => (Open, North) :: Nil }
      | """East|east|right|Right""".r ^^ { s => (Open, East) :: Nil }
      | """West|west|left|Left""".r ^^ { s => (Open, West) :: Nil }
      | """South|south|down|Down""".r ^^ { s => (Open, South) :: Nil })

}