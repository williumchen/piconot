package piconot.external
import scala.util.parsing.combinator._
import picolib.semantics._


object Parser extends JavaTokenParsers with PackratParsers {
  // parsing interface
  def apply(s: String): ParseResult[RuleExp] = parseAll(ruleExp, s)

  lazy val ruleExp: PackratParser[RuleExp] =
    (surAtom ^^ { case sur1 => test(sur1) })

  lazy val surAtom: PackratParser[RuleExp] =
    (sur)
    
    def sur: Parser[OurSur] =  "".r ^^ {OurSur(Anything, Anything, Anything, Anything)} 
}
    
//    
//    // expressions
//    lazy val expr: PackratParser[Expr] = 
//      (   expr~"+"~term ^^ {case l~"+"~r ⇒ Plus(l, r)}
//        | expr~"-"~term ^^ {case l~"-"~r => Minus(l, r)}
//        | term )
//    
//    // term
//    lazy val term: PackratParser[Expr] = 
//      (   expr~"*"~fact ^^ {case l~"*"~r => Mult(l, r)}
//      | expr~"/"~fact ^^ {case l~"/"~r => Div(l,r)}
//      | fact)
//        
//    // factors
//    lazy val fact: PackratParser[Expr] =
////      number | "("~>expr<~")"
//    number | "("~expr~")" ^^ {case "("~expr~")" => Paren(expr)}
//      
//    // numbers
//    def number: Parser[Num] = wholeNumber ^^ {s ⇒ Num(s.toInt)}
// }