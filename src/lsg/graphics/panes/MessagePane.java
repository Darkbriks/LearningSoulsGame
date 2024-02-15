package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lsg.graphics.widgets.texts.GameLabel;
import lsg.utils.Constants;

public class MessagePane extends VBox
{
    private GameLabel label;

    public MessagePane()
    {
        label = new GameLabel();
        this.getChildren().add(label);

        // Center the label
        label.translateXProperty().bind(this.widthProperty().subtract(label.widthProperty()).divide(2));
        label.translateYProperty().bind(this.heightProperty().subtract(label.heightProperty()).divide(2));
    }

    public void showMessage(String msg, int cycleCount, EventHandler<ActionEvent> finishedEvent)
    {
        // Reset
        this.setTranslateY(0);
        this.setOpacity(0);
        this.setVisible(true);
        this.setDisable(false);
        this.setHeight(this.getHeight()*0.5);
        this.setWidth(this.getWidth()*0.5);

        label.setText(msg);
        animIn(event -> animIdle(cycleCount, event1 -> animOut(finishedEvent)));
    }

    public void animIn(EventHandler<ActionEvent> finishedHandler)
    {
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setToValue(1);

        ScaleTransition st = new ScaleTransition(Duration.millis(1000));
        st.setToX(1.25);
        st.setToY(1.25);

        ParallelTransition pt = new ParallelTransition(ft, st);
        pt.setNode(this);
        pt.setCycleCount(1);
        pt.setOnFinished(finishedHandler);
        pt.play();
    }

    public void animIdle(int cycleCount, EventHandler<ActionEvent> finishedHandler)
    {
        ScaleTransition st = new ScaleTransition(Duration.millis(500));
        st.setToX(1);
        st.setToY(1);
        st.setCycleCount(cycleCount);
        st.setAutoReverse(true);
        st.setNode(this);
        st.setOnFinished(finishedHandler);
        st.play();
    }

    public void animOut(EventHandler<ActionEvent> finishedHandler)
    {
        TranslateTransition tt = new TranslateTransition(Duration.millis(3500));
        tt.setToY(this.getLayoutY() - 300);

        FadeTransition ft = new FadeTransition(Duration.millis(2500));
        ft.setToValue(0);

        ParallelTransition pt = new ParallelTransition(tt, ft);
        pt.setNode(this);
        pt.setCycleCount(1);
        pt.setOnFinished(finishedHandler);
        pt.play();
    }
}