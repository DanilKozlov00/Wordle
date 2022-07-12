package wordle.model.game;

import io.swagger.v3.oas.annotations.media.Schema;
import wordle.model.dictionary.WordCharacter;

import java.util.List;

@Schema
public class StepResult {
    private String checkWordStatus;
    private List<WordCharacter> wordCharacters;
    private String gameStatus;

    public StepResult() {
    }

    public StepResult(CheckWordStatus checkWordStatus, List<WordCharacter> wordCharacters, GameStatus gameStatus) {
        this.checkWordStatus = checkWordStatus.getStatus();
        this.wordCharacters = wordCharacters;
        this.gameStatus = gameStatus.getStatus();
    }

    public String getCheckWordStatus() {
        return checkWordStatus;
    }

    public void setCheckWordStatus(String checkWordStatus) {
        this.checkWordStatus = checkWordStatus;
    }

    public List<WordCharacter> getWordCharacters() {
        return wordCharacters;
    }

    public void setWordCharacters(List<WordCharacter> wordCharacters) {
        this.wordCharacters = wordCharacters;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}
