package com.shieldteq.reactor.service;

import com.shieldteq.reactor.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RandomPlayerSelector {
    public static final int NO_OF_CAPTAINS = 2;
    private final List<String> team1 = new ArrayList<>();
    private final List<String> team2 = new ArrayList<>();
    private final List<String> captains = new ArrayList<>();
    private final List<String> captainsNames = new ArrayList<>();

    public void init(String team1Path, String team2Path) throws IOException {
        team1.addAll(Util.readPlayers(team1Path));
        team2.addAll(Util.readPlayers(team2Path));
    }

    public void selectRandomPlayers(int noOfPlayers, int noOfTeams, int startIndex) throws IOException {
        List<String> players = Stream.concat(team1.stream(), team2.stream()).toList();
        for (int i = startIndex; i < (noOfTeams + startIndex); i++) {
            List<String> team = selectPlayers(noOfPlayers, players);
            selectRandomCaptain(i, team);
        }
    }

    public void selectRandomPlayers(int noOfTeam1Players, int noOfTeam2Players, int noOfTeams, int startIndex) throws IOException {
        for (int i = startIndex; i < (noOfTeams + startIndex); i++) {
            List<String> list1 = selectPlayers(noOfTeam1Players, team1);
            List<String> list2 = selectPlayers(noOfTeam2Players, team2);
            List<String> team = Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toCollection(ArrayList::new));
            selectRandomCaptain(i, team);
        }
    }


    private void selectRandomCaptain(int i, List<String> team) throws IOException {
        List<String> captain = selectCaptains(team);
        captains.add(captain.getFirst());
        team.add("Captain : " + captain.getFirst() + " (Team " + i + ")");
        team.add("Vice Captain : " + captain.getLast() + " (Team " + i + ")");
        captainsNames.add("Captain : " + captain.getFirst() + " Team " + i);
        writeTeam(team, "Random Team " + i);
    }

    private List<String> selectCaptains(List<String> players) {

        List<String> playerList = players.stream().filter(player -> !captains.contains(player)).collect(Collectors.toList());
        if (playerList.size() < 2) {
            captains.clear();
            playerList = new ArrayList<>(players);
        }
        return selectRandom(NO_OF_CAPTAINS, playerList);
    }

    private List<String> selectPlayers(int noOfPlayers, List<String> players) {
        return selectRandom(noOfPlayers, players);
    }


    private static List<String> selectRandom(int noOfPlayers, List<String> playerList) {
        List<String> randomPlayers = new ArrayList<>();
        playerList = new ArrayList<>(playerList);
        if (noOfPlayers > playerList.size()) noOfPlayers = playerList.size();

        for (int i = 0; i < noOfPlayers; i++) {
            String player = Util.getRandom(playerList);
            playerList.remove(player);
            randomPlayers.add(player);
        }
        return randomPlayers;
    }

    private void writeTeam(List<String> team, String name) throws IOException {
        String teamStr = formatTeam(team);
        Util.writeToFile(teamStr, "teams-my-circle/" + name);
    }

    private String formatTeam(List<String> team) {
        AtomicInteger counter = new AtomicInteger();
        return team.stream().map(p -> getCounter(p, counter) + p + getTeam(p)).collect(Collectors.joining("\n"));
    }

    private static String getCounter(String name, AtomicInteger counter) {
        return (name.contains("Captain") ? "" : (counter.incrementAndGet()) + ". ");
    }

    private String getTeam(String player) {
        return player.contains("Captain") ? "" : team1.contains(player) ? " (Team 1)" : " (Team 2) ";
    }

    public void writeCaptains() throws IOException {
        Util.writeToFile(formatTeam(captainsNames), "teams-my-circle/captains");
    }
}
