package io.rpg.view;

import io.rpg.model.object.CollectibleGameObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InventoryGameObjectView extends ImageView {
    CollectibleGameObject collectibleGameObject;

    public InventoryGameObjectView(CollectibleGameObject collectibleGameObject) {
        this.collectibleGameObject=collectibleGameObject;
        Image image=new Image(GameObjectView.resolvePathToJFXFormat(collectibleGameObject.getAssetPath()));
        setImage(image);
    }
}
