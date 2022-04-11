package io.rpg.gui;

import io.rpg.gui.model.LocationModel;
import io.rpg.gui.popups.PointsEarnedPopup;
import io.rpg.model.Game;
import io.rpg.model.Vector;
import io.rpg.view.LocationView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LocationController implements Initializable {
    private final static URL FXML_URL = LocationController.class.getResource("location-view.fxml");

    @FXML private ImageView mapImageView;
    @FXML private Pane foregroundPane, contentPane;
    @FXML private HBox parent;

    private LocationModel model;
    private Scene scene;
    private Game game;

    private final PointsEarnedPopup pointsPopup = new PointsEarnedPopup();

    public static LocationController load() throws IOException {
        FXMLLoader loader = new FXMLLoader(FXML_URL);
        loader.load();
        return loader.getController();
    }

    public LocationController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapImageView.imageProperty().addListener((property, oldImg, newImg) -> {
            contentPane.setPrefWidth(newImg.getWidth());
            contentPane.setPrefHeight(newImg.getHeight());

            foregroundPane.setPrefWidth(newImg.getWidth());
            foregroundPane.setPrefHeight(newImg.getHeight());

            mapImageView.setFitWidth(newImg.getWidth());
            mapImageView.setFitHeight(newImg.getHeight());

        });

        model = new LocationModel(mapImageView.imageProperty(), foregroundPane.getChildren(), this);

        scene = new Scene(parent);
        scene.addEventFilter(KeyEvent.KEY_TYPED, this::onKeyTyped;
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, this::onKeyReleased);
    }

    public void onKeyPressed(KeyEvent event){
        switch(event.getCode()){
            case W:
//                game.getPlayer().setDirection(new Vector(0,-1));
                game.getPlayer().setUpPressed(true);
                break;
            case S:
//                game.getPlayer().setDirection(new Vector(0,1));
                game.getPlayer().setDownPressed(true);
                break;
            case A:
//                game.getPlayer().setDirection(new Vector(-1,0));
                game.getPlayer().setLeftPressed(true);
                break;
            case D:
//                game.getPlayer().setDirection(new Vector(1,0));
                game.getPlayer().setRightPressed(true);
                break;
        }

    }

    public void onKeyReleased(KeyEvent event){
        event.getCharacter();
//        System.out.println();
        switch(event.getCode()){
            case W:
//                game.getPlayer().setDirection(new Vector(0,-1));
                game.getPlayer().setUpPressed(false);
                break;
            case S:
//                game.getPlayer().setDirection(new Vector(0,1));
                game.getPlayer().setDownPressed(false);
                break;
            case A:
//                game.getPlayer().setDirection(new Vector(-1,0));
                game.getPlayer().setLeftPressed(false);
                break;
            case D:
//                game.getPlayer().setDirection(new Vector(1,0));
                game.getPlayer().setRightPressed(false);
                break;
        }

    }

    public void onKeyTyped(KeyEvent event) {
        // TODO: 01.04.2022 Implement key actions
        System.out.println(event);
        String c=event.getCharacter();
//        System.out.println();
        switch(c){
            case "f":
                Stage popup = pointsPopup.getPopup(5, scene);
								popup.show();	
                break;
        }
    }

    public void setGame(Game game){
        this.game=game;
    }
    public LocationModel getModel(){
        return model;
    }

    public Scene getScene() {
        return scene;
    }

}
