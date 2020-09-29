package Match.input.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Match.Category;
import Match.Player;
import Match.constants.InputDataConstants;
import Match.input.api.InputReader;

public class CSVInputReader implements InputReader {

    public CSVInputReader() {
        super();
    }

    @Override
    public List<Player> readPlayersDataPreMatch(String filename) {
        String name, team;
        double credits;
        double estimatedScore;
        Category category;
        List<Player> players = new ArrayList<Player>();

        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] record = line.split(InputDataConstants.CSVSPLITBY);
                name = record[0];
                credits = Double.parseDouble(record[1]);
                category = Category.valueOf(record[2]);
                team = record[3];
                Player.Builder playerBuilder = new Player.Builder(name).setCredits(credits).setCategory(category)
                        .setTeam(team);
                if (record.length == 5) {
                    estimatedScore = Double.parseDouble(record[4]);
                    playerBuilder.setEstimatedScore(estimatedScore);
                }
                players.add(playerBuilder.build());
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public List<Player> readPlayersDataPostMatch(String filename) {
        // TODO : complete this method
        return null;
    }
}
