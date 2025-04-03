package com.shieldteq.reactor;

import com.shieldteq.reactor.service.RandomPlayerSelector;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        RandomPlayerSelector selector = new RandomPlayerSelector();
        selector.init("teams/team-1.txt", "teams/team-2.txt");
        selector.selectRandomPlayers(7, 4, 4, 1);
        selector.selectRandomPlayers(4, 7, 4, 5);
        selector.selectRandomPlayers(11, 4, 9);
        selector.writeCaptains();
    }
}
