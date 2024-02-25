package lsg.graphics;

import javafx.scene.image.Image;
import lsg_api.consumables.ICollectible;

public class CollectibleFactory
{
    /**
     * Méthode qui renvoie l'image associée a un Collectible
     * @param ICollectible : le collectible pour lequel l'image est demandée
     * @return l'image représentant le collectible
     */
    public static Image getImageFor(ICollectible ICollectible)
    {
        // implémentez la méthode en ne prenant (pour l’instant) en compte
        // que les Collectible du type SmallStamPotion et SuperBerry.
        // (Retourner null si c’est un autre type)
        // (Utilisez ImageFactory)
        switch (ICollectible.getClass().getSimpleName())
        {
            case "SmallStamPotion":
                return ImageFactory.getSprites(ImageFactory.SPRITES_ID.SMALL_STAM_POTION)[0];
            case "SuperBerry":
                return ImageFactory.getSprites(ImageFactory.SPRITES_ID.SUPER_BERRY)[0];
            default:
                return null;
        }
    }
}