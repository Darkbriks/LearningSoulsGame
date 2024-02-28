package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lsg.graphics.CSSFactory;
import lsg.graphics.widgets.texts.GameLabel;
import lsg.utils.Constants;

public class CreationPane extends VBox
{
    private final TextField nameField;
    private final GameLabel nameLabel;

    public TextField getNameField() { return nameField; }

    public CreationPane()
    {
        super();
        nameLabel = new GameLabel("Player Name");
        this.getChildren().add(nameLabel);

        nameField = new TextField();
        nameField.setMaxWidth(200);
        nameField.setPromptText("Enter your name here");
        nameField.setFocusTraversable(false);
        this.getChildren().add(nameField);

        GameLabel versionLabel = new GameLabel("Version " + Constants.GAME_VERSION);
        versionLabel.setScaleX(0.25);
        versionLabel.setScaleY(0.25);
        versionLabel.setTranslateY(650);
        versionLabel.setTranslateX(935);
        this.getChildren().add(versionLabel);

        this.getStylesheets().add(CSSFactory.getStyleSheet("LSGFont.css"));
        nameLabel.getStyleClass().addAll("game-font");
        versionLabel.getStyleClass().addAll("game-font");

        this.nameLabel.translateXProperty().bind(this.widthProperty().subtract(this.nameLabel.widthProperty()).divide(2));
        this.nameField.translateXProperty().bind(this.widthProperty().subtract(this.nameField.widthProperty()).divide(2));

        this.nameLabel.setTranslateY((double) Constants.GAME_HEIGHT /2 - this.nameLabel.getHeight()*1.25);
        this.nameField.setTranslateY((double) Constants.GAME_HEIGHT /2);

        // Créer un dropdown pour les langues en haut à droite
        ObservableList<Constants.LANG> langList = FXCollections.observableArrayList(Constants.LANG.values());
        ComboBox<Constants.LANG> langComboBox = new ComboBox<>(langList);
        langComboBox.setValue(Constants.getLang());
        langComboBox.setOnAction(event -> Constants.setLang(langComboBox.getValue()));
        langComboBox.setVisibleRowCount(3);

        this.getChildren().add(langComboBox);

        langComboBox.setTranslateX((double) Constants.GAME_WIDTH - 65);
        langComboBox.setTranslateY(-155);

        this.setOpacity(0);
        this.setVisible(true);
        this.setDisable(false);
    }

    public void fadeIn(EventHandler<ActionEvent> finishedHandler)
    {
        FadeTransition ft = new FadeTransition(Constants.TITLE_ANIMATION_DURATION);
        ft.setToValue(1);
        ft.setNode(this);
        ft.setCycleCount(1);
        ft.setOnFinished(finishedHandler);
        ft.play();
    }
}
