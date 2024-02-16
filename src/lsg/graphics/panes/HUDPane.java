package lsg.graphics.panes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.BorderPane;
import lsg.graphics.widgets.characters.statbars.Statbar;
import lsg.graphics.widgets.skills.SkillBar;
import lsg.graphics.widgets.texts.GameLabel;

public class HUDPane extends BorderPane
{
    private MessagePane messagePane;
    private Statbar heroStatbar;
    private Statbar monsterStatbar;
    private BorderPane topPane;
    private SkillBar skillBar;
    private IntegerProperty score = new SimpleIntegerProperty();
    private GameLabel scoreLabel;

    public MessagePane getMessagePane() { return messagePane; }
    public Statbar getHeroStatbar() { return heroStatbar; }
    public Statbar getMonsterStatbar() { return monsterStatbar; }
    public SkillBar getSkillBar() { return skillBar; }
    public IntegerProperty scoreProperty() { return score; }

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
    }

    private void buildTop()
    {
        topPane = new BorderPane();
        this.setTop(topPane);
        this.heroStatbar = new Statbar();
        this.monsterStatbar = new Statbar();

        topPane.setLeft(heroStatbar);
        topPane.setRight(monsterStatbar);
        monsterStatbar.flip();

        // Dans buildTop(), instanciez et ajoutez scoreLabel au centre avec:
        // texte de départ: «0»
        // un scalingde 1.3
        // une translation sur l’axe Y de 40px
        scoreLabel = new GameLabel("0");
        scoreLabel.setScaleX(1.3);
        scoreLabel.setScaleY(1.3);
        scoreLabel.setTranslateY(75);
        topPane.setCenter(scoreLabel);

        // ajoutez un listener sur score pour  faire  en  sorte  que  les modifications de score soient répercutées sur le texte affiché dans scoreLabel.
        score.addListener((o, oldVal, newVal) -> scoreLabel.setText(newVal.toString()));
    }

    private void buildBottom()
    {
        skillBar = new SkillBar();
        this.setBottom(skillBar);
    }
}