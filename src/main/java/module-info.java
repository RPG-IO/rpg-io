module io.rpg {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires com.google.gson;
  requires org.jetbrains.annotations;
  requires org.apache.logging.log4j;
  requires org.apache.logging.log4j.core;
  requires org.apache.commons.io;
  requires result.type;

  opens io.rpg.model.location to com.google.gson;
  opens io.rpg.model.object to com.google.gson;
  opens io.rpg.model.data to com.google.gson;
  opens io.rpg.model.actions to com.google.gson;

  opens io.rpg.viewmodel to javafx.fxml;
  opens io.rpg.wrapper to javafx.fxml;
  opens io.rpg.view to javafx.fxml;
  opens io.rpg.config.model to com.google.gson;

  exports io.rpg;
  opens io.rpg.view.popups to javafx.fxml;
}
