package lsg.graphics.widgets.skills;

import javafx.scene.input.KeyCode;
import lsg.consumables.Consumable;
import lsg.graphics.CSSFactory;
import lsg.graphics.CollectibleFactory;

public class ConsumableTrigger extends SkillTrigger
{
    private Consumable consumable;

    public ConsumableTrigger(KeyCode keyCode, String text, Consumable consumable, SkillAction action)
    {
        super(keyCode, text, null, action);

        this.getStylesheets().add(CSSFactory.getStyleSheet("ConsumableTrigger.css"));
        this.getStyleClass().add("consumable");

        this.setConsumable(consumable);
    }

    /**
     * Permet d'associer un consommable au trigger
     * @param consumable : le consommable
     */
    public void setConsumable(Consumable consumable)
    {
        this.consumable = consumable;
        if (consumable != null)
        {
            this.setImage(CollectibleFactory.getImageFor(consumable));
            this.consumable.isEmpty().addListener((obs, oldV, newV) -> this.setDisable(newV));
        }
    }
}
