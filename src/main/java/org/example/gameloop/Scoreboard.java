package org.example.gameloop;

import org.example.bot.BotPlayer;
import org.example.enums.PistiTypes;
import org.example.player.HumanPlayer;
import org.example.player.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Scoreboard {
    private final Map<Player, Integer> scores;
    private final Map<Player, PistiCounter> pistiCounts;


    public Scoreboard(HumanPlayer humanPlayer, BotPlayer bot) {
        this.scores = new HashMap<>();
        this.scores.put(humanPlayer, 0);
        this.scores.put(bot, 0);

        this.pistiCounts = new HashMap<>();
        this.pistiCounts.put(humanPlayer, new PistiCounter());
        this.pistiCounts.put(bot, new PistiCounter());
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

    public void addPisti(Player player, PistiTypes type) {
        pistiCounts.get(player).incrementPistiCount(type);
    }

    public Map<Player, PistiCounter> getPistiCounts() {
        return Collections.unmodifiableMap(pistiCounts);
    }

    @Override
    public String toString() {
        return "Scoreboard{" +
                "scores=" + scores +
                ", pistiCounts=" + pistiCounts +
                '}';
    }
}
