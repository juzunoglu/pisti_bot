package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Scoreboard {
    private final Map<Player, Integer> scores;

    public Scoreboard() {
        this.scores = new HashMap<>();
    }

    public void addScore(Player player, int score) {
        scores.put(player, scores.getOrDefault(player, 0) + score);
    }

    public int getScore(Player player) {
        return scores.getOrDefault(player, 0);
    }

    public Map<Player, Integer> getScores() {
        return Collections.unmodifiableMap(scores);
    }

    @Override
    public String toString() {
        return "Scoreboard{" +
                "scores=" + scores +
                '}';
    }
}
