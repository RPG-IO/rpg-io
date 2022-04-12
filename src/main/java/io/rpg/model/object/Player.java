package io.rpg.model.object;

import io.rpg.model.data.Vector;
import io.rpg.torefract.GameObject;
import javafx.scene.image.Image;

public class Player extends GameObject {

    int strength;
    float speed;
    Vector direction;
    boolean rightPressed;
    boolean leftPressed;
    boolean upPressed;
    boolean downPressed;

    public Player(Vector position, Image image){
        super(position,image);
        speed=5f;
        direction=new Vector(0,0);
        this.rightPressed=false;
        this.leftPressed=false;
        this.upPressed=false;
        this.downPressed=false;
        this.strength=0;
    }

    public void updateStrength(int value){
        strength += value;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public void update(float elapsed){
        float y=0;
        float x=0;
//        the sum tells us the direction
        if(upPressed)
            y+=-1;

        if(downPressed)
            y+=1;

        if(leftPressed)
            x+=-1;

        if(rightPressed)
            x+=1;

        this.position=new Vector(this.position.x+speed*x*elapsed/1000,this.position.y+speed*y*elapsed/1000);
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
