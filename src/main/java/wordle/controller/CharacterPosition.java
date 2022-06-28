package wordle.controller;

/**
 * Перечисление возможных состояний буквы в слове
 */
public enum CharacterPosition {
    CORRECT_POSITION,
    INCORRECT_POSITION,
    MISSING_IN_WORD
}
