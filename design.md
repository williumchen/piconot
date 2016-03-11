# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

The target audience for this design is students who are taking CS 5.  Because Picobot is the first assignment given to students, we aren’t assuming prior knowledge of maze-solving algorithms or of CS fundamentals in general.

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

The main goals of our DSL design are as follows:
- Introduce new CS students to programming basics such as ‘if’ statements, functions that take parameters, and to be explicit with their code.
- Allow for easier organization of rules.  It makes sense to think of all the rules with the same start state as a single set of actions, so our design reflects this.
- Incorporate natural language to make the learning curve a bit lower.

## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

It can be easier to define very complex behaviors with many different states with our design.  This is because we encourage users to explicitly group together rules with the same start state and we also give users the ability to define states with strings (which can be easier to keep track of than numbers).  It’s also generally easier to read code written in our DSL because of its inclusion of natural language.

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

There aren’t any behaviors that are necessarily more difficult to express (in both our DSL and the original, users simply define a set of rules).
However, our design is definitely more wordy, so it may not be good for a user who’s already savvy with the Picobot domain.

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

7.  Our declarations of rules look pretty different because of our inclusion of natural language and function + parameter-style rule creation.  However, we don’t make any big changes to the logic/control flow of our syntax other than segregating rules with the same start state.

## Is there anything you would improve about your design?

We’d add more control statements such as `else’, `while’, etc.  These could create some interesting changes to how users organize rules and would also help to introduce CS 5 students to more control structures.
