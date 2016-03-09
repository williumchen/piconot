[Picobot]: https://www.cs.hmc.edu/twiki/bin/view/CS5/PicobotProgrammingGold
[Teams]: https://github.com/hmc-cs111-spring2016/piconot/wiki/Team-sign-ups
[API]: http://www.cs.hmc.edu/courses/2016/spring/cs111/picolib/index.html#package
[JavaFX]: https://docs.oracle.com/javafx/2/installation/jfxpub-installation.htm
[emptyfile]: https://d1b10bmlvqabco.cloudfront.net/attach/ijjfckl6old4zz/hku6eqiwn313j8/il5k6nm8w0yk/empty.txt
[ParserCombinatorResource]: http://bitwalker.org/posts/2013-08-10-learn-by-example-scala-parser-combinators/
[ParserCombinatorAPI]: http://www.scala-lang.org/files/archive/api/2.11.7/scala-parser-combinators/#scala.util.parsing.combinator.package

# Piconot

## Overview

This assignment asks you to re-design the syntax of an existing language
(Picobot) and to implement your syntax as both an internal and external DSL.
The goals of this assignment are to: 
   - learn how to design a brand-new syntax
   - get some experience implementing a language the **wrong way**
   - get some experience implementing an external DSL (the _right_ way, we hope)

About implementing a language the wrong way:  You'll probably design a syntax
that is either difficult to implement internally and  perhaps requires
significant implementation effort on your part.  **This is good!** It's good to
have a chance to experience the limitations of internal DSLs, so you'll start to
develop an instinct for when they are and are not appropriate. (Of course, it's
also good to have a chance to experience how to implement a language the right
way, so you'll also implement your syntax as an external DSL.)

## Checklist
  - [x] [Sign up for teams][Teams]. You'll work in pairs for this assignment
  - [x] Design a new syntax for Picobot 
     - [ ] Describe your design in `design.md`
     - [ ] Before you implement the syntax, write the "empty room" program in
     `example-ideal.txt`
  - [x] Implement your new syntax as an internal DSL
     - [x] Add files, as needed, to implement your syntax
     - Include two example programs
       - [x] `src/main/scala/piconot/internal/Empty.scala`
       - [x] `src/main/scala/piconot/internal/RightHand.scala`
     - [ ] Describe your implementation process in `evaluation.md`
  - [ ] Implement your new syntax as an external DSL, using parser combinators
     - Include at least two example programs
        - [ ] `src/main/scala/piconot/external/Empty.bot`
        - [ ] `src/main/scala/piconot/external/RightHand.bot`
     - [ ] Provide instructions for how to run piconot in `build.md`
     - [ ] Describe your implementation process in `evaluation.md`
  - [ ] Critique another team's design and implementation

## Warm-up: Picobot
You should (re-)familiarize yourself with 
[Picobot, as it's defined in CS 5][Picobot]. Make sure you understand the 
"empty room" and "maze" Picobot programs (posted on [Piazza][emptyfile]).

## Syntax design

Design your own syntax for Picobot. Try to come up with a design that is
faithful to the domain (of maze-navigation), that does not add any new features
(e.g., non-determinism), but that is as different as possible from the current
syntax. The more novel your syntax, the better, assuming that it's faithful to
the domain. **Design your syntax as an ideal, i.e., don't worry at all about how
you'll implement it.**

Re-write the empty room program in your new syntax, in the file 
`example-ideal.txt`. 

Describe and justify your design, in the file `design.md` (see that file for
instructions on what to write). 

## Picobot semantics

I've provided a library that implements the semantics of Picobot (i.e., what
happens when a program runs). You'll need to transform statements from your
syntax into calls to the provided library.

You'll need to understand the interface for the Picobot library (but not its
implementation!). You should take a look at the file
`src/main/scala/piconot/EmptyAPI.scala` for an example use of the library. You
can also look at the library's [auto-generated documentation][API]. The code for
the library itself is in the `lib` directory. Thanks to sbt, you shouldn't need
to do anything special with the library to build and run your code.

## Building and running the code

You should be able to load your code into ScalaIDE in the usual way (i.e., by
executing `sbt eclipse` in the top-level directory). Then open the file
`src/main/scala/piconot.internal/EmptyAPI.scala` and run it.

**Warning:** This provided code has a graphical version of a running program.
The graphics rely on a Java library called JavaFX. If this library
isn't installed on your machine, then you'll get an error. To fix it, you might:
   - Match the version of ScalaFX to your Java version. 
   - Install [JavaFX] on your computer (preferred option, but don't spend a lot of
  time if it's not easy to install)
   -  Remove the graphics elements from the project and just use the text-based
   runner. To do so, from the main directory of the project, run
   `git checkout no-graphics`. Then compile and run as you normally would. 
   You're now working off a different branch of the assignment. I'm not quite 
   sure how pull requests will work, but we'll figure it out :).

You can also run the provided "internal" program on the command line, by typing
`sbt "run internal.EmptyAPI"` in the top-level directory. (Note, if you run the
program from the command line, you'll probably get a warning about a .css file,
and the graphics for the buttons will look a bit different. The program should
still work, though.) If you use sbt and the provided `build.sbt` file to build
your code, you should  automatically have access to the Picobot library, and it
will be included on your classpath when you compile and run.

## Syntax implementation: internal DSL

Place your code for the internal implementation in the `piconot.internal`
package. You can (and probably should) add new files to the implementation.

### A running diary

As you work, comment on your experience in the file `evaluation.md`. In
particular, if you change your ideal syntax, you should describe what
changed and why you made the change (e.g., your original idea was too hard to
implement or it didn't match well with the library calls) You should also answer
the following questions: On a scale of 1–10 (where 10 is "a lot"), how much did
you have to change your syntax? 

### Sample programs

Include at least two sample programs.

For the internal version, include the files:

  1. `src/main/scala/piconot/external/Empty.bot`: a program in your internal
  DSL that can solve the empty room. A file that describes an empty room is in 
  `resources/empty.txt`.
  
  2. `src/main/scala/piconot/external/RightHand.bot`: a program in your
  internal DSL that uses the right-hand rule to solve the maze in 
  `resources/maze.txt`.

## Syntax implementation: external DSL

**Note:** you'll probably want to wait to implement the external DSL until we
cover the necessary techniques in class.

Use [parser combinators][ParserCombinatorAPI] and case classes to implement the
parser. Here's a [good article][ParserCombinatorResource]  for learning a bit
more about parser combinators; there are many other ones on the web. Chapter 19
of _Scala for the Impatient_ also covers parser combinators. For more complex
syntaxes, the _Language Implementation Patterns_ book may also be useful.

A note about building the given code: use sbt! If you don't, you'll have to jump
through many difficult hoops to get your code to compile against the parser
combinator library.

When implementing your language, use the architecture from class:

```
src/main 
 |-- scala
      |-- piconot
        |-- external
             |-- parser
             |-- ir
             |-- semantics

src/test 
 |-- scala
      |-- piconot
        |-- external
             |-- parser
             |-- ir
             |-- semantics
```

**Note:** You'll have to create some these directories yourself -- they're not
all part of the initial repository.

**Note:** Your intermediate representation might be data structures from the
`picolib` library, or it might be portions of your internal DSL, or a
combination of both. In some cases, you might not need to create _all_ the 
packages described above.

## A running diary 

As you work, comment on your experience in the file `evaluation.md`. In
particular, each time you change your ideal syntax, you should describe what
changed and why you made the change (e.g., your original idea required an
operator that you couldn't implement in Scala or integrate with your other ides
). You should also answer the following questions: On a scale of 1–10 (where 10
is "a lot"), how much did you have to change your syntax? On a scale of 1–10
(where 10 is "very difficult"), how difficult was it to map your syntax to the
provided API?

### Sample programs

Include at least two sample programs.

For the internal version, include the files:

  1. `src/main/scala/piconot/external/Empty.bot`: a program in your internal
  DSL that
  can solve the empty room. A file that describes an empty room is in 
  `resources/empty.txt`.
  
  2. `src/main/scala/piconot/internal/RightHand.scala`: a program in your internal DSL 
  that uses the right-hand rule to solve the maze in `resources/maze.txt`.


### Provide instructions for building and running your external DSL

In the `build.md` file provide instructions for how to build your
external DSL and how to run it on a piconot program. A somewhat easy way to do
is the following:

```
sbt "run-main piconot.external.Piconot resources/empty.txt src/main/scala/piconot/external/Empty.bot"
```

Note that for your users to run your language this way, you'll have to design
your solution so that the `main` function takes and processes an argument that
contains the filename of the maze file and the filename of the picobot program.

Alternatively, you can build a stand-alone jar file, which users can execute:
  1. build the stand-alone .jar file by running `sbt assembly` 
  (and note the location of the jar file that sbt generates)
  2. run the software on a file by executing the command 

```
scala -cp path-to-jar-file name-of-class-with-main-function maze-file bot-file
```

**Make sure the build process works before your final submission!**


## Grading

A "three" is work that completes all the parts and which the syntax is different
from the original Picobot syntax in a way that is justified in `design.md`.

A "four" furthermore gracefully handles errors, in the external DSL. Errors
might include syntax errors, programs that reference undefined rules, etc.
Giving good error messages for parsers is notoriously difficult. The `failure`,
`positioned`, `phrase`, and `log` combinators (defined in the
[scala.util.parsing.combinators.Parsers trait](http://www.scala-lang.org/files/archive/api/2.11.7/scala-parser-combinators/#scala.util.parsing.combinator.Parsers))  
may be helpful.

## Peer-review another team's work

Comment on another team's design and implementation. You should look through
their grammars, pay special attention to their `design.md` and `evaluation.md`
files, their internal DSL and their parser. You might consider the following
questions:

  - What good insights about implementation did the team in `design.md`? Did
  you have any experiences that were similar to the team?
  - How nice is their implementation? Is it understandable? Clean? Modular?
  Extensible? What do you like about their code? Are there any particularly
  elegant ways they overcame implementation challenges?
  - Download their code and run their example programs. How easy was it to run? 
  Did the instructions work?
  - Modify the example programs to introduce errors. How robust is their
  implementation? What do you like about their error-handling? What can be improved?
  - Are there any implementation tricks that you can suggest to the team?
  Anything you see that might make the implementation easier or more like the
  original design?

## Advice

### Time
**Give yourself a time budget.** This project can easily eat up hours of time,
as you try to get your language _just right_. If you've got that kind of time --
go for it! But if your time is limited, a budget would be good. Here's a
suggestion, though feel free to alter it:

   + 30 minutes reviewing Picobot
   + 2 hours coming up with a new syntax design
   + 30 minutes writing the example(s) in your ideal syntax
   + 30 minutes skimming the provided example program and API
   + 3-4 hours implementing your internal design
   + 3-4 hours implementing your external design
   + 1 hour answering the questions in `design.md` and `evaluation.md`
   + 1 hour: critique / peer review

This is about 13-14 hours of work, spread over two weeks. **Start early!**
Note that this budget doesn't leave _that_ much time for implementing your
design. You might have to make hard choices and radically change your syntax to
get things working. That's okay! You can always come back to it when you have
more time.

### Implementation techniques

Look back at all our class materials and previous assignments to see tips
internal and external implementation techniques. If you get stuck trying to
implement something, ask for help!
