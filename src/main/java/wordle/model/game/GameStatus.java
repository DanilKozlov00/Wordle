package wordle.model.game;

public enum GameStatus {
    IN_GAME("In game"),
    GAME_LOSE("Game is lose"),
    GAME_WIN("Game is win");

    private final String status;

    GameStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
