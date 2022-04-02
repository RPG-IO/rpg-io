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

  opens io.rpg to javafx.fxml;
  opens io.rpg.model to com.google.gson;
  exports io.rpg;
}
