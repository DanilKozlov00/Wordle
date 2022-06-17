package wordle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wordle.view.WordleInterface;

public class Main {
    
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        WordleInterface wordleInterface = ctx.getBean(WordleInterface.class);
        wordleInterface.startGame();
    }
}
