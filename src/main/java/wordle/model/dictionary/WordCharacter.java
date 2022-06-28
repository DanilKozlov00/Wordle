package wordle.model.dictionary;

import wordle.controller.CharacterPosition;

public class WordCharacter {

    private final Character character;
    private final CharacterPosition characterPosition;

    public WordCharacter(Character character, CharacterPosition characterPosition) {
        this.character = character;
        this.characterPosition = characterPosition;
    }

    public Character getCharacter() {
        return character;
    }

    public CharacterPosition getCharacterPosition() {
        return characterPosition;
    }
}
