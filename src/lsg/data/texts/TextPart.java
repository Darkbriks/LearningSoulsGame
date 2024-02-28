package lsg.data.texts;

import lsg.characters.Hero;
import lsg.utils.Constants;
import lsg.weapons.Sword;
import lsg.weapons.Weapon;
import lsg_api.characters.ICharacter;
import lsg_api.characters.IHero;
import lsg_api.characters.IMonster;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Cette classe représente une partie de texte qui peut être soit une chaîne de caractères, soit une méthode à exécuter.
 */
public class TextPart
{
    /**
     * Enumération définissant les types de parties de texte possibles.
     * @see TEXT_PART_TYPE#STRING
     * @see TEXT_PART_TYPE#METHOD
     * @see TextPart
     */
    public enum TEXT_PART_TYPE { STRING, METHOD; }

    private final TEXT_PART_TYPE type;
    private final String textPart;
    private final Object instance;

    /**
     * Constructeur avec type, partie de texte et instance.
     *
     * @param type     Le type de la partie de texte.
     * @param textPart La partie de texte.
     * @param instance L'instance associée à la partie de texte.
     */
    public TextPart(TEXT_PART_TYPE type, String textPart, Object instance)
    {
        this.type = type;
        this.textPart = textPart;
        this.instance = instance;
    }

    /**
     * Constructeur avec partie de texte et instance. Utilisé pour créer directement une méthode à exécuter.
     *
     * @param textPart La partie de texte.
     * @param instance L'instance associée à la partie de texte.
     */
    public TextPart(String textPart, Object instance) { this(TEXT_PART_TYPE.METHOD, textPart, instance); }

    /**
     * Constructeur avec partie de texte. Utilisé pour créer directement une chaîne de caractères.
     *
     * @param textPart La partie de texte.
     */
    public TextPart(String textPart) { this(TEXT_PART_TYPE.STRING, textPart, null); }

    /**
     * Constructeur avec instance. Utilisé pour créer directement une instance de toString.
     *
     * @param instance L'instance associée à la partie de texte.
     */
    public TextPart(Object instance) { this(TEXT_PART_TYPE.METHOD, "toString", instance); }

    /**
     * Méthode permettant de récupérer la partie de texte.
     *
     * @return Le texte (chaîne de caractères ou résultat de la méthode).
     */
    public String get()
    {
        if (type == TEXT_PART_TYPE.METHOD)
        {
            try
            {
                if (textPart.contains("."))
                {
                    String[] parts = textPart.split("\\.");
                    Object objects = instance;

                    for (String part : parts)
                    {
                        if (part.equals("THIS")) { objects = this; continue; }
                        if (part.equals("HERO")) { objects = getHero(); continue; }
                        if (part.equals("MONSTER")) { objects = getMonster(); continue; }
                        if (part.contains("CHARACTER")) { objects = getCharacter(part.split("~")[1]); continue; }
                        if (part.contains("~")) { objects = executeWithArgs(part, objects); continue; }

                        Method method = objects.getClass().getMethod(part);
                        objects = method.invoke(objects);
                    }
                    return objects.toString();
                }
                else
                {
                    Method method = instance.getClass().getMethod(textPart);
                    return method.invoke(instance).toString();
                }
            }
            catch (Exception e) { System.err.println("Error in TextPart.get() : " + e.getClass().getSimpleName() + " - " + e.getMessage()); return null; }
        }
        else { return getString(); }
    }

    /**
     * Méthode permettant de récupérer la partie de texte en chaîne de caractères.
     *
     * @return La partie de texte.
     */
    private String getString()
    {
        String result = textPart;
        if (result.contains("~ENTER~")) { result = result.replace("~ENTER~", "\n"); }
        if (result.contains("~APOSTROPHE~")) { result = result.replace("~APOSTROPHE~", "'"); }
        if (result.contains("~QUOTE~")) { result = result.replace("~QUOTE~", "\""); }
        if (result.contains("~BULLET~")) { result = result.replace("~BULLET~", Constants.BULLET_POINT); }
        if (result.contains("~HERO~")) { result = result.replace("~HERO~", getHero().getName()); }
        if (result.contains("~MONSTER~")) { result = result.replace("~MONSTER~", getMonster().getName()); }
        return result;
    }

    /**
     * Méthode permettant d'exécuter une méthode avec des arguments.
     *
     * @param part    La partie de texte.
     * @param objects L'instance associée à la partie de texte.
     * @return Le résultat de la méthode.
     * @throws NoSuchMethodException     Si la méthode n'existe pas.
     * @throws IllegalAccessException    Si l'accès à la méthode est interdit.
     * @throws InvocationTargetException Si la méthode ne peut pas être invoquée.
     */
    private static Object executeWithArgs(String part, Object objects) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        // TODO: Ajouter des protections pour empêcher l'exécution de code malveillant.
        String[] split = part.split("~");
        Object[] parameters = new Object[(split.length - 1) / 2];
        Class<?>[] parameterTypes = new Class<?>[(split.length - 1) / 2];

        for (int i = 1; i < split.length; i += 2)
        {
            String parameter = split[i];
            String type = split[i + 1];
            switch (type) {
                case "INT":
                    parameters[(i - 1) / 2] = Integer.parseInt(parameter);
                    parameterTypes = new Class<?>[]{int.class};
                    break;
                case "FLOAT":
                    parameters[(i - 1) / 2] = Float.parseFloat(parameter);
                    parameterTypes = new Class<?>[]{float.class};
                    break;
                case "DOUBLE":
                    parameters[(i - 1) / 2] = Double.parseDouble(parameter);
                    parameterTypes = new Class<?>[]{double.class};
                    break;
                case "BOOLEAN":
                    parameters[(i - 1) / 2] = Boolean.parseBoolean(parameter);
                    parameterTypes = new Class<?>[]{boolean.class};
                    break;
                default:
                    parameters[(i - 1) / 2] = parameter;
                    parameterTypes = new Class<?>[]{String.class};
                    break;
            }
        }

        Method method = objects.getClass().getMethod(split[0], parameterTypes);
        return method.invoke(objects, parameters);
    }

    /**
     * Méthode permettant de récupérer le héros. Uniquement déstinée à être utilisée dans la classe pour le mot clé "HERO".
     *
     * @return Le héros.
     */
    public IHero getHero() { return ICharacter.getHero(); }

    /**
     * Méthode permettant de récupérer le monstre. Uniquement déstinée à être utilisée dans la classe pour le mot clé "MONSTER".
     *
     * @return Le monstre.
     */
    public IMonster getMonster() { return ICharacter.getMonster(); }

    /**
     * Méthode permettant de récupérer un personnage. Uniquement déstinée à être utilisée dans la classe pour le mot clé "CHARACTER".
     *
     * @param name Le nom du personnage.
     * @return Le personnage.
     */
    public ICharacter getCharacter(String name) { return ICharacter.getCharacter(name); }

    /**
     * Méthode permettant de convertir la partie de texte en XML.
     *
     * @implNote La conversion en XML ne prend pas en compte l'instance associée, et la définie comme "null".
     * @return La partie de texte en XML.
     */
    public Element toXML(Document doc)
    {
        Element root = doc.createElement("textPart");
        root.setAttribute("instance", "null");
        root.setAttribute("method", textPart);
        root.setAttribute("type", type.toString());
        return root;
    }

    /**
     * Méthode permettant de convertir une partie de texte en XML en une instance de TextPart.
     *
     * @param node Le noeud XML.
     * @return La partie de texte.
     * @implNote La méthode ne prend pas en compte l'instance associée, et la définie comme "null".
     */
    public static TextPart fromXML(Node node)
    {
        String type = node.getAttributes().getNamedItem("type").getNodeValue();
        String method = node.getAttributes().getNamedItem("method").getNodeValue();
        return new TextPart(TEXT_PART_TYPE.valueOf(type), method, null);
    }

     /**
     * Méthode de test.
     */
    private static void test()
    {
        TextPart textPart = new TextPart("THIS.getHero.getName", null);
        System.out.println(textPart.get());

        textPart = new TextPart("HERO.getWeapon.getName", null);
        System.out.println(textPart.get());

        textPart = new TextPart("CHARACTER~Gregooninator.getName", null);
        System.out.println(textPart.get());

        textPart = new TextPart("HERO.getHitWith~10~INT.HERO.getLife", null);
        System.out.println(textPart.get());

        //String xml = textPart.toXML();
        //System.out.println(xml);

        //textPart = TextPart.fromXML(xml);
        System.out.println(textPart.get());
    }

    public static void main(String[] args) {
        IHero hero = new Hero("Gregooninator");
        Weapon weapon = new Sword();
        hero.setWeapon(weapon);
        ICharacter.setHero(hero);

        test();
    }
}