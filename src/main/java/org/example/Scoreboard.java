package org.example;

import org.example.bot.BotPlayer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Scoreboard {
    private final Map<Player, Integer> scores;

    public Scoreboard(HumanPlayer humanPlayer, BotPlayer bot) {
        this.scores = new HashMap<>();
        this.scores.put(humanPlayer, 0);
        this.scores.put(bot, 0);
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

    public int getOpponentScore(BotPlayer bot) {
        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            if (!entry.getKey().equals(bot)) {
                return entry.getValue();
            }
        }
        throw new IllegalStateException("No opponent found for Bot player");
    }

    @Override
    public String toString() {
        return "Scoreboard{" +
                "scores=" + scores +
                '}';
    }
}
