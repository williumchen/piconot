# Evaluation: running commentary

## Internal DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._


The first change from our ideal syntax to the syntax we implemented was to also make the end state of a rule a method that takes in an argument to be consistent with the other aspects of a rule. Because our DSL targetted those unfamiliar with computer science, it made sense to make the method-argument model consistent throughout. Another early change from our ideal syntax (although this is more on implementation details than syntactical appearance) was using ShortRule, our own defined class, rather than Rule. We reasoned that because we were abstracting out the start state from the rule, we could just use ShortRule, which only took in surroundings, moveDirection, and endState, and then construct a Rule from our state method by passing in the startState. We also realized that it was probably impossible to not pass the state name to a state method, so we made our state method take in a string that would be implicitly converted to a State type and a variatic sequences of Rules. The move and to methods were very straightforward to implement: the move method took in a moveDirection and returned that moveDirection and the to method took in an string (which was implicitly converted to a State), and returned the endState. The open and blocked open took in a variatic variable of moveDirections and used pattern-matching to determine the corresponding surroundings. The most difficult part was implementing the binary operator to combine open and blocked so that only one Surrounding would be returned. Here's a snippet of the comments we had for implementing the binary operator (to give some insight on how we approached it):

Notes on combining blocked + open (from Tues night):
 * can combine with "+" in the form "this.+(open(..), blocked(..))" without making our own wrapper class (simply unpack Surroundings and create new one),
 * However, to get proper binary operator (with the form, "open(..) + blocked(..)", we need to create our own class that mimics Surrounding because we'd need to define the operator inside it.
 * (^Unless I'm forgetting about something obvious)
 * Now our tuples might contain Surroundings or OurSurroundings (if users only pass in an 'open', for example, the '+' method will never be called, so the OurSurroundings will never get converted to a Surroundings).
 * So, we must cast both types of tuples to ShortRule.

As the comments above show, we needed to create a wrapper class OurSurroundings to get the binary operator working, which was something we also didn't expect when writing our ideal example.
We also realized that Scala requires several parantheses to pass in arguments, so it ends up looking like Lisp or Racket. Because the external DSL uses regular expressions, it was far easier to make get rid of these parantheses. 

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

I'd say 3. This is mostly because the DSL we had in mind is supposed to look a lot like a programming language, which seems significantly easier to emulate than implementing, for example, a Picobot language composed entirely of natural language. Most of the "changes" detailed above didn't affect the appearance of our syntax; rather, the "changes" were more focused around implementation details.

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

Probably 7. We felt our syntax mapped pretty well to the provided API in that it retains the same structure (a series of rules where each rule passes in the same parameters). However, abstracting out the start state from the Rule type required writing several additional wrapper functions, as detailed above. 

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

The most difficult part of implementing the external DSL was figuring out the proper syntax for the parser.  We initially spent a couple hours struggling to create our own tokens (similar to the wholeNumber built-in token from the JavaTokensParser).  After looking at the sample solution, however, we realized we could just use the RegExParser and not deal with tokens.

Once we had that figured out, implementing the parser was relatively straightforward.  We started from the bottom up, first implementing a parser for RelativeDescription, then for a Surrounding, then ShortRule (our internal representation of a Rule without the start state), etc.  Because we decided to group together states with the same start state (see example code), we had to write some methods that would take bundles of tuples (as an internal data structure) and output Rules, ShortRules, etc.  See ‘descDirSetToSurroundings’ and ‘stateShortsToRules’ from our Semantics.scala.

We didn’t have to make many changes from our initial design, since we were just parsing strings with RegEx.  One change we did make was adding parenthesis around the destination state.  We did this purposefully so that it would look like a function/method like our ‘open()’, ‘move()’, etc methods.

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**

Probably 1. We didn't really have to change a lot of our syntax as using the regex parser pretty much got rid of all the parantheses we had earlier.

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**

Probably 4. It was signficantly easier because we could use data structures that we created earlier for the internal DSL. This helped with the bundling structure we used for our DSL (a program consists of a series of states, which each consist of a series of rules, which consist of a series of surroundings, move directions, and end states). 
