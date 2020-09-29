package Match.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Match.Player;
import Match.constants.InputDataConstants;
import Match.constants.LimitConstants;
import Match.input.api.InputReader;
import Match.input.api.InputReaderFactory;
import Match.input.impl.ExcelInputReader;
import Match.util.SortUtil;

public class OutputWriter {

    public void write(List<List<Player>> teams, int noOfTeams, String outputFile, String matchPlayersFile)
            throws IOException {

        int i, j, rowid = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFRow row;
        XSSFSheet sheet = null;
        boolean ifMatchPlayersFileIsPresent = false;

        if (matchPlayersFile != null && !matchPlayersFile.isEmpty()) {
            ifMatchPlayersFileIsPresent = true;
        }

        for (i = 0; i < noOfTeams; i++) {
            if (i % LimitConstants.SHEET_SIZE == 0) {
                sheet = workbook.createSheet("Teams " + (i + 1) + "-" + (i + LimitConstants.SHEET_SIZE));
                rowid = 0;
            }
            SortUtil.sortPlayersByPoints(teams.get(i));
            for (Player player : teams.get(i)) {

                row = sheet.createRow(rowid++);
                int cellid = 0;

                Cell cell = row.createCell(cellid++);
                cell.setCellValue(i + 1);

                cell = row.createCell(cellid++);
                cell.setCellValue(player.getName());

                cell = row.createCell(cellid++);
                cell.setCellValue(player.getTeam());

                cell = row.createCell(cellid++);
                cell.setCellValue(player.getCategory().name());

                cell = row.createCell(cellid++);
                cell.setCellValue(player.getCredits());

                cell = row.createCell(cellid++);

                if (teams.get(i).get(0).getName().equalsIgnoreCase(player.getName())) {
                    cell = row.createCell(cellid++);
                    cell.setCellValue("Captain");
                }

                if (teams.get(i).get(1).getName().equalsIgnoreCase(player.getName())) {
                    cell = row.createCell(cellid++);
                    cell.setCellValue("Vice Captain");
                }

                if (ifMatchPlayersFileIsPresent) {
                    InputReader ir = InputReaderFactory.getInputReaderFactoryInstance()
                            .getInputReader(matchPlayersFile);
                    List<Player> matchPlayers = ir.readPlayersDataPostMatch(matchPlayersFile);
                    cell = row.createCell(cellid++);
                    for (j = 0; j < matchPlayers.size(); j++) {
                        if (matchPlayers.get(j).getName().equalsIgnoreCase(player.getName()))
                            cell.setCellValue(matchPlayers.get(j).getPoints());
                    }
                }
            }
            rowid += InputDataConstants.OUTPUT_PADDING_BETWEEN_TEAMS;
        }
        FileOutputStream out = new FileOutputStream(new File(outputFile));
        workbook.write(out);
        out.close();
        if (ifMatchPlayersFileIsPresent) {
            outputDreamTeam(teams, noOfTeams, workbook, matchPlayersFile, outputFile);
        }
        workbook.close();
        System.out.println("Teams formed and entered into the sheet. Checkout");
    }

    private void outputDreamTeam(List<List<Player>> teams, int noOfTeams, XSSFWorkbook workbook,
            String matchPlayersFile, String outputFile) throws IOException {

        int i, j, rowid = 0;
        boolean doesMatch = false;
        XSSFRow row;
        InputReader ir = new ExcelInputReader();
        List<Player> playersWhoPlayedInTheMatch = ir.readPlayersDataPostMatch(matchPlayersFile);
        List<Player> dreamTeamPlayerstop11 = new ArrayList<Player>();
        XSSFSheet sheet = workbook.createSheet("DreamTeam");

        for (i = 0; i < LimitConstants.NO_OF_PLAYERS_IN_TEAM; i++) {
            dreamTeamPlayerstop11.add(playersWhoPlayedInTheMatch.get(i));
        }
        SortUtil.sortPlayersByPoints(dreamTeamPlayerstop11);

        for (i = 0; i < noOfTeams; i++) {
            SortUtil.sortPlayersByNames(teams.get(i));

            for (j = 0; j < teams.get(i).size(); j++) {
                if (!(teams.get(i).get(j).getName().equalsIgnoreCase(dreamTeamPlayerstop11.get(j).getName()))) {
                    break;
                }
                if (j == teams.get(i).size() - 1) {
                    doesMatch = true;
                }
            }
            if (doesMatch == true) {
                // the dream team exists in my top team - print the rank of the team, names and
                // individual points
                for (j = 0; j < teams.get(i).size(); j++) {
                    row = sheet.createRow(rowid++);
                    int cellid = 0;

                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue(i + 1);

                    cell = row.createCell(cellid++);
                    cell.setCellValue(dreamTeamPlayerstop11.get(j).getName());

                    cell = row.createCell(cellid++);
                    cell.setCellValue(dreamTeamPlayerstop11.get(j).getPoints());
                }
                FileOutputStream out = new FileOutputStream(new File(outputFile));
                workbook.write(out);
                out.close();
                workbook.close();
                System.out.println("DreamTeam matched one of your top records. Check out the sheet.");
            }
        }
    }
}
