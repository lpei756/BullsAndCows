# Bulls and Cows

Bulls and Cows is a classic code-breaking game. Player and ComputerAI create secret codes, and they try to guess the code from each other. The game allows the user to choose the difficulty level of the ComputerAI (easy/medium/hard). The game uses a secret code consisting of 4 unique characters, where the allowed characters are "0123456789ABCDEF".

## How to play
1. Compile and run the game.
2. Choose the difficulty level for the computer AI: easy/medium/hard.
3. Enter your secret code consisting of 4 unique digits.
4. Choose your guess input source (manual or file).
    - If you choose "manual", enter your guess directly in the command line.
    - If you choose "file", enter the name of the file containing your guesses.
5. The game will continue until you guess the computer's secret code or the computer guesses your secret code.
6. Do you want to save the game result to a text file? (yes/no)
    - If you choose "yes", enter the file name to store. Game result will be saved to the file. Then Process finished with exit code 0.
    - If you choose "no", Process finished with exit code 0.

## Classes

1. **Keyboard**  
The Keyboard class handles user input through the command line. It includes methods for getting the AI difficulty level, user secret code, user guess, user guess source (manual or file), file name, and reading the file.

2. **ComputerAI**  
The ComputerAI class implements the computer AI logic for different difficulty levels (easy, medium, hard). The class contains methods for generating secret codes and guesses based on the chosen difficulty level. In the "hard" difficulty level, the AI uses an algorithm that eliminates impossible codes based on previous guesses.

## Usage

1. Compile all the classes:  
javac Player.java
javac Keyboard.java
javac ComputerAI.java

2. Run the game:  
java Main

3. Follow the on-screen instructions to play the game.

Enjoy playing Bulls and Cows!
