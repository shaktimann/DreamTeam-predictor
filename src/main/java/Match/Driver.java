package Match;

import java.io.IOException;

import org.apache.commons.cli.ParseException;

public class Driver {
    
    public static void main(String[] args) throws IOException, ParseException {       
        Game game = new Game(new ArgumentParser());
        game.parseArguments(args);
        game.playGame();
    }
}
