package lsg.graphics.widgets.texts;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class GameLabel extends Label
{
    public GameLabel(String text, Node graphic)
    {
        super(text, graphic);
        this.getStyleClass().addAll("game-font", "game-font-fx");
    }

    public GameLabel(String text) { this(text, null); }

    public GameLabel() { this(null, null); }
}
