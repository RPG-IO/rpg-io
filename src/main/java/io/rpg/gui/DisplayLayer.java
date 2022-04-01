package io.rpg.gui;

import io.rpg.gui.model.LocationModel;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DisplayLayer {
    private final Stage mainStage;
    private final LocationController locationController;

    public DisplayLayer(Stage mainStage) throws IOException {
        this.mainStage = mainStage;
        mainStage.show();
        locationController = LocationController.load();
    }


    public LocationModel showLocation(){
        // maybe initialize the scene only once
        mainStage.setScene(locationController.getScene());
        return locationController.getModel()
                .clear();
    }


}
