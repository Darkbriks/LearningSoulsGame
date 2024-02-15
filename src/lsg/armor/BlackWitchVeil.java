package lsg.armor;

/**
 * Classe BlackWitchVeil
 * La classe BlackWitchVeil représente un objet d'armure collectible dans le jeu.
 * Elle hérite de la classe ArmorItem.
 * Cette pièce d'armure est inspirée de la série de jeux vidéo Dark Souls.
 * La Black Witch Veil est un voile noir qui protège le visage du joueur.
 * Elle est très légère et offre une bonne protection contre les dégâts magiques.
 * Lien vers le <a href="https://darksouls.fandom.com/wiki/Black_Witch_Veil">wiki</a> de Dark Souls pour plus d'informations sur la Black Witch Veil.
 * @see ArmorItem
 */
public class BlackWitchVeil extends ArmorItem
{
    /**
     * Crée une nouvelle Black Witch Veil.
     * Elle a pour nom "Black Witch Veil" et pour valeur d'armure 4.6.
     * @see ArmorItem
     */
    public BlackWitchVeil()
    {
        super("Black Witch Veil", 4.6f);
    }
}
