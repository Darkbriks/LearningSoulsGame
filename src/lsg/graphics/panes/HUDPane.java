package lsg.graphics.panes;

import javafx.scene.layout.BorderPane;
import lsg.graphics.widgets.characters.statbars.Statbar;
import lsg.graphics.widgets.skills.SkillBar;

public class HUDPane extends BorderPane
{
    private MessagePane messagePane;
    private Statbar heroStatbar;
    private Statbar monsterStatbar;
    private BorderPane topPane;
    private SkillBar skillBar;

    public MessagePane getMessagePane() { return messagePane; }
    public Statbar getHeroStatbar() { return heroStatbar; }
    public Statbar getMonsterStatbar() { return monsterStatbar; }
    public SkillBar getSkillBar() { return skillBar; }

    public HUDPane()
    {
        buildCenter();
        buildTop();
        buildBottom();
    }

    private void buildCenter()
    {
        messagePane = new MessagePane();
        this.setCenter(messagePane);

        // Debug : affichage de la bordure bleue
        //this.setStyle("-fx-border-color: blue");

    }

    private void buildTop()
    {
        topPane = new BorderPane();
        this.setTop(topPane);
        this.heroStatbar = new Statbar();
        this.monsterStatbar = new Statbar();

        topPane.setLeft(heroStatbar);
        topPane.setRight(monsterStatbar);

        // Debug : affichage de la bordure verte
        //topPane.setStyle("-fx-border-color: green");
    }

    private void buildBottom()
    {
        skillBar = new SkillBar();
        this.setBottom(skillBar);

        /*skillBar.setMaxWidth(350);
        skillBar.setPrefWidth(350);
        skillBar.setMinWidth(350);

        skillBar.setMaxHeight(75);
        skillBar.setPrefHeight(75);
        skillBar.setMinHeight(75);

        // Laisser un espace de 50 pixels entre le bas de la fenêtre et la barre de compétences
        this.setMargin(skillBar, new javafx.geometry.Insets(50, 0, 0, 0));

        // Debug : affichage de la bordure rouge
        skillBar.setStyle("-fx-border-color: red");*/
    }
}