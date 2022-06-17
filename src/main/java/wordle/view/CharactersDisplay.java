package wordle.view;

import wordle.controller.CharacterPosition;

/**
 * Перечесления предоставляющее отображения возможных позиций буквы в слове
 */
public enum CharactersDisplay {

    CORRECT_POSITION_DISPLAY("Y"),
    INCORRECT_POSITION_DISPLAY("X"),
    MISSING_IN_WORD_DISPLAY("N");

    private static final String THE = "\nThe '";
    private static final String CHARACTER_PICKED = "' character indicates that you picked";
    private static final String INCORRECT_POS_CHAR_INFO = " the right letter but its in the wrong spot.";
    private static final String CORRECT_POS_CHAR_INFO = " the right letter in the correct spot.";
    private static final String MISSING_CHAR_INFO = " the letter is not included in the word at all.";

    private final String displayIndicator;

    CharactersDisplay(String displayIndicator) {
        this.displayIndicator = displayIndicator;
    }

    /**
     * Возращает символ, который будет отображать состояние переданной позиции
     *
     * @param characterPosition - позиция буквы в слове
     * @return - символ отображения буквы в слове
     */
    public static String getCharacterPositionDisplay(CharacterPosition characterPosition) {
        if (characterPosition.equals(CharacterPosition.CORRECT_POSITION)) {
            return CORRECT_POSITION_DISPLAY.displayIndicator;
        } else {
            if (characterPosition.equals(CharacterPosition.INCORRECT_POSITION)) {
                return INCORRECT_POSITION_DISPLAY.displayIndicator;
            } else return MISSING_IN_WORD_DISPLAY.displayIndicator;
        }
    }

    /**
     * Справка по отображению
     *
     * @return - возвращает справочную информацию об отображении символов
     */
    public static String getCharactersNotation() {
        return THE + CORRECT_POSITION_DISPLAY.displayIndicator + CHARACTER_PICKED + INCORRECT_POS_CHAR_INFO +
                THE + INCORRECT_POSITION_DISPLAY.displayIndicator + CHARACTER_PICKED + CORRECT_POS_CHAR_INFO +
                THE + MISSING_IN_WORD_DISPLAY.displayIndicator + CHARACTER_PICKED + MISSING_CHAR_INFO;
    }
}
