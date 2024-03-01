package lsg.graphics.widgets.skills;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lsg.data.XMLFactory;
import lsg.data.XMLFactory.TEXTE_ID;
import lsg.graphics.CSSFactory;
import lsg.utils.Constants;

public class SkillTrigger extends AnchorPane
{
    private final ImageView view;
    private Label text;
    private Tooltip tooltip;
    private TEXTE_ID tooltipID;
    private KeyCode keyCode;
    private SkillAction action = null;
    private final ColorAdjust desaturate;

    public SkillTrigger(KeyCode keyCode, String text, String tooltip, Image image, SkillAction action)
    {
        this.view = new ImageView(image);
        this.text = new Label(text);
        this.tooltip = new Tooltip(tooltip);
        this.tooltipID = TEXTE_ID.DEFAULT_SKILL_TOOLTIP;
        this.keyCode = keyCode;
        this.action = action;
        this.desaturate = new ColorAdjust();
        this.desaturate.setSaturation(-1);
        this.desaturate.setBrightness(0.6);

        this.tooltip.setOnShowing(event -> this.tooltip.setText(getTooltipText()));

        buildUI();
        addListeners();
    }

    public Image getImage() { return this.view.getImage(); }
    public Label getText() { return this.text; }
    public Tooltip getTooltip() { return this.tooltip; }
    public TEXTE_ID getTooltipID() { return this.tooltipID; }
    public KeyCode getKeyCode() { return this.keyCode; }
    public SkillAction getAction() { return this.action; }

    public void setImage(Image image) { this.view.setImage(image); }
    public void setText(Label text) { this.text = text; }
    public void setTooltipID(TEXTE_ID tooltipID) { this.tooltipID = tooltipID; }
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
        Tooltip.install(this, tooltip);

        this.setPrefSize(50, 50);
        this.setMinSize(50, 50);
        this.setMaxSize(50, 50);

        this.text.setMinSize(50, 50);
        this.text.setMaxSize(50, 50);
        this.text.setPrefSize(50, 50);
        this.text.setStyle("-fx-alignment: center;");

        tooltip.setWrapText(true);
    }

    public void trigger()
    {
        if (disabledProperty().get()) { return; }
        animate();
        if (action != null) { action.execute(); }
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

    private String getTooltipText() { return XMLFactory.getText(tooltipID).toString(Constants.getLang()); }
}