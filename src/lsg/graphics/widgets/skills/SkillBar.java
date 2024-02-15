package lsg.graphics.widgets.skills;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import java.util.LinkedHashMap;
import java.util.Objects;

public class SkillBar extends HBox
{
    private static LinkedHashMap<KeyCode, String> DEFAULT_BINDING = new LinkedHashMap<>();
    static
    {
        DEFAULT_BINDING.put(KeyCode.DIGIT1, "&");
        DEFAULT_BINDING.put(KeyCode.DIGIT2, "é");
        DEFAULT_BINDING.put(KeyCode.DIGIT3, "\"");
        DEFAULT_BINDING.put(KeyCode.DIGIT4, "'");
        DEFAULT_BINDING.put(KeyCode.DIGIT5, "(");
    }

    private SkillTrigger[] triggers;
    private boolean enabled = true;

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public SkillBar()
    {
        this.setSpacing(10);
        this.setPrefHeight(110);

        // Centre les éléments dans la barre
        this.setStyle("-fx-alignment: center;");

        init();
    }

    private void init()
    {
        triggers = new SkillTrigger[DEFAULT_BINDING.size()];
        int i = 0;
        for (KeyCode keyCode : DEFAULT_BINDING.keySet())
        {
            triggers[i] = new SkillTrigger(keyCode, DEFAULT_BINDING.get(keyCode), null, null);
            this.getChildren().add(triggers[i]);
            i++;
        }
    }

    public SkillTrigger getTrigger(int i) { return triggers[i]; }

    public void process(KeyCode code)
    {
        if (!enabled) return;
        for (SkillTrigger trigger : triggers)
        {
            if (Objects.equals(trigger.getKeyCode(), code))
            {
                trigger.trigger();
                break;
            }
        }
    }
}