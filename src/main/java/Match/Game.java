package Match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.ParseException;
import org.apache.poi.EncryptedDocumentException;

import Match.constants.LimitConstants;
import Match.input.api.InputReader;
import Match.input.api.InputReaderFactory;
import Match.input.impl.ExcelInputReader;
import Match.output.OutputWriter;
import Match.util.SortUtil;

public class Game {

    List<List<Player>> finallist;
    List<Player> allPlayers;
    ArgumentParser ap;

    public Game() {
        this.finallist = new ArrayList<List<Player>>();
        this.allPlayers = new ArrayList<Player>();
    }

    public Game(ArgumentParser ap) {
        this();
        this.ap = ap;
    }

    public void parseArguments(String[] args) throws ParseException {
        ap.parseArguments(ap.options(), args);

    }

    public void playGame() throws EncryptedDocumentException, IOException {
        InputReader ir = InputReaderFactory.getInputReaderFactoryInstance().getInputReader(ap.getInputFile());
        outputTopTeamCombinations(ir.readPlayersDataPreMatch(ap.getInputFile()));
    }

    public void findAllValidCombinations(List<Player> allPlayers, List<Player> teamSoFar, double creditsSoFar,
            int currentIndex) {

        int sizeSoFar = teamSoFar.size();
        if (sizeSoFar == LimitConstants.NO_OF_PLAYERS_IN_TEAM && creditsSoFar <= LimitConstants.MAX_CREDITS) {
            if (!checkConditions(teamSoFar))
                return;
            List<Player> oneset = new ArrayList<Player>();
            oneset.addAll(teamSoFar);
            finallist.add(oneset);
            return;
        }

        if (currentIndex == allPlayers.size())
            return;

        if (creditsSoFar > LimitConstants.MAX_CREDITS)
            return;

        if (sizeSoFar < LimitConstants.NO_OF_PLAYERS_IN_TEAM && creditsSoFar < LimitConstants.MAX_CREDITS) {
            if (creditsSoFar + allPlayers.get(currentIndex).getCredits() > LimitConstants.MAX_CREDITS)
                return;
            teamSoFar.add(allPlayers.get(currentIndex));
            findAllValidCombinations(allPlayers, teamSoFar, creditsSoFar + allPlayers.get(currentIndex).getCredits(),
                    currentIndex + 1);
            teamSoFar.remove(teamSoFar.size() - 1);
            creditsSoFar -= allPlayers.get(currentIndex).getCredits();
            findAllValidCombinations(allPlayers, teamSoFar, creditsSoFar + allPlayers.get(currentIndex).getCredits(),
                    currentIndex + 1);
        }
    }

    public boolean checkConditions(List<Player> teamSoFar) {
        int wk = 0, bat = 0, ar = 0, bowl = 0;
        Map<String, Integer> playersTeamWise = new HashMap<String, Integer>();

        for (Player player : teamSoFar) {
            if (player.getCategory() == Category.BAT)
                bat++;
            else if (player.getCategory() == Category.BOWL)
                bowl++;
            else if (player.getCategory() == Category.AR)
                ar++;
            else
                wk++;

            playersTeamWise.putIfAbsent(player.getTeam().trim(), 0);
            playersTeamWise.put(player.getTeam().trim(), playersTeamWise.get(player.getTeam().trim()) + 1);
        }
        if (wk >= LimitConstants.MIN_WK && wk <= LimitConstants.MAX_WK && bat >= LimitConstants.MIN_BAT
                && bat <= LimitConstants.MAX_BAT && bowl >= LimitConstants.MIN_BOWL && bowl <= LimitConstants.MAX_BOWL
                && ar >= LimitConstants.MIN_AR && ar <= LimitConstants.MAX_AR) {

            for (int teamPlayers : playersTeamWise.values()) {
                if (teamPlayers > LimitConstants.MAX_EACH_TEAM)
                    return false;
            }
            return true;
        }
        return false;

    }

    public void outputTopTeamCombinations(List<Player> allPlayers) throws IOException {
        findAllValidCombinations(allPlayers, new ArrayList<Player>(), 0, 0);
        SortUtil.sortTeamsByEstimatedScores(finallist);
        OutputWriter writer = new OutputWriter();
        writer.write(finallist, ap.getNoOfTeams(), ap.getOutputFile(), ap.getInputDreamTeam());
    }
}
