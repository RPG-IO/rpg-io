package io.rpg.model.data;

import io.rpg.model.object.CollectibleGameObject;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    public List<CollectibleGameObject> items;

    public Inventory(){
        items=new ArrayList<>();
    }
    public void add(CollectibleGameObject object){
        items.add(object);
    }

}
