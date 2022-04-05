package io.rpg;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class ClickInteractionHandler implements EventHandler<MouseEvent> {
    private List<List<Integer>> a;
    private int posX;
    private int posY;

    public ClickInteractionHandler(List<List<Integer>> a, int posX, int posY) {
        this.a = a;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void handle(MouseEvent event){
        int clickX = (int)event.getSceneX();
        int clickY = (int)event.getSceneY();
        System.out.println(Math.pow(clickX-posX, 2) + Math.pow(clickY-posY, 2));
        if(Math.pow(clickX-posX, 2) + Math.pow(clickY-posY, 2) <= 2){
            System.out.println("ON ACTION");
        }
        else{
            System.out.println("NOT ON ACTION");
        }
    }
}
