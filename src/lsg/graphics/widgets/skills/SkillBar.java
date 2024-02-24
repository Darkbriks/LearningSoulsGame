package lsg.graphics.widgets.skills;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.util.LinkedHashMap;
import java.util.Objects;

public class SkillBar extends HBox
{
    private static final LinkedHashMap<KeyCode, String> DEFAULT_BINDING = new LinkedHashMap<>();
    static
    {
        DEFAULT_BINDING.put(KeyCode.DIGIT1, "&");
        DEFAULT_BINDING.put(KeyCode.DIGIT2, "é");
        DEFAULT_BINDING.put(KeyCode.DIGIT3, "\"");
        DEFAULT_BINDING.put(KeyCode.DIGIT4, "'");
        DEFAULT_BINDING.put(KeyCode.DIGIT5, "(");
    }

    private final ConsumableTrigger consumableTrigger = new ConsumableTrigger(KeyCode.C, "c", null, null);

    private SkillTrigger[] triggers;

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
            triggers[i] = new SkillTrigger(keyCode, DEFAULT_BINDING.get(keyCode), null, null, null);
            this.getChildren().add(triggers[i]);
            i++;
        }

        // Ajout d'un rectangle transparent pour inserer un espace
        Rectangle spacer = new Rectangle(0, 0, 100, 50);
        spacer.setOpacity(0);
        this.getChildren().add(spacer);

        // Ajout du trigger de consommable
        this.getChildren().add(consumableTrigger);
    }

    public ConsumableTrigger getConsumableTrigger() { return consumableTrigger; }

    public SkillTrigger getTrigger(int i) { return triggers[i]; }

    public void process(KeyCode code)
    {
        if (disabledProperty().get()) { return; }

        if (code == consumableTrigger.getKeyCode())
        {
            consumableTrigger.trigger();
            return;
        }

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