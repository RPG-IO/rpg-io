package io.rpg.view;

import io.rpg.model.data.Inventory;
import io.rpg.model.data.Position;
import io.rpg.model.object.Player;
import io.rpg.viewmodel.InventoryPopupViewModel;
import io.rpg.viewmodel.TextPopupViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryPopup extends Scene {
    public InventoryPopup(Inventory inventory) {
        super(new Group(), Color.TRANSPARENT);
        Group group=new Group();
        for (int i = 0; i <inventory.items.size(); i++) {
//            String assetPath=inventory.items.get(i).getAssetPath();
//            to display objects in the menu
            InventoryGameObjectView imageGameObjectView=new InventoryGameObjectView(inventory.items.get(i));

            imageGameObjectView.setX(i*50);
            imageGameObjectView.setY(0);
            imageGameObjectView.setOnMouseClicked(event->{
                System.out.println("Object clicked");
                InventoryGameObjectView src=(InventoryGameObjectView) event.getSource();
                System.out.println(src.collectibleGameObject.getAssetPath());
                System.out.println();
            });
            group.getChildren().add(imageGameObjectView);

        }

        this.setRoot(group);

        this.setFill(Color.TRANSPARENT);
    }


}
