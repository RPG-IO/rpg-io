package io.rpg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 200, 200);

    List<List<Integer>> map = new ArrayList<>();
    int counter = 0;
    for(int i = 0; i < 20; i++){
      ArrayList<Integer> row = new ArrayList<>();
      for(int j = 0; j < 20; j++)
        row.add(counter++);
      map.add(row);
    }

    scene.setOnMouseClicked(new ClickInteractionHandler(map, 0, 0));
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}