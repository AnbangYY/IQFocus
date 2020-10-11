TASK2: Create a method called ppValid(), this checks whether each brick is in 
the correct location. Parameter of this method would be the four  characters of each piece. 
The board with its length and width will be created as an object. It will return a boolean value
to determine the validity of the piece placement.

TASK3: Create a class PValid. In this class, the member variable will be a stringArray that indicates 
the charateristic of every piece on the board. First of all we need to determine the validity of every 
piece on board, using the ppValid()method created in task2. Then use a method called isPValid() to check if 
every piece on the board is placed in a valid position.

TASK4: Use javaFX to visualise a interface that can place pieces. Create a method 
called onBoard() to check whether tiles on the board.Create a method called alreadyOccupied()
to check whether the current destination cell is already occupied by another tile .
Create a method called snapToHome() to let the tile snap to home when it's not viable.
Create a method called rotate() to let the tile rotate and update the state of tile.


TASK5: First the placement of a piece needs to pass task 2,3(returning true values). Use countingChar to 
determine whether the length of a piece is correct. Also create a method isPieceOverlapped() to determine whether 
the pieces overlap with each other.

TASK6: This step determines the completion of challenge. First the above tasks must be passed, and everything
returns a true value from above. Then in this step we create a method that checks the challenge, isChallengeDone(),
this method takes the challenge value and the value of pieces in the middle of board as parameter. 
isChallengeDone() returns true when the pieces in the middle meet the criteria of challenge. 

TASK7: Use javaFX to show the board in a window, and the challenge at the left top corner of the window, and the
moveble pieces at bottom of the window.

TASK8: Unclear: where are the challenges, do we need to generate on our own? Since it is not feasible to generate
by using random program. We presume that we need to create challenge as an object.

TASK9: Just like Task6. But the col, row and placement string are NULL.

TASK10: According to the challenge, we can give the hints of pieces which can form the chanllenge.

TASK11: Integrate eveything above to make an AI which can fit all 10 pieces in the board correctly, and give the
value of the challenge of the completed board. This requires the AI to try many possibilities of the placement and 
each placement gives a true value when the placement value is put into isPValid() method. Then create a method
called giveChallenge() which takes the placement value as parameter and gives out the values of the challenge.