package Match.util;

import java.util.Comparator;
import java.util.List;

import Match.Player;

public class SortUtil {

    public static void sortTeamsByEstimatedScores(List<List<Player>> listOfTeams) {
        listOfTeams.sort(new Comparator<List<Player>>() {
            public int compare(List<Player> list1, List<Player> list2) {
                int i;
                double sumOfEstimatedScores1 = 0;
                double sumOfEstimatedScores2 = 0;
                double sumcredits1 = 0;
                double sumcredits2 = 0;

                for (i = 0; i < list1.size(); i++) {
                    sumOfEstimatedScores1 += list1.get(i).getEstimatedScore();
                    sumcredits1 += list1.get(i).getCredits();
                }
                for (i = 0; i < list2.size(); i++) {
                    sumOfEstimatedScores2 += list2.get(i).getEstimatedScore();
                    sumcredits2 += list2.get(i).getCredits();
                }

                if (sumOfEstimatedScores1 == sumOfEstimatedScores2) {
                    {
                        if (sumcredits1 == sumcredits2)
                            return 0;
                        else if (sumcredits1 > sumcredits2)
                            return -1;
                        else
                            return 1;
                    }
                } else if (sumOfEstimatedScores1 > sumOfEstimatedScores2)
                    return -1;

                else
                    return 1;
            }
        });
    }

    public static void sortTeamsByCredits(List<List<Player>> listOfTeams) {
        listOfTeams.sort(new Comparator<List<Player>>() {
            public int compare(List<Player> list1, List<Player> list2) {

                int i;
                double sum1 = 0;
                double sum2 = 0;
                for (i = 0; i < list1.size(); i++)
                    sum1 += list1.get(i).getCredits();
                for (i = 0; i < list2.size(); i++)
                    sum2 += list2.get(i).getCredits();

                if (sum1 == sum2) {
                    return 0;
                }

                else if (sum1 > sum2)
                    return -1;

                else
                    return 1;
            }
        });
    }

    public static void sortPlayersByPoints(List<Player> players) {
        players.sort(new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                if (player1.getPoints() == player2.getPoints()) {
                    return 0;
                }

                else if (player1.getPoints() > player2.getPoints())
                    return -1;

                else
                    return 1;
            }
        });
    }

    public static void sortPlayersByEstimatedScores(List<Player> players) {
        players.sort(new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                if (player1.getEstimatedScore() == player2.getEstimatedScore()) {
                    return 0;
                }

                else if (player1.getEstimatedScore() > player2.getEstimatedScore())
                    return -1;

                else
                    return 1;
            }
        });
    }

    public static void sortPlayersByNames(List<Player> players) {
        players.sort(new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                if (player1.getName().equalsIgnoreCase(player2.getName())) {
                    return 0;
                } else if (player1.getName().compareTo(player2.getName()) < 0)
                    return -1;

                else
                    return 1;
            }
        });
    }

}
