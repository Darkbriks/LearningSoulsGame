package lsg.graphics.panes;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import lsg.graphics.CSSFactory;
import lsg.graphics.widgets.texts.GameLabel;
import lsg.utils.Constants;

public class TitlePane extends VBox
{
    private Scene scene;
    private GameLabel title;
    private static final double ZOOM_SCALE = 1.5;
    private static final double ZOOM_Y = 0.25;

    public TitlePane(Scene scene, String title)
    {
        this.scene = scene;
        this.title = new GameLabel(title);
        this.getChildren().add(this.title);
        this.scene.getStylesheets().add(CSSFactory.getStyleSheet("LSGFont.css"));
        this.title.getStyleClass().addAll("game-font");
    }

    public void zoomIn(EventHandler<ActionEvent> finishedHandler)
    {
        ScaleTransition st = new ScaleTransition(Constants.TITLE_ANIMATION_DURATION);
        st.setToX(ZOOM_SCALE);
        st.setToY(ZOOM_SCALE);

        TranslateTransition tt = new TranslateTransition(Constants.TITLE_ANIMATION_DURATION);
        tt.setToY(scene.getHeight()*ZOOM_Y);

        ParallelTransition pt = new ParallelTransition(tt, st);
        pt.setNode(this);
        pt.setCycleCount(1);
        pt.setOnFinished(finishedHandler);
        pt.play();
    }

    public void zoomOut(EventHandler<ActionEvent> finishedHandler)
    {
        ScaleTransition st = new ScaleTransition(Constants.TITLE_ANIMATION_DURATION);
        st.setToX(1);
        st.setToY(1);

        TranslateTransition tt = new TranslateTransition(Constants.TITLE_ANIMATION_DURATION);
        tt.setToY(0);

        ParallelTransition pt = new ParallelTransition(tt, st);
        pt.setNode(this);
        pt.setCycleCount(1);
        pt.setOnFinished(finishedHandler);
        pt.play();
    }
}
