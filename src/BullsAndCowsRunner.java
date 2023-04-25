import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class BullsAndCowsRunner {
    public static final int MAX_ROUNDS = 7;
    public static final int CODE_LENGTH = 4;
    private final StringBuilder gameHistory;
    private ComputerAI computerAI;
    private final Keyboard keyboard;
    public static void main(String[] args) {
        BullsAndCowsRunner runner = new BullsAndCowsRunner();
        runner.playGame();
    }
    // Constructor
    public BullsAndCowsRunner() {
        this.keyboard = new Keyboard();
        this.gameHistory = new StringBuilder();
        this.computerAI = new ComputerAI("easy");
    }
    // Get user's secret code
    private String getUserSecretCode() {return keyboard.getUserSecretCode();}
    // Calculate bulls and cows for given secret code and guess
    public static int[] getBullsAndCows(String secretCode, String guess) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            } else if (secretCode.contains(Character.toString(guess.charAt(i)))) {
                cows++;
            }
        }
        return new int[]{bulls, cows};
    }
    // Get user's guess
    private String getUserGuess() {return keyboard.getUserGuess();}
    // Generate secret code for computer
    private String generateSecretCode() {
        return computerAI.generateSecretCode();
    }
    // Get computer's guess based on user's secret code
    private String getComputerGuess(String userSecretCode) {return computerAI.getComputerGuess(userSecretCode);}
    // Get user's guess source (manual or file)
    private String getUserGuessSource() {return keyboard.getUserGuessSource();}
    // Read file and return list of guesses
    private List<String> readFile(String fileName) {return keyboard.readFile(fileName);}
    // Get file name for guesses
    private String getFileName() {
        return keyboard.getFileName();
    }
    // Save game history to a text file
    private void saveGameHistory() {
        System.out.println("Do you want to save the game result to a text file? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String saveToFile = scanner.next().toLowerCase();
        if (saveToFile.equals("yes")) {
            System.out.println("Enter the file name:");
            String fileName = scanner.next();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(gameHistory.toString());
                System.out.println("Game result saved to " + fileName);
            } catch (IOException e) {
                System.out.println("Error saving game result: " + e.getMessage());
            }
        }
    }
    // Play the game
    public void playGame() {
        System.out.println("Welcome to the Bulls and Cows game!");
        String aiDifficulty = keyboard.getAiDifficulty();
        this.computerAI = new ComputerAI(aiDifficulty);
        String userSecretCode = getUserSecretCode();
        String computerSecretCode = generateSecretCode();
        System.out.println("You and the computer have set secret codes. You have " + MAX_ROUNDS + " rounds to guess each other's codes.");

        gameHistory.append("Bulls & Cows game result：\n");

        this.computerAI = new ComputerAI(aiDifficulty);

        int round = 1;
        boolean userWon = false;
        boolean computerWon = false;

        String userGuessSource = getUserGuessSource();
        List<String> fileGuesses = new ArrayList<>();
        int fileGuessIndex = 0;
        if (userGuessSource.equals("file")) {
            String fileName = getFileName();
            fileGuesses = readFile(fileName);
        }
        while (round <= MAX_ROUNDS && !(userWon || computerWon)) {
            System.out.println("Round " + round);
            gameHistory.append("Round ").append(round).append("\n");

            String userGuess;
            if (userGuessSource.equals("manual") || fileGuessIndex >= fileGuesses.size()) {
                userGuess = getUserGuess();
            } else {
                userGuess = fileGuesses.get(fileGuessIndex++);
                System.out.println("Using guess from file: " + userGuess);
                gameHistory.append("Using guess from file: " ).append(round).append("\n");
            }

            int[] userBullsAndCows = getBullsAndCows(computerSecretCode, userGuess);
            System.out.println("Your guess: " + userGuess + " | Bulls: " + userBullsAndCows[0] + " | Cows: " + userBullsAndCows[1]);
            gameHistory.append(String.format("Your guess: %s | Bulls: %d | Cows: %d\n", userGuess, userBullsAndCows[0], userBullsAndCows[1]));


            if (userBullsAndCows[0] == CODE_LENGTH) {
                userWon = true;
            } else {
                String computerGuess = getComputerGuess(userSecretCode);
                int[] computerBullsAndCows = getBullsAndCows(userSecretCode, computerGuess);
                System.out.println("Computer's guess: " + computerGuess + " | Bulls: " + computerBullsAndCows[0] + " | Cows: " + computerBullsAndCows[1]);
                gameHistory.append(String.format("Computer's guess: %s | Bulls: %d | Cows: %d\n", computerGuess, computerBullsAndCows[0], computerBullsAndCows[1]));


                if (computerBullsAndCows[0] == CODE_LENGTH) {
                    computerWon = true;
                }
            }

            round++;
        }

        if (userWon) {
            System.out.println("Congratulations! You guessed the computer's secret code!");
            gameHistory.append("Congratulations! You guessed the computer's secret code!\n");
            gameHistory.append(String.format("Your secret code was: %s | The computer's secret code was: %s\n", userSecretCode, computerSecretCode));
        } else if (computerWon) {
            System.out.println("The computer guessed your secret code! Better luck next time!");
            gameHistory.append("The computer guessed your secret code! Better luck next time!\n");
            gameHistory.append(String.format("Your secret code was: %s | The computer's secret code was: %s\n", userSecretCode, computerSecretCode));
        } else {
            System.out.println("Neither you nor the computer guessed the secret code in " + MAX_ROUNDS + " rounds. ");
            System.out.println("Your secret code was: " + userSecretCode + " | The computer's secret code was: " + computerSecretCode);
            gameHistory.append("Neither you nor the computer guessed the secret code in " + MAX_ROUNDS + " rounds.\n");
            gameHistory.append(String.format("Your secret code was: %s | The computer's secret code was: %s\n", userSecretCode, computerSecretCode));
        }

        saveGameHistory();
    }
}