package io.rpg.gui.model;

import io.rpg.gui.LocationController;
import io.rpg.model.*;
import io.rpg.model.GameObject;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.HashMap;

public class LocationModel {

    private Game game;
    private final ObjectProperty<Image> background;
    // TODO: 01.04.2022 Think about a better name
//    objects which we store on the playground
//    mapNodes views to be displayed
    private final ObservableList<Node> mapNodes;
//    private

//    private HashMap<GameObject, Node> gameObject2NodeMap;
    private final LocationController controller;

    public LocationModel(ObjectProperty<Image> background, ObservableList<Node> mapNodes, LocationController controller) {
        this.background = background;
        this.mapNodes = mapNodes;
//        this.gameObject2NodeMap = new HashMap<>();
        this.controller = controller;
    }

    public LocationModel clear(){
//        this.gameObject2NodeMap = new HashMap<>();
        mapNodes.clear();
        return this;
    }

    public LocationModel setBackgroundImage(Image backgroundImage){
        background.setValue(backgroundImage);
        return this;
    }

    public LocationModel addMapObject(GameObject gameObject){
        ImageView imageView = new ImageView(gameObject.getImage());
//        gameObject
        Vector mapPosition = getMapPosition(gameObject.getPosition());
//        Pair<Integer,Integer> mapPosition = getMapPosition(gameObject.getPosition());
        imageView.setX(mapPosition.x);
        imageView.setY(mapPosition.y);
//        gameObject2NodeMap.put(gameObject, imageView);
        gameObject.setImageView(imageView);
        mapNodes.add(imageView);
        imageView.setOnMouseClicked((e) -> onGameObjectAction(gameObject));
        return this;
    }
    
    private void onGameObjectAction(GameObject source){
        // TODO: 01.04.2022 What to do when some GameObject was clicked
        System.out.println(source);
    }

    // TODO: 01.04.2022 Replace with Position
    private Vector getMapPosition(Vector position){
    //        size of one tile
        int scale = 32;
        return new Vector(position.x * scale, position.y * scale);
//        return new Pair<>(position.x * scale, position.y * scale);
    }

    public LocationModel setGame(Game game) {
        this.game = game;
        for(int i=0;i<game.getObjectCount();i++){
            addMapObject(game.getObject(i));
        }
        return this;
    }
    public void update(){
        Player player=game.getPlayer();
        player.getImageView().setX(player.getPosition().x);
        player.getImageView().setX(player.getPosition().y);
//        player.getPosition().y
//        game
    }
}
