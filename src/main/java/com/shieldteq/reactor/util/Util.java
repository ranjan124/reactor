package com.shieldteq.reactor.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Util {
    private Util() {
    }

    public static List<String> readPlayers(String pathUrl) throws IOException {
        String[] content = readFile(pathUrl).split(",");
        return Arrays.stream(content).map(String::trim).toList();
    }

    public static String readFile(String pathUrl) throws IOException {
        Path path = Path.of(pathUrl);
        return Files.exists(path) ? Files.readString(path) : "";
    }

    public static String getRandom(List<String> playerList) {
        return playerList.get(new Random().nextInt(playerList.size()));
    }

    public static void writeToFile(String team, String fileName) throws IOException {
        Path path = Path.of(fileName);
        if (Files.exists(path)) Files.delete(path);
//        Files.createFile(path);
        Files.writeString(path, team);
    }
}
