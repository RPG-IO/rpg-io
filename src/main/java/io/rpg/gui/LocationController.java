package io.rpg.gui;

import io.rpg.gui.model.LocationModel;
import io.rpg.model.GameObjectStandIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LocationController implements Initializable {
    private final static URL FXML_URL = LocationController.class.getResource("location-view.fxml");

    @FXML private ImageView mapImageView;
    @FXML private Pane foregroundPane;
    @FXML private HBox parent;

    private LocationModel model;

    public static LocationController load() throws IOException {
        FXMLLoader loader = new FXMLLoader(FXML_URL);
        loader.load();
        return loader.getController();
    }

    public LocationController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foregroundPane.prefWidthProperty().bind(mapImageView.imageProperty().get().widthProperty());
        foregroundPane.prefHeightProperty().bind(mapImageView.imageProperty().get().heightProperty());

        model = new LocationModel(mapImageView.imageProperty(), foregroundPane.getChildren(), this);
    }

    @FXML
    private void onKeyTyped(KeyEvent event) {

    }

    public Parent getParent(){
        return parent;
    }

    public LocationModel getModel(){
        return model;
    }


}
