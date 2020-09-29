package Match.input.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Match.Category;
import Match.Player;
import Match.input.api.InputReader;

public class ExcelInputReader implements InputReader {

    public ExcelInputReader() {
        super();
    }

    @Override
    public List<Player> readPlayersDataPreMatch(String filename) {
        String name, team;
        double credits;
        double estimatedScore;
        Category category;
        List<Player> players = new ArrayList<Player>();

        try {
            FileInputStream file = new FileInputStream(new File(filename));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (!(cell.getStringCellValue().trim().isEmpty())) {
                        name = cell.getStringCellValue().trim();
                        cell = cellIterator.next();
                        credits = cell.getNumericCellValue();
                        cell = cellIterator.next();
                        category = Category.valueOf(cell.getStringCellValue().trim());
                        cell = cellIterator.next();
                        team = cell.getStringCellValue().trim();

                        if (cellIterator.hasNext()) {
                            cell = cellIterator.next();
                            Player.Builder playerBuilder = new Player.Builder(name).setCredits(credits)
                                    .setCategory(category).setTeam(team);
                            try {
                                estimatedScore = cell.getNumericCellValue();
                                playerBuilder.setEstimatedScore(estimatedScore);
                            } catch (IllegalStateException | NumberFormatException e) {
                                System.out.println("Estimated score isnt available");
                            } finally {
                                players.add(playerBuilder.build());
                            }
                        }
                    }
                }
            }
            workbook.close();
            file.close();
        } catch (Exception e) {
            System.out.println("Some error occured with file reading or parsing");
        }
        return players;
    }

    @Override
    public List<Player> readPlayersDataPostMatch(String filename) {
        String name;
        double points;
        List<Player> matchPlayers = new ArrayList<Player>();

        try {
            FileInputStream file = new FileInputStream(new File(filename));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (!(cell.getStringCellValue().trim().isEmpty())) {
                        name = cell.getStringCellValue().trim();
                        cell = cellIterator.next();
                        points = cell.getNumericCellValue();
                        matchPlayers.add(new Player.Builder(name).setPoints(points).build());
                    }
                }
            }
            workbook.close();
            file.close();
        } catch (Exception e) {
            System.out.println("Some error occured with file reading or parsing");
        }
        return matchPlayers;
    }
}