package io.rpg.gui.model;

import io.rpg.gui.LocationController;
import io.rpg.model.GameObjectStandIn;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.HashMap;

public class LocationModel {
    
    private final ObjectProperty<Image> background;
    // TODO: 01.04.2022 Think about a better name 
    private final ObservableList<Node> mapObjects;

    private HashMap<GameObjectStandIn, Node> gameObject2NodeMap;
    private final LocationController controller;

    public LocationModel(ObjectProperty<Image> background, ObservableList<Node> mapObjects, LocationController controller) {
        this.background = background;
        this.mapObjects = mapObjects;
        this.gameObject2NodeMap = new HashMap<>();
        this.controller = controller;
    }

    public LocationModel clear(){
        this.gameObject2NodeMap = new HashMap<>();
        mapObjects.clear();
        return this;
    }

    public LocationModel setBackgroundImage(Image backgroundImage){
        background.setValue(backgroundImage);
        return this;
    }

    public LocationModel addMapObject(GameObjectStandIn gameObject){
        ImageView imageView = new ImageView(gameObject.getImage());
        Pair<Integer,Integer> mapPosition = getMapPosition(gameObject.getPosition());
        imageView.setX(mapPosition.getKey());
        imageView.setY(mapPosition.getValue());
        gameObject2NodeMap.put(gameObject, imageView);
        mapObjects.add(imageView);
        imageView.setOnMouseClicked((e) -> onGameObjectAction(gameObject));
        return this;
    }
    
    private void onGameObjectAction(GameObjectStandIn source){
        // TODO: 01.04.2022 What to do when some GameObject was clicked
        System.out.println(source);
    }

    // TODO: 01.04.2022 Replace with Position
    private Pair<Integer, Integer> getMapPosition(Pair<Integer, Integer> position){
        int scale = 32;
        return new Pair<>(position.getKey() * scale, position.getValue() * scale);
    }


}