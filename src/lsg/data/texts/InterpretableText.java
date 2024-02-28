package lsg.data.texts;

import lsg.characters.Hero;
import lsg.utils.Constants.LANG;
import lsg.weapons.Sword;
import lsg_api.characters.ICharacter;
import lsg_api.characters.IHero;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe permet de créer des textes interprétables, c'est-à-dire des textes qui peuvent des bouts de code
 */
public class InterpretableText
{
    private final Map<LANG, TextPart[]> sentences;

    /**
     * Constructeur de la classe InterpretableText
     * @param sentences : les phrases interprétables
     */
    public InterpretableText(Map<LANG, TextPart[]> sentences) { this.sentences = sentences; }

    /**
     * Cette méthode permet de récupérer les phrases interprétables
     * @param lang : la langue dans laquelle on veut récupérer les phrases interprétables
     * @return : la chaîne de caractères correspondant à la phrase interprétable
     */
    public String toString(LANG lang)
    {
        StringBuilder sb = new StringBuilder();
        for (TextPart part : sentences.get(lang)) { sb.append(part.get()); }
        return sb.toString();
    }

    /**
     * Cette méthode permet de récupérer les phrases interprétables
     * @return : la chaîne de caractères correspondant à la phrase interprétable en anglais
     */
    public String toString() { return toString(LANG.EN); }

    public Element toXML(Document doc)
    {
        Element root = null;
        try
        {
            root = doc.createElement("sentences");

            for (LANG lang : sentences.keySet())
            {
                Element langElement = doc.createElement(lang.toString());
                root.appendChild(langElement);
                for (TextPart part : sentences.get(lang)) { langElement.appendChild(part.toXML(doc)); }
            }
        }
        catch (Exception e) { System.err.println("Error in InterpretableText.toXML() : " + e.getClass().getSimpleName() + " - " + e.getMessage()); }
        return root;
    }

    public static InterpretableText fromXML(Node node)
    {
        Map<LANG, TextPart[]> sentences = new HashMap<>();
        try
        {
            NodeList langNodes = node.getChildNodes();
            for (int i = 0; i < langNodes.getLength(); i++)
            {
                LANG lang = LANG.fromString(langNodes.item(i).getNodeName());
                TextPart[] parts = new TextPart[langNodes.item(i).getChildNodes().getLength()];
                for (int j = 0; j < langNodes.item(i).getChildNodes().getLength(); j++) { parts[j] = TextPart.fromXML(langNodes.item(i).getChildNodes().item(j)); }
                sentences.put(lang, parts);
            }
        }
        catch (Exception e) { System.err.println("Error in InterpretableText.fromXML() : " + e.getClass().getSimpleName() + " - " + e.getMessage()); }
        return new InterpretableText(sentences);
    }

    public static void main(String[] args)
    {
        Sword sword = new Sword();
        IHero hero = new Hero();
        hero.setWeapon(sword);
        ICharacter.setHero(hero);

        InterpretableText text = new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[]
                    {
                        new TextPart("Hello, World! ~QUOTE~"),
                        new TextPart("HERO.getWeapon.getName", null),
                        new TextPart("~QUOTE~ inflicted between "),
                        new TextPart("HERO.getWeapon.getMinDamage", null),
                        new TextPart(" and "),
                        new TextPart("HERO.getWeapon.getMaxDamage", null),
                        new TextPart(" damage.~ENTER~"),
                    });
                    put(LANG.FR, new TextPart[]
                    {
                        new TextPart("Bonjour, Monde! ~QUOTE~"),
                        new TextPart("HERO.getWeapon.getName", null),
                        new TextPart("~QUOTE~ inflige entre "),
                        new TextPart("HERO.getWeapon.getMinDamage", null),
                        new TextPart(" et "),
                        new TextPart("HERO.getWeapon.getMaxDamage", null),
                        new TextPart(" dégâts.~ENTER~"),
                    });
                    put(LANG.DE, new TextPart[]
                    {
                        new TextPart("Hallo, Welt! ~QUOTE~"),
                        new TextPart("HERO.getWeapon.getName", null),
                        new TextPart("~QUOTE~ verursacht zwischen "),
                        new TextPart("HERO.getWeapon.getMinDamage", null),
                        new TextPart(" und "),
                        new TextPart("HERO.getWeapon.getMaxDamage", null),
                        new TextPart(" Schaden.~ENTER~"),
                    });
                    put(LANG.ES, new TextPart[]
                    {
                        new TextPart("¡Hola, Mundo! ~QUOTE~"),
                        new TextPart("HERO.getWeapon.getName", null),
                        new TextPart("~QUOTE~ inflige entre "),
                        new TextPart("HERO.getWeapon.getMinDamage", null),
                        new TextPart(" y "),
                        new TextPart("HERO.getWeapon.getMaxDamage", null),
                        new TextPart(" daño.~ENTER~"),
                    });
                }}
        );

        System.out.println(text);
        System.out.println(text.toString(LANG.FR));
        System.out.println(text.toString(LANG.DE));
        System.out.println(text.toString(LANG.ES));
    }
}