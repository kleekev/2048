=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: kleekev
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Array

  2. Collection

  3. IO

  4. JUnit Testing

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  The TwentyFortyEight Class has all the functions that deals with the board such
  as move. It includes all the functions that has to do anything with manipulating
  the board or game state.

  The GameBoard class has all the functions that affects how the game looks

  The RunTwentyFortyEight Class has all the functions that creates the buttons
  and the windows of the game

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  I realized that storing all the moves like left right up and down into a collection
  does not really do anything at all since after every move there is a random tile
  being added. This means that I cannot undo a random.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think my design is ok. It does work but I feel that it could be a little
  more efficient. For example, the function to find if there are no more moves
  is very long, I feel that there could be an easier way to do this.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
