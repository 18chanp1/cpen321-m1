# Something about my app
## Purpose of button 4
It is a game that allows you to play Tic-Tac-Toe by touching the squares on the screen.

The game is implemented in an activity named `TicTacToe`, with the layout in `activity_tic_tac_toe.xml`

The implementation was done by using a table layout to create a grid of 9 buttons. Each button is equipped with a touch handler that will update the square that is touched with the correct icon (e.g. X). It will also compute the next move by the computer, and update the icon correspondingly. The handler will not do anything in the case of a bad input (e.g. square already taken).

The image resource of the buttons are updated with icons from the standard android library, and colors updated accordingly. The color filter is set correspondingly to blue for the user's plays, and red for the computer's plays

The game state is controlled by a class `TicTacToeGame` which holds the state of the board, and has functions that can be called to input a move, or instruct the computer to make the next move. The class is also responsible for checking whether the game is terminated.

The computer selects moves randomly. After all, it would be no fun if you never won, since a well known algorithm for the best Tic-Tac-Toe game is already known.

A reset button is included to reset the game. This calls a reset function in `TicTacToeGame` and removes all the icons. 



## Generative AI
No generative AI technology was used in this assignment