package io.rpg.model;

import javafx.scene.image.Image;

/**
 * A Temporary Class waiting to be replaced with InteractiveGameObject class from branch RPG-79
 */

abstract public class InteractiveGameObject extends io.rpg.model.GameObject{
    public InteractiveGameObject(Vector position, Image image) {
        super(position, image);
    }

    abstract public void onAction();
}
