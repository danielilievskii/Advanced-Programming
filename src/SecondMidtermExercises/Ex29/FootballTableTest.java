package SecondMidtermExercises.Ex29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */

class FootballTeam {
    String name;
    int homeGoals;
    int awayGoals;
    int receivedGoals;
    int wins;
    int loses;
    int draws;

    public FootballTeam(String name) {
        this.name = name;
        this.homeGoals = 0;
        this.awayGoals = 0;
        this.receivedGoals = 0;
        this.wins = 0;
        this.loses = 0;
        this.draws = 0;
    }

    public void addHomeGoals(int homegoals) {
        this.homeGoals += homegoals;
    }

    public void addAwayGoals(int awayGoals) {
        this.awayGoals += awayGoals;
    }
    public void addReceivedGoals(int receivedGoals) {
        this.receivedGoals+=receivedGoals;
    }
    public void addWin() {
        wins++;
    }
    public void addLose() {
        loses++;
    }
    public void addDraw() {
        draws++;
    }
    public int getNumMathces() {
        return this.wins+this.loses+this.draws;
    }
    public int getPoints() {
        return this.wins * 3 + this.draws;
    }
    public int getGoalDifference() {
        return homeGoals+awayGoals - receivedGoals;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d", name, getNumMathces(), wins, draws, loses, getPoints(), getGoalDifference());
    }
}

class FootballTable {

    Map<String, FootballTeam> allTeams;

    public FootballTable() {
        allTeams = new TreeMap<>();
    }
    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        allTeams.putIfAbsent(homeTeam, new FootballTeam(homeTeam));
        allTeams.putIfAbsent(awayTeam, new FootballTeam(awayTeam));

        FootballTeam homeTeamObj = allTeams.get(homeTeam);
        homeTeamObj.addHomeGoals(homeGoals);
        homeTeamObj.addReceivedGoals(awayGoals);

        FootballTeam awayTeamObj = allTeams.get(awayTeam);
        awayTeamObj.addAwayGoals(awayGoals);
        awayTeamObj.addReceivedGoals(homeGoals);

        if(homeGoals > awayGoals) {
            homeTeamObj.addWin();
            awayTeamObj.addLose();
        } else if (homeGoals == awayGoals) {
            homeTeamObj.addDraw();
            awayTeamObj.addDraw();
        } else {
            homeTeamObj.addLose();
            awayTeamObj.addWin();
        }
    }

    public void printTable() {
        List<FootballTeam> sortedTeams = allTeams.values().stream()
                .sorted(Comparator
                        .comparing(FootballTeam::getPoints)
                        .thenComparing(FootballTeam::getGoalDifference)
                        .reversed()
                        .thenComparing(FootballTeam::getName))
                .collect(Collectors.toList());

        IntStream.range(0, sortedTeams.size())
                .forEach(i -> System.out.printf("%2d. %s\n", i+1, sortedTeams.get(i)));

//        int idx =1;
//        for (FootballTeam team : sortedTeams) {
//            System.out.println(String.format("%d. %s", idx++, team));
//        }
    }
}
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here


