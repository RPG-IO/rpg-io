package io.rpg.model;

import javafx.scene.image.Image;

public class Player extends GameObject{

    float speed;
    Vector direction;
    public Player(Vector position, Image image){
        super(position,image);
        speed=0.1f;
        direction=new Vector(0,0);
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public void update(){
        this.position=new Vector(this.position.x+speed*direction.x,this.position.y+speed*direction.y);
    }
}
