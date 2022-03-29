package wordle;

import java.util.*;

public class Game {

    private enum Character_Position {
        CORRECT_POSITION,
        INCORRECT_POSITION,
        MISSING_IN_WORD
    }

    public static void main(String[] args) {
        Game game = new Game(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), "TOAST");
    }

   public static final String GAME_WIN = "wordle.Game is win";
   public static final String GAME_LOSE = "wordle.Game is lose";
   public static final String INCORRECT_WORD = "Incorrect word";
   public static final String INCORRECT_INPUT = "Incorrect input, try another word!";
   private static final int WORD_LENGTH = 5;
   private static final int MAX_STEPS = 6;

    private Integer stepsCount = 0;
    private String hiddenWord;
    private final List<String> allWords;

    public Game(Dictionary dictionary) {
        allWords = dictionary.readDictionary();
        hiddenWord = allWords.get(new Random().nextInt(allWords.size()));
    }

    public Game(Dictionary dictionary, String hiddenWord) {
        allWords = dictionary.readDictionary();
        this.hiddenWord = hiddenWord;
    }

    private boolean isCorrectWord(String inputWord) {
        if (inputWord.length() != WORD_LENGTH) {
            return false;
        }
        return allWords.contains(inputWord.toLowerCase());
    }

    private List<AbstractMap.SimpleEntry<Character, Character_Position>> getCharactersPosition(String inputWord) {
        List<AbstractMap.SimpleEntry<Character, Character_Position>> result = new LinkedList<>();
        for (int i = 0; i < inputWord.length(); i++) {
            char characterToCheck = inputWord.toLowerCase().charAt(i);
            if (hiddenWord.indexOf(characterToCheck) != -1) {
                if (hiddenWord.charAt(i) == characterToCheck) {
                    result.add(new AbstractMap.SimpleEntry<>(characterToCheck, Character_Position.CORRECT_POSITION));
                } else {
                    result.add(new AbstractMap.SimpleEntry<>(characterToCheck, Character_Position.INCORRECT_POSITION));
                }
            } else {
                result.add(new AbstractMap.SimpleEntry<>(characterToCheck, Character_Position.MISSING_IN_WORD));
            }
        }
        return result;
    }

    public String getStepResult(String inputWord) {
        if (stepsCount >= MAX_STEPS) {
            return GAME_LOSE;
        }

        if (isCorrectWord(inputWord)) {
            stepsCount++;
            List<AbstractMap.SimpleEntry<Character, Character_Position>> result = getCharactersPosition(inputWord);
            for (AbstractMap.SimpleEntry<Character, Character_Position> pair : result) {
                if (pair.getValue() != Character_Position.CORRECT_POSITION) {
                    return INCORRECT_WORD;
                }
            }
            return GAME_WIN;
        } else {
            return INCORRECT_INPUT;
        }
    }

    public void startNewGame(String hiddenWord) {
        stepsCount = 0;
        this.hiddenWord = hiddenWord;
    }
}
