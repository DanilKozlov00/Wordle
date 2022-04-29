package wordle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wordle.view.WordleInterface;

public class Main {

    public static final String ENGLISH_TXT_DICTIONARY_PATH = "src/main/resources/dictionaries/dictionary.txt";

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringCongirutation.class);
        WordleInterface wordleInterface = ctx.getBean(WordleInterface.class);
        wordleInterface.startGame();
    }
}
