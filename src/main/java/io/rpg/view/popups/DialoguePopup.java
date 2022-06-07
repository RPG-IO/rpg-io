package io.rpg.view.popups;

import io.rpg.util.PathUtils;
import io.rpg.viewmodel.DialoguePopupViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DialoguePopup extends Scene {

  private final DialoguePopupViewModel viewModel;
  private final List<String> textPages;
  private int currentPage = 0;

  private static String backgroundPath;
  private static String npcFramePath;

  public DialoguePopup(String text, Image npcImage) {
    super(new Group(), Color.TRANSPARENT);
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(DialoguePopupViewModel.class.getResource("dialogue-popup-view.fxml")));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.setRoot(root);

    viewModel = loader.getController();
    viewModel.setNpcImage(npcImage);
    this.setFill(Color.TRANSPARENT);

    viewModel.setBackgroundImage(backgroundPath);
    viewModel.setNpcFrameImage(npcFramePath);

    viewModel.setCloseButtonImage(PathUtils.resolvePathToJFXFormat(PathUtils.resolvePathToAsset("close-button.png").get()));
    viewModel.setNextButtonImage(PathUtils.resolvePathToJFXFormat(PathUtils.resolvePathToAsset("right-arrow.png").get()));

    textPages = List.of(text.split("(?<=\\G.{200})")); //split text into 200-letter pages
    viewModel.setText(textPages.get(currentPage));
    if (textPages.size() == 1) {
      viewModel.setNextVisibility(false);
    }
    viewModel.setPreviousVisibility(false);
    viewModel.setNextButtonOnClick(event -> nextPage());
    viewModel.setPreviousButtonOnClick(event -> previousPage());
  }

  public void setCloseButtonCallback(EventHandler<? super MouseEvent> callback) {
    viewModel.setCloseButtonOnClick(callback);
  }

  public void nextPage() {
    currentPage++;
    viewModel.setText(textPages.get(currentPage));
    if (currentPage == textPages.size() - 1) {
      viewModel.setNextVisibility(false);
    }
    viewModel.setPreviousVisibility(true);
  }

  public void previousPage() {
    currentPage--;
    viewModel.setText(textPages.get(currentPage));
    if (currentPage == 0) {
      viewModel.setPreviousVisibility(false);
    }
    viewModel.setNextVisibility(true);
  }

  public static void setBackgroundPath(String backgroundPath) {
    DialoguePopup.backgroundPath = backgroundPath;
  }

  public static void setNpcFramePath(String npcFramePath) {
    DialoguePopup.npcFramePath = npcFramePath;
  }

}
