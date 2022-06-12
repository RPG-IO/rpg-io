package io.rpg.util;

public class BattleResult {
    public enum Result { VICTORY, DEFEAT, DRAW };
    private final Result result;
    private final int reward;

    public BattleResult(Result result, int reward) {
        this.result = result;
        this.reward = reward;
    }

    public String getMessage() {
        return switch (result) {
            case VICTORY -> "Wygrałeś! Twoja nagroda: " + reward + " pkt.";
            case DEFEAT -> "Przegrałeś. Postaraj się podnieść swoje statystyki.";
            case DRAW -> "Remis.";
        };
    }
}
