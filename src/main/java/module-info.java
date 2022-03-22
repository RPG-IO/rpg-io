module io.rpg.rpg {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;

  opens io.rpg.rpg to javafx.fxml;
  exports io.rpg.rpg;
}