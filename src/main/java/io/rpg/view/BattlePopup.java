package io.rpg.view;

import io.rpg.model.object.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.EventListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BattlePopup extends Scene {

    final int SPACING = 25;
    final int POINTS_PER_SECOND = 50;
    final int PENALTY=-10;
    private char [] singleMove={'W','A','S','D'};
    private Random random;
    private String sequence;
    private int sequenceLength =5;
    private Label[] characterLabels;
    private ImageView[] timerDots;
    private Label isOk;
    private int currentSequencePosition;
    private ImageView swordIcon;
    private Timer timer;
    private int timeToCountDown;
    private Player player;
    private Button button;


    public BattlePopup(Player player){

        super(new Group(), Color.TRANSPARENT);
        this.player=player;
        this.timer= new java.util.Timer();
        this.timeToCountDown=5;
        this.random=new Random();
        this.timerDots=new ImageView[timeToCountDown];
        this.sequence=generateRandomSequence(sequenceLength);
        this.button=new Button("OK");


        Group group = new Group();
        //TODO: load asset path from config
        ImageView imageView = new ImageView(GameObjectView.resolvePathToJFXFormat("assets/popup-background.png"));

        this.swordIcon=new ImageView(GameObjectView.resolvePathToJFXFormat("assets/sword6.png"));
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
        ImageView imageViewButton=new ImageView(GameObjectView.resolvePathToJFXFormat("assets/button-image.png"));
        button.setLayoutX(imageView.getImage().getWidth()/2-imageViewButton.getImage().getWidth()/2);
        button.setLayoutY(imageView.getImage().getHeight()-imageViewButton.getImage().getHeight()/2);
        button.setGraphic(imageViewButton);
        button.setContentDisplay(ContentDisplay.CENTER);
        button.setStyle("-fx-background-color: transparent;");
        group.getChildren().add(imageView);
        group.getChildren().add(button);
        for(int i=0;i<5;i++){
            timerDots[i] = new ImageView(GameObjectView.resolvePathToJFXFormat("assets/button-image.png"));
            timerDots[i].setLayoutX(i*SPACING+i*singleLabelWidth+ imageView.getImage().getWidth()/2-allLabelsWidth/2);
            timerDots[i].setLayoutY(centerY+ imageView.getImage().getHeight()/2+50);
            timerDots[i].setScaleX(0.5);
            timerDots[i].setScaleY(0.5);
            group.getChildren().add(timerDots[i]);
        }
        group.getChildren().add(swordIcon);

        for(int i=0;i<sequenceLength;i++){
            this.characterLabels[i]=new Label(String.valueOf(sequence.charAt(i)));
            this.characterLabels[i].setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + 18);
            this.characterLabels[i].setLayoutX(i*SPACING+i*singleLabelWidth+ imageView.getImage().getWidth()/2-allLabelsWidth/2);
            this.characterLabels[i].setLayoutY(centerY+ imageView.getImage().getHeight()/2);
            this.swordIcon.setLayoutX(imageView.getImage().getWidth()/2);
            this.swordIcon.setLayoutY(20);
            group.getChildren().add(characterLabels[i]);
            double x=characterLabels[i].getLayoutX();
            double y=characterLabels[i].getLayoutY();
            System.out.println(x+" "+y);
        }

        this.setRoot(group);
        this.setFill(Color.TRANSPARENT);

        setOnKeyPressed((event)->{
            String str=event.getCode().getChar();
            if(str.equals(String.valueOf(sequence.charAt(currentSequencePosition)))){
                this.characterLabels[currentSequencePosition].setStyle("-fx-font-family: Monospaced; -fx-text-fill: #2bee1e; -fx-font-weight: bold; -fx-font-size: " + 18);
                currentSequencePosition++;
                if(this.currentSequencePosition==sequenceLength){
                    win();
                    this.isOk.setText("OK");
                    group.getChildren().add(this.isOk);
                }
            }else{
                this.characterLabels[currentSequencePosition].setStyle("-fx-font-family: Monospaced; -fx-text-fill: #fa2902; -fx-font-weight: bold; -fx-font-size: " + 18);
                currentSequencePosition++;
            }
        });

        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        timerTick();
                    }
                });
            }
        },1000,1000);
        button.setOnAction((event)->{

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

    private void timerTick(){
        timeToCountDown--;
        if(timeToCountDown<0){
            timer.cancel();
            lose();
            return;
        }
        timerDots[timeToCountDown].setImage(new Image(GameObjectView.resolvePathToJFXFormat("assets/button-image-2.png")));
    }

    public void win(){
        player.addPoints(timeToCountDown*POINTS_PER_SECOND);
        timer.cancel();
    }

    public void lose(){
        player.removePoints(PENALTY);
    }

    public void setCloseButtonActionListener(EventHandler<ActionEvent> value){
        button.setOnAction(value);
    }

}
