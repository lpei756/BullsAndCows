import java.util.*;
public class ComputerAI {
    public static final int CODE_LENGTH = 4;
    public static final String ALLOWED_CHARACTERS = "0123456789ABCDEF";
    private final Set<String> previousGuesses;
    private List<String> possibleCodes;
    private final String aiDifficulty;
    // Constructor
    public ComputerAI(String aiDifficulty) {
        this.aiDifficulty = aiDifficulty;
        this.previousGuesses = new HashSet<>();
        generatePossibleCodes();
    }
    // Get computer's guess based on AI difficulty and user's secret code
    public String getComputerGuess(String userSecretCode) {
        switch (aiDifficulty) {
            case "easy":
                return generateRandomCode();
            case "medium":
                return mediumAI();
            case "hard":
                return hardAI(userSecretCode);
            default:
                throw new IllegalStateException("Invalid AI difficulty: " + aiDifficulty);
        }
    }
    // Medium AI generates a random code not guessed before
    public String mediumAI() {
        String guess;
        do {
            guess = generateRandomCode();
        } while (previousGuesses.contains(guess));
        previousGuesses.add(guess);
        return guess;
    }
    // Hard AI uses user's secret code to make an educated guess
    public String hardAI(String userSecretCode) {
        if (possibleCodes.isEmpty()) {
            generatePossibleCodes();
        }

        String guess = possibleCodes.get(new Random().nextInt(possibleCodes.size()));
        int[] currentGuessBullsAndCows = BullsAndCowsRunner.getBullsAndCows(userSecretCode, guess);
        int currentComputerBulls = currentGuessBullsAndCows[0];
        int currentComputerCows = currentGuessBullsAndCows[1];

        Iterator<String> iterator = possibleCodes.iterator();
        while (iterator.hasNext()) {
            String code = iterator.next();
            int[] currentBullsAndCows = BullsAndCowsRunner.getBullsAndCows(guess, code);

            if (currentBullsAndCows[0] != currentComputerBulls || currentBullsAndCows[1] != currentComputerCows) {
                iterator.remove();
            }
        }

        return guess;
    }
    // Generate secret code for computer
    public String generateSecretCode() {
        StringBuilder secretCode = new StringBuilder();
        Random random = new Random();
        while (secretCode.length() < CODE_LENGTH) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomCharacter = ALLOWED_CHARACTERS.charAt(randomIndex);
            if (secretCode.indexOf(Character.toString(randomCharacter)) == -1) {
                secretCode.append(randomCharacter);
            }
        }
        return secretCode.toString();
    }
    // Generate all possible codes
    public void generatePossibleCodes() {
        possibleCodes = new ArrayList<>();
        generatePossibleCodesRecursive(new StringBuilder(), 0);
    }
    // Generate all possible codes using recursion
    public void generatePossibleCodesRecursive(StringBuilder code, int index) {
        if (index == CODE_LENGTH) {
            possibleCodes.add(code.toString());
            return;
        }

        for (char c : ALLOWED_CHARACTERS.toCharArray()) {
            if (code.indexOf(Character.toString(c)) == -1) {
                code.append(c);
                generatePossibleCodesRecursive(code, index + 1);
                code.deleteCharAt(index);
            }
        }
    }
    // Generate a random code
    public String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = (int) (Math.random() * ALLOWED_CHARACTERS.length());
            code.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return code.toString();
    }
}