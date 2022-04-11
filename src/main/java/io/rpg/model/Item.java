package io.rpg.model;

public class Item {
    private int strength;
    private final String name;

    public Item(int strength, String name) {
        this.strength = strength;
        this.name = name;
    }

    public int strength() {
        return strength;
    }

    public String name() {
        return name;
    }
}