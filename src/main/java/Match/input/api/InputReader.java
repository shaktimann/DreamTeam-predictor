package Match.input.api;

import java.util.List;

import Match.Player;

public interface InputReader {

    /*public List<Player> players;

    public InputReader() {
        this.players = new ArrayList<Player>();
    }
    
    public List<Player> getPlayers() {
        return players;
    }*/

    public List<Player> readPlayersDataPreMatch(String filename);
    
    
    public List<Player> readPlayersDataPostMatch(String filename);
}
