import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Keyboard {
    public Scanner scanner;
    public static final int CODE_LENGTH = 4;
    public static final String ALLOWED_CHARACTERS = "0123456789ABCDEF";
    // Constructor
    public Keyboard() {
        this.scanner = new Scanner(System.in);
    }
    // Get AI difficulty from user input
    public String getAiDifficulty() {
        Scanner scanner = new Scanner(System.in);
        String aiDifficulty;
        System.out.println("Chose the difficulty（easy/medium/hard）:");
        do {
            aiDifficulty = scanner.nextLine().toLowerCase();
        } while (!isValidAiDifficulty(aiDifficulty));
        return aiDifficulty;
    }
    // Check if user input is a valid AI difficulty
    public boolean isValidAiDifficulty(String aiDifficulty) {
        if (!aiDifficulty.equals("easy") && !aiDifficulty.equals("medium") && !aiDifficulty.equals("hard")) {
            System.out.println("Invalid input. Please choose a valid difficulty level (easy/medium/hard):");
            return false;
        }
        return true;
    }
    // Get user's secret code input
    public String getUserSecretCode() {
        String secretCode;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Enter your secret code (" + CODE_LENGTH + " unique digits):");
            secretCode = scanner.nextLine();
        } while (!isValidSecretCode(secretCode));
        return secretCode;
    }
    // Check if user's secret code input is valid
    public boolean isValidSecretCode(String secretCode) {
        if (secretCode.length() != CODE_LENGTH) {
            System.out.println("Invalid secret code. The code should be " + CODE_LENGTH + " digits long.");
            return false;
        }

        if (!secretCode.matches("[0-9]+")) {
            System.out.println("Invalid secret code. The code should only contain digits.");
            return false;
        }

        Set<Character> uniqueChars = new HashSet<>();
        for (char c : secretCode.toCharArray()) {
            uniqueChars.add(c);
        }

        if (uniqueChars.size() != CODE_LENGTH) {
            System.out.println("Invalid secret code. The code should not have duplicate digits.");
            return false;
        }

        return true;
    }
    // Get user's guess input
    public String getUserGuess() {
        Scanner scanner = new Scanner(System.in);
        String guess;
        do {
            System.out.print("Enter your guess: ");
            guess = scanner.nextLine();
        } while (!isValidGuess(guess));
        return guess;
    }
    // Check if user's guess input is valid
    public boolean isValidGuess(String guess) {
        if (guess.length() != CODE_LENGTH) {
            System.out.println("Invalid guess. The guess should be " + CODE_LENGTH + " characters long.");
            return false;
        }

        if (!guess.matches("[" + ALLOWED_CHARACTERS + "]+")) {
            System.out.println("Invalid guess. The guess should only contain allowed characters.");
            return false;
        }

        Set<Character> uniqueChars = new HashSet<>();
        for (char c : guess.toCharArray()) {
            uniqueChars.add(c);
        }

        if (uniqueChars.size() != CODE_LENGTH) {
            System.out.println("Invalid guess. The guess should not have duplicate characters.");
            return false;
        }

        return true;
    }
    // Get user's guess source (manual or file)
    public String getUserGuessSource() {
        Scanner scanner = new Scanner(System.in);
        String inputSource;
        do {
            System.out.println("Enter your guess source (manual/file):");
            inputSource = scanner.nextLine().toLowerCase();
        } while (!inputSource.equals("manual") && !inputSource.equals("file"));
        return inputSource;
    }
    // Read the file and return a list of guesses
    public List<String> readFile(String fileName) {
        List<String> guesses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                guesses.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return guesses;
    }
    // Get the file name containing guesses from the user
    public String getFileName() {
        Scanner scanner = new Scanner(System.in);
        String fileName;
        do {
            System.out.println("Enter the name of the file containing guesses:");
            fileName = scanner.nextLine();
        } while (!isValidFile(fileName));
        return fileName;
    }
    // Check if the file is valid and accessible
    public boolean isValidFile(String fileName) {
        try (BufferedReader ignored = new BufferedReader(new FileReader(fileName))) {
            return true;
        } catch (IOException e) {
            System.err.println("Invalid file name. Please try again.");
            return false;
        }
    }
}