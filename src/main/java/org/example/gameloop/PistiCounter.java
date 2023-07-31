package org.example.gameloop;

import org.example.enums.PistiTypes;

import java.util.HashMap;
import java.util.Map;

public class PistiCounter {
    private final Map<PistiTypes, Integer> counts;

    public PistiCounter() {
        this.counts = new HashMap<>();
        for (PistiTypes type : PistiTypes.values()) {
            this.counts.put(type, 0);
        }
    }

    public void incrementPistiCount(PistiTypes type) {
        this.counts.put(type, this.counts.get(type) + 1);
    }

    @Override
    public String toString() {
        return "PistiCount{" +
                "counts=" + counts +
                '}';
    }
}
