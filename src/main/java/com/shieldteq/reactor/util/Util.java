package com.shieldteq.reactor.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    private Util() {
    }

    public static List<String> readPlayers(String pathUrl) throws IOException {
        String[] content = readFile(pathUrl).split("\n");
        return Arrays.stream(content).map(String::trim).toList();
    }

    public static String readFile(String pathUrl) throws IOException {
        Path path = Path.of(pathUrl);
        return readFile(path);
    }

    public static String readFile(Path path) throws IOException {
        return Files.exists(path) ? Files.readString(path) : "";
    }

    public static String getRandom(List<String> playerList) {
        return playerList.get(new Random().nextInt(playerList.size()));
    }

    public static void writeToFile(String team, String fileName) throws IOException {
        Path path = Path.of(fileName);
        if (Files.exists(path)) Files.delete(path);
        Files.writeString(path, team);
    }

    public static void countPlayerInMyTeams() throws IOException {
        Path path = Path.of("teams-my-circle");

        try (Stream<Path> files = Files.list(path)) {
            Map<String, Long> random = files.filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().startsWith("Random"))
                    .map(x -> {
                        try {
                            return readFile(x);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .flatMap(x -> Arrays.stream(x.split("\n")))
                    .filter(x -> !x.startsWith("Captain") && !x.startsWith("Vice"))
                    .map(x -> x.trim().substring(x.indexOf(".") + 1, x.indexOf(" (")))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


            random.forEach((k, v) -> System.out.println(k + ": " + v));
        }
    }
}
