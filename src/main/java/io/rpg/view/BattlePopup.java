package io.rpg.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.EventListener;
import java.util.Random;

public class BattlePopup extends Scene {

    final int SPACING = 25;
    private char [] singleMove={'W','A','S','D'};
    private Random random;
    private String sequence;
    private int sequenceLength =5;
    private Label[] characterLabels;
    private Label isOk;
    private int currentSequencePosition;

    public BattlePopup(){
        super(new Group(), Color.TRANSPARENT);
        this.random=new Random();
        this.sequence=generateRandomSequence(sequenceLength);
        Group group = new Group();
        //TODO: load asset path from config
        ImageView imageView = new ImageView(GameObjectView.resolvePathToJFXFormat("assets/popup-background.png"));
        imageView.setX(0);
        imageView.setY(0);
        this.characterLabels=new Label[sequenceLength];
        double centerX=getX()+getWidth()/2;
        double centerY=getY()+getHeight()/2;
        int singleLabelWidth=25;
        double allLabelsWidth=sequenceLength*singleLabelWidth+(sequenceLength-1)*SPACING;
        this.currentSequencePosition=0;
        this.isOk=new Label();
        this.isOk.setLayoutX(200);
        this.isOk.setLayoutY(250);
        group.getChildren().add(imageView);

        for(int i=0;i<sequenceLength;i++){
            this.characterLabels[i]=new Label(String.valueOf(sequence.charAt(i)));
            this.characterLabels[i].setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + 18);
            this.characterLabels[i].setLayoutX(i*SPACING+i*singleLabelWidth);
            this.characterLabels[i].setLayoutY(centerY);
            group.getChildren().add(characterLabels[i]);
            double x=characterLabels[i].getLayoutX();
            double y=characterLabels[i].getLayoutY();
            System.out.println(x+" "+y);
        }

        this.setRoot(group);
        this.setFill(Color.TRANSPARENT);

        setOnKeyPressed((event)->{
            String str=event.getCode().getChar();
//            String character=String
            if(str.equals(String.valueOf(sequence.charAt(currentSequencePosition)))){
//                this.characterLabels[currentSequencePosition];
                currentSequencePosition++;
                if(this.currentSequencePosition==sequenceLength){
                    this.isOk.setText("OK");
                    group.getChildren().add(this.isOk);
                }
            }

        });
    }

    public String generateRandomSequence(int length){
        String str="";
        for(int i=0;i<length;i++){
            str+=singleMove[random.nextInt(singleMove.length)];
        }
        return str;
    }

    public boolean checkPassedLetter(char letter,int position){
        if(sequence.charAt(position)==letter)
            return true;
        else
            return false;
    }

}
