package wordle;

import java.util.Scanner;

import static wordle.WordleRule.*;

public class Console {

    private final GameWordle gameWordle;

    public Console(GameWordle gameWordle) {
        this.gameWordle = gameWordle;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        String stepResult = "", inputWord;
        System.out.println(gameWordle.getGameRules());
        do {
            while (!stepResult.equals(GAME_LOSE) && !stepResult.equals(GAME_WIN)) {
                System.out.println("Введите слово:");
                inputWord = scanner.nextLine();
                stepResult = gameWordle.checkWord(inputWord);
                gameWordle.printCharactersPositions(inputWord,gameWordle.getHiddenWord());
                System.out.println(stepResult);
                if (stepResult.equals(GAME_LOSE)) {
                    System.out.println("Hidden word:" + gameWordle.getHiddenWord());
                }
            }
            System.out.println("Print 'start' to restart game or 'exit' to exit");
            inputWord = scanner.nextLine();
            if (inputWord.equals("start")) {
                gameWordle.restartGame();
                stepResult = "";
            }
        } while (!inputWord.equals("exit"));
    }

}
