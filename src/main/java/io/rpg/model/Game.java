package io.rpg.model;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Game {
    private Player player;
//    Timer
    ArrayList<GameObject> gameObjects;

    public Game(){
        this.gameObjects=new ArrayList<>();
        this.player=null;
    }

    public void addGameObject(GameObject gameObject) throws Exception {
        if(gameObject instanceof Player){
            if(player!=null)
                throw new Exception();
            player=(Player)gameObject;
        }
        gameObjects.add(gameObject);
    }

    public GameObject getObject(int index){
        return gameObjects.get(index);
    }
    public int getObjectCount(){
        return gameObjects.size();
    }

    public Player getPlayer() {
        return player;
    }
    public void update(float elapsed){
        player.update(elapsed);
    }
}
