package lsg.data;

import lsg.data.texts.InterpretableText;
import lsg.data.texts.TextPart;
import lsg.utils.Constants;
import lsg.utils.Constants.LANG;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.util.HashMap;
import java.util.Objects;

public class XMLFactory
{
    public static enum TEXTE_ID
    {
        TEMPLATE                        ("xml/template.xml"),

        ////////// CREATION PANE //////////
        CREATION_NAME_TITLE             ("xml/creationPane.xml"),
        CREATION_NAME_PLACEHOLDER        ("xml/creationPane.xml"),

        ////////// EXCEPTIONS //////////
        BAG_FULL                        ("xml/exceptions.xml"),
        CONSUME_EMPTY                   ("xml/exceptions.xml"),
        CONSUME_NULL                    ("xml/exceptions.xml"),
        CONSUME_REPAIR_NULL_WEAPON      ("xml/exceptions.xml"),
        NO_BAG                          ("xml/exceptions.xml"),
        STAMINA_EMPTY                   ("xml/exceptions.xml"),
        WEAPON_BROKEN                   ("xml/exceptions.xml"),
        WEAPON_NULL                     ("xml/exceptions.xml"),

        ////////// SKILL TOOLTIPS //////////
        DEFAULT_SKILL_TOOLTIP            ("xml/skillTooltips.xml"),
        SKILL_TOOLTIP_1                  ("xml/skillTooltips.xml"), // Basic attack
        SKILL_TOOLTIP_2                  ("xml/skillTooltips.xml"), // Recuperate
        SKILL_TOOLTIP_CONSUME            ("xml/skillTooltips.xml"), // Consume
        ;

        private final String path ;
        TEXTE_ID(String path) { this.path = path ; }
    }

    private static final HashMap<TEXTE_ID, InterpretableText> textes = new HashMap<>() ;

    public static void preloadAll(Runnable finishedHandler) {
        new Thread(() -> {
            try {
                for (TEXTE_ID id : TEXTE_ID.values()) { loadXML(id); }
                if (finishedHandler != null) { finishedHandler.run(); }
            }
            catch (Exception e) { System.err.println("Error in XMLFactory.preloadAll() : " + e.getClass().getSimpleName() + " - " + e.getMessage()); }
        }).start();
    }

    public static InterpretableText getText(TEXTE_ID id)
    {
        InterpretableText interpretableText = textes.get(id);
        if (interpretableText == null)
        {
            interpretableText = loadXML(id);
        }
        return interpretableText;
    }

    public static void createXML(TEXTE_ID[] ids, InterpretableText[] textes)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element root = doc.createElement("textes");
            doc.appendChild(root);
            for (int i = 0; i < ids.length; i++) { root.appendChild(toXML(ids[i], textes[i], doc)); }

            String path = Objects.requireNonNull(XMLFactory.class.getResource(ids[0].path)).toString();
            if (Constants.IS_JAR) { path = Constants.JAR_PATH + ids[0].path; }
            System.out.println(path);

            writeXML(path, doc);
        }
        catch (Exception e) { System.err.println("Error in XMLFactory.createXML() : " + e.getClass().getSimpleName() + " - " + e.getMessage()); }
    }

    public static Element toXML(TEXTE_ID id, InterpretableText texte, Document doc)
    {
        Element root = doc.createElement("texte");
        root.setAttribute("id", id.name());
        root.appendChild(texte.toXML(doc));
        return root;
    }

    private static void writeXML(String path, Document doc) throws TransformerException
    {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        DOMSource source = new DOMSource(doc);
        t.transform(source, new javax.xml.transform.stream.StreamResult(path));
    }

    public static InterpretableText loadXML(TEXTE_ID id)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(Objects.requireNonNull(Objects.requireNonNull(XMLFactory.class.getResource(id.path)).toString()));
            Element root = doc.getDocumentElement();
            InterpretableText texte = null;

            NodeList nodes = root.getElementsByTagName("texte");
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Element texteElement = (Element) nodes.item(i);
                if (texteElement.getAttribute("id").equals(id.name()))
                {
                    texte = InterpretableText.fromXML(texteElement.getFirstChild());
                    break;
                }
            }

            textes.put(id, texte);
            return texte;
        }
        catch (Exception e) { System.err.println("Error in XMLFactory.loadXML() : " + e.getClass().getSimpleName() + " - " + e.getMessage()); return null; }
    }

    public static void createXMLFiles()
    {
        new XMLTemplateFile().CreateXML();
        new XMLCreationPane().CreateXML();
        new XMLExceptionFile().CreateXML();
        new XMLSkillTooltipFile().CreateXML();
    }

    public static void main(String[] args) { createXMLFiles(); }
}

class XMLTemplateFile {
    void CreateXML()
    {
        InterpretableText text = new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "This is a template.", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Ceci est un modèle.", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Dies ist eine Vorlage.", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Esto es una plantilla.", null) });
                }}
        );

        XMLFactory.createXML(new XMLFactory.TEXTE_ID[] { XMLFactory.TEXTE_ID.TEMPLATE }, new InterpretableText[] { text });
    }
}

class XMLCreationPane
{
    void CreateXML()
    {
        InterpretableText[] texts = new InterpretableText[]
        {
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "PLAYER NAME", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "NOM DE JOUEUR", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "SPIELERNAME", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "NOMBRE DEL JUGADOR", null) });
                }}
            ),
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Enter your name here", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Entrez votre nom ici", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Geben sie hier ihren Namen ein", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Introduzca su nombre aquì", null) });
                }}
            ),
        };

        XMLFactory.TEXTE_ID[] ids = new XMLFactory.TEXTE_ID[]
        {
            XMLFactory.TEXTE_ID.CREATION_NAME_TITLE,
            XMLFactory.TEXTE_ID.CREATION_NAME_PLACEHOLDER,
        };
        XMLFactory.createXML(ids, texts);
    }
}

class XMLExceptionFile {
    void CreateXML()
    {
        InterpretableText[] texts = new InterpretableText[]
        {
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " is full !", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " est plein !", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " ist voll !", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " está lleno !", null) });
                }}
            ),
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " has no more charges !", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " n'a plus de charges !", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " hat keine Ladungen mehr !", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " ya no tiene cargas !", null) });
                }}
            ),
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Consumable is null !", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Le consommable est nul !", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Verbrauchsmaterial ist null !", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "El consumible es nulo !", null) });
                }}
            ),
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Weapon is null !", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "L'arme est nulle !", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Waffe ist null !", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "El arma es nula !", null) });
                }}
            ),
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "No bag has been equipped !", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Aucun sac n'a été équipé !", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Keine Tasche wurde ausgerüstet !", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "No se ha equipado ninguna bolsa !", null) });
                }}
            ),
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Stamina is empty !", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "La stamina est vide !", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Die Ausdauer ist leer !", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "La resistencia está vacía !", null) });
                }}
            ),
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " is broken !", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " est cassée !", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " ist kaputt !", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, " está rota !", null) });
                }}
            ),
            new InterpretableText(
                new HashMap<LANG, TextPart[]>()
                {{
                    put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Weapon is null !", null) });
                    put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "L'arme est nulle !", null) });
                    put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Waffe ist null !", null) });
                    put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "El arma es nula !", null) });
                }}
            ),
        };

        XMLFactory.TEXTE_ID[] ids = new XMLFactory.TEXTE_ID[]
        {
            XMLFactory.TEXTE_ID.BAG_FULL,
            XMLFactory.TEXTE_ID.CONSUME_EMPTY,
            XMLFactory.TEXTE_ID.CONSUME_NULL,
            XMLFactory.TEXTE_ID.CONSUME_REPAIR_NULL_WEAPON,
            XMLFactory.TEXTE_ID.NO_BAG,
            XMLFactory.TEXTE_ID.STAMINA_EMPTY,
            XMLFactory.TEXTE_ID.WEAPON_BROKEN,
            XMLFactory.TEXTE_ID.WEAPON_NULL,
        };
        XMLFactory.createXML(ids, texts);
    }
}

class XMLSkillTooltipFile {
    void CreateXML()
    {
        InterpretableText[] texts = new InterpretableText[]
            {
                new InterpretableText(
                        new HashMap<LANG, TextPart[]>()
                        {{
                            put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "This is a default skill tooltip.", null) });
                            put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Ceci est une info-bulle de compétence par défaut.", null) });
                            put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Dies ist ein Standard-Skill-Tooltip.", null) });
                            put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Esta es una información sobre la habilidad por defecto.", null) });
                        }}
                ),
                new InterpretableText(
                    new HashMap<LANG, TextPart[]>()
                    {{
                        put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Attack the ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "MONSTER.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " with your ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " for ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getMinDamage", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " to ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getMaxDamage", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " damage.~ENTER~Costs ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getStamCost", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " stamina points.", null)
                        });
                        put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Attaque le ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "MONSTER.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " avec ton ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " pour ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getMinDamage", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " à ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getMaxDamage", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " dégâts.~ENTER~Coûte ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getStamCost", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " points de stamina.", null)
                        });
                        put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Greift das ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "MONSTER.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " mit deiner ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " für ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getMinDamage", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " bis ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getMaxDamage", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " Schaden an.~ENTER~Kostet ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getStamCost", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " Ausdauerpunkte.", null)
                        });
                        put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Ataca al ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "MONSTER.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " con tu ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " para ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getMinDamage", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " a ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getMaxDamage", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " daño.~ENTER~Cuesta ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getWeapon.getStamCost", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " puntos de resistencia.", null)
                        });
                    }}
                ),
                new InterpretableText(
                    new HashMap<LANG, TextPart[]>()
                    {{
                        put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Recuperates ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getLifeRegen", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " life points and ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getStamRegen", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " stamina points.", null)
                        });
                        put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Récupère ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getLifeRegen", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " points de vie et ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getStamRegen", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " points de stamina.", null)
                        });
                        put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Erholt ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getLifeRegen", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " Lebenspunkte und ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getStamRegen", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " Ausdauerpunkte.", null)
                        });
                        put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Recupera ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getLifeRegen", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " puntos de vida y ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "HERO.getStamRegen", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " puntos de resistencia.", null)
                        });
                    }}
                ),
                new InterpretableText(
                    new HashMap<LANG, TextPart[]>()
                    {{
                        put(LANG.EN, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Consume ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " and recover ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getCapacity", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " point of ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getStat", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, ".", null)
                        });
                        put(LANG.FR, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Consomme ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " et récupère ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getCapacity", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " point de ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getStat", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, ".", null)
                        });
                        put(LANG.DE, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Verbraucht ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " und erholt ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getCapacity", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " Punkt ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getStat", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, ".", null)
                        });
                        put(LANG.ES, new TextPart[] { new TextPart(TextPart.TEXT_PART_TYPE.STRING, "Consume ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getName", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " y recupera ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getCapacity", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, " punto de ", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.METHOD, "CONSUMABLE.getStat", null),
                                new TextPart(TextPart.TEXT_PART_TYPE.STRING, ".", null)
                        });
                    }}
                ),
            };

        XMLFactory.TEXTE_ID[] ids = new XMLFactory.TEXTE_ID[]
        {
            XMLFactory.TEXTE_ID.DEFAULT_SKILL_TOOLTIP,
            XMLFactory.TEXTE_ID.SKILL_TOOLTIP_1,
            XMLFactory.TEXTE_ID.SKILL_TOOLTIP_2,
            XMLFactory.TEXTE_ID.SKILL_TOOLTIP_CONSUME,
        };

        XMLFactory.createXML(ids, texts);
    }
}