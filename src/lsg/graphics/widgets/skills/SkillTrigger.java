package lsg.graphics.widgets.skills;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lsg.graphics.CSSFactory;

public class SkillTrigger extends AnchorPane
{
    private ImageView view;
    private Label text;
    private KeyCode keyCode;
    private SkillAction action = null;
    private ColorAdjust desaturate;

    public SkillTrigger(KeyCode keyCode, String text, Image image, SkillAction action)
    {
        this.view = new ImageView(image);
        this.text = new Label(text);
        this.keyCode = keyCode;
        this.action = action;
        this.desaturate = new ColorAdjust();
        this.desaturate.setSaturation(-1);
        this.desaturate.setBrightness(0.6);

        buildUI();
        addListeners();
    }

    public Image getImage() { return this.view.getImage(); }
    public Label getText() { return this.text; }
    public KeyCode getKeyCode() { return this.keyCode; }
    public SkillAction getAction() { return this.action; }

    public void setImage(Image image) { this.view.setImage(image); }
    public void setText(Label text) { this.text = text; }
    public void setKeyCode(KeyCode keyCode) { this.keyCode = keyCode; }
    public void setAction(SkillAction action) { this.action = action; }

    private void buildUI()
    {
        this.getStylesheets().add(CSSFactory.getStyleSheet("SkillTrigger.css"));
        this.getStyleClass().add("skill");
        this.text.getStyleClass().add("skill-text");

        this.view.setFitWidth(50); this.view.setFitHeight(50);

        this.getChildren().add(view);
        this.getChildren().add(text);

        this.setPrefSize(50, 50);
        this.setMinSize(50, 50);
        this.setMaxSize(50, 50);

        this.text.setMinSize(50, 50);
        this.text.setMaxSize(50, 50);
        this.text.setPrefSize(50, 50);
        this.text.setStyle("-fx-alignment: center;");
    }

    public void trigger()
    {
        if (disabledProperty().get()) { return; }
        animate();
        action.execute();
    }

    private void addListeners()
    {
        this.setOnMouseClicked(event -> trigger());

        disabledProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { this.setEffect(desaturate); }
            else { this.setEffect(null); }
        });
    }

    private void animate()
    {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), this);
        st.setToX(1.3); st.setToY(1.3);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }
}