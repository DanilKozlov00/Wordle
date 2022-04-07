package wordle;

public class Main {
    public static void main(String[] args) {
        Dictionary dictionary = new TxtDictionary("src/main/resources/dictionaries/dictionary.txt");
        GameRule gameRule = new WordleRule(dictionary);
        GameWordle gameWordle = new GameWordle(gameRule);
        Console console = new Console(gameWordle);

        console.startGame();
    }
}
