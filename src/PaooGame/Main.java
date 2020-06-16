package PaooGame;

import javax.sound.sampled.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) throws SQLException, IOException, UnsupportedAudioFileException {
        Game game = Game.getInstance("Hungry Little Bear", 800, 600);
        game.StartGame();
    }
}
/*
@startuml

class Main {
- refLink : RefLinks
}
@enduml
 */