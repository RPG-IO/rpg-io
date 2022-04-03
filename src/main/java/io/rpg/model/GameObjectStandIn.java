package io.rpg.model;

import javafx.scene.image.Image;
import javafx.util.Pair;

/**
 * A Temporary Class waiting to be replaced with GameObject class from branch RPG-79
 */
public class GameObjectStandIn {

//    private Pair<Integer,Integer> position;
    private Vector position;
    private Image image;

    public GameObjectStandIn(Vector position, Image image) {
        this.position = position;
        this.image = image;
    }

    public Vector getPosition() {
        return position;
    }

    public Image getImage(){
        return image;
    }
}
