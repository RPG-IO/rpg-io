package io.rpg.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A Temporary Class waiting to be replaced with GameObject class from branch RPG-79
 */
public class GameObject {

//    private Pair<Integer,Integer> position;
    protected Vector position;
    private Image image;
//    control that displays objects
    private ImageView imageView;

    public GameObject(Vector position, Image image) {
        this.position = position;
        this.image = image;
    }

    public Vector getPosition() {
        return position;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
