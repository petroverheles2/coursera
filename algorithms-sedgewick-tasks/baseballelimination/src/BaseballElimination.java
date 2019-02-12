import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BaseballElimination {

    private final int teamsNumber;
    private final String[] teamNames;
    private final int[] wins;
    private final int[] losses;
    private final int[] remainingGames;
    private final int[][] remainingMatches;
    private final boolean[] isEliminated;
    private final Collection<String>[] eliminationCertificates;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        In input = new In(filename);

        if (!input.exists()) {
            throw new IllegalArgumentException();
        }

        String line = input.readLine();
        if (line == null) {
            throw new IllegalArgumentException("Incorrect file format");
        }

        teamsNumber = Integer.parseInt(line.trim());
        teamNames = new String[teamsNumber];
        wins = new int[teamsNumber];
        losses = new int[teamsNumber];
        remainingGames = new int[teamsNumber];
        remainingMatches = new int[teamsNumber][teamsNumber];
        int i = 0;
        while (input.hasNextLine()) {
            String s = input.readLine();

            if (s == null) {
                throw new IllegalArgumentException("Incorrect file format");
            }

            String[] tokens = s.trim().split("\\s+");
            String teamName = tokens[0];
            teamNames[i] = teamName;
            wins[i] = Integer.parseInt(tokens[1]);
            losses[i] = Integer.parseInt(tokens[2]);
            remainingGames[i] = Integer.parseInt(tokens[3]);

            for (int k = 0; k < teamsNumber; k++) {
                remainingMatches[i][k] = Integer.valueOf(tokens[4 + k]);
            }

            i++;
        }

        isEliminated = new boolean[teamsNumber];
        eliminationCertificates = new Collection[teamsNumber];

        calculateElimination();
    }

    private void calculateSimpleElimination() {
        for (int i = 0; i < teamsNumber; i++) {
            for (int j = 0; j < teamsNumber; j++) {
                if (i != j && wins[i] + remainingGames[i] < wins[j]) {
                    isEliminated[i] = true;
                    Collection<String> certificate = eliminationCertificates[i];
                    if (certificate == null) {
                        certificate = new LinkedList<>();
                        eliminationCertificates[i] = certificate;
                    }
                    certificate.add(teamNames[j]);
                }
            }
        }
    }

    private void calculateElimination() {
        calculateSimpleElimination();

        for (int i = 0; i < teamsNumber; i++) {
            if (!isEliminated[i]) {
                calculateComplexElimination(i);
            }
        }
    }

    private void calculateComplexElimination(int teamIndex) {
        int M = teamsNumber - 1;
        int gamePairsNumber = M * (M - 1) / 2;
        int numberOfVertices = M * M + 2;
        FlowNetwork flowNetwork = new FlowNetwork(numberOfVertices);

        int v = 0;
        int s = v;
        int t = numberOfVertices - 1;

        int[] teamIndexToVertex = new int[teamsNumber];
        for (int i = 0; i < teamsNumber; i++) {
            teamIndexToVertex[i] = -1;
        }
        List<FlowEdge> fromSFlowEdges = new ArrayList<>();
        for(int i = 0; i < teamsNumber; i++) {
            for (int j = i + 1; j < teamsNumber; j++) {
                if (i != teamIndex && j != teamIndex) {
                    int gameVertex = ++v;
                    FlowEdge fromSFlowEdge = new FlowEdge(s, gameVertex, remainingMatches[i][j]);
                    flowNetwork.addEdge(fromSFlowEdge);
                    fromSFlowEdges.add(fromSFlowEdge);

                    int teamVertex = teamIndexToVertex[i]; // one team
                    if (teamVertex == -1) {
                        teamVertex = gamePairsNumber + i + (i < teamIndex ? 1 : 0);
                        teamIndexToVertex[i] = teamVertex;
                        flowNetwork.addEdge(new FlowEdge(teamVertex, t, wins[teamIndex] + remainingGames[teamIndex] - wins[i]));
                    }
                    flowNetwork.addEdge(new FlowEdge(gameVertex, teamVertex, Double.POSITIVE_INFINITY));

                    teamVertex = teamIndexToVertex[j]; // another team
                    if (teamVertex == -1) {
                        teamVertex = gamePairsNumber + j + (j < teamIndex ? 1 : 0);
                        teamIndexToVertex[j] = teamVertex;
                        flowNetwork.addEdge(new FlowEdge(teamVertex, t, wins[teamIndex] + remainingGames[teamIndex] - wins[j]));
                    }
                    flowNetwork.addEdge(new FlowEdge(gameVertex, teamVertex, Double.POSITIVE_INFINITY));
                }
            }
        }

        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, s, t);

        for (FlowEdge fromSFlowEdge : fromSFlowEdges) {
            if (fromSFlowEdge.flow() < fromSFlowEdge.capacity()) {
                isEliminated[teamIndex] = true;

                for (int p = 1; p <= gamePairsNumber; p++) {
                    if (fordFulkerson.inCut(p)) {
                        Collection<String> certificate = eliminationCertificates[teamIndex];
                        if (certificate == null) {
                            certificate = new HashSet<>();
                            eliminationCertificates[teamIndex] = certificate;
                        }

                        certificate.add(teamNames[calculateTeamOneByGamePairVertex(p, teamIndex)]);
                        certificate.add(teamNames[calculateTeamTwoByGamePairVertex(p, teamIndex)]);
                    }
                }


                break;
            }
        }
    }

    private int calculateTeamOneByGamePairVertex(int gameVertex, int teamIndex) {
        int v = 1;
        for(int i = 0; i < teamsNumber; i++) {
            for (int j = i + 1; j < teamsNumber; j++) {
                if (i != teamIndex && j != teamIndex) {
                    if (gameVertex == v) {
                        return i;
                    }
                    v++;
                }
            }
        }

        throw new IllegalArgumentException("Cannot detect team one by game vertex " + gameVertex);
    }

    private int calculateTeamTwoByGamePairVertex(int gameVertex, int teamIndex) {
        int v = 1;
        for(int i = 0; i < teamsNumber; i++) {
            for (int j = i + 1; j < teamsNumber; j++) {
                if (i != teamIndex && j != teamIndex) {
                    if (gameVertex == v) {
                        return j;
                    }
                    v++;
                }
            }
        }

        throw new IllegalArgumentException("Cannot detect team one by game vertex " + gameVertex);
    }

    // number of teams
    public int numberOfTeams() {
        return teamsNumber;
    }

    // all teams
    public Iterable<String> teams() {
        return Arrays.asList(teamNames);
    }

    // number of wins for given team
    public int wins(String team) {
        return wins[getTeamIndex(team)];
    }

    public int losses(String team) {
        return losses[getTeamIndex(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remainingGames[getTeamIndex(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return remainingMatches[getTeamIndex(team1)][getTeamIndex(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return isEliminated[getTeamIndex(team)];
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return eliminationCertificates[getTeamIndex(team)];
    }

    private int getTeamIndex(String team) {
        for (int i = 0; i < teamsNumber; i++) {
            if (teamNames[i].equals(team)) {
                return i;
            }
        }

        throw new IllegalArgumentException("Unknown team " + team);
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
