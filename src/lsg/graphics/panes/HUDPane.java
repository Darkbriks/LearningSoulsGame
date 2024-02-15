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

    }

    private void buildTop()
    {
        topPane = new BorderPane();
        this.setTop(topPane);
        this.heroStatbar = new Statbar();
        this.monsterStatbar = new Statbar();

        topPane.setLeft(heroStatbar);
        topPane.setRight(monsterStatbar);
    }

    private void buildBottom()
    {
        skillBar = new SkillBar();
        this.setBottom(skillBar);
    }
}