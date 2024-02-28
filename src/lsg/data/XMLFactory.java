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

        ////////// EXCEPTIONS //////////
        BAG_FULL                        ("xml/exceptions.xml"),
        CONSUME_EMPTY                   ("xml/exceptions.xml"),
        CONSUME_NULL                    ("xml/exceptions.xml"),
        CONSUME_REPAIR_NULL_WEAPON      ("xml/exceptions.xml"),
        NO_BAG                          ("xml/exceptions.xml"),
        STAMINA_EMPTY                   ("xml/exceptions.xml"),
        WEAPON_BROKEN                   ("xml/exceptions.xml"),
        WEAPON_NULL                     ("xml/exceptions.xml"),
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

    public static InterpretableText getText(TEXTE_ID id) { return textes.get(id); }

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

    public static void loadXML(TEXTE_ID id)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(Objects.requireNonNull(XMLFactory.class.getResource(id.path).toString()));
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
        }
        catch (Exception e) { System.err.println("Error in XMLFactory.loadXML() : " + e.getClass().getSimpleName() + " - " + e.getMessage()); }
    }

    public static void main(String[] args)
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

        createXML(new TEXTE_ID[] { TEXTE_ID.TEMPLATE }, new InterpretableText[] { text });

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

        TEXTE_ID[] ids = new TEXTE_ID[]
        {
            TEXTE_ID.BAG_FULL,
            TEXTE_ID.CONSUME_EMPTY,
            TEXTE_ID.CONSUME_NULL,
            TEXTE_ID.CONSUME_REPAIR_NULL_WEAPON,
            TEXTE_ID.NO_BAG,
            TEXTE_ID.STAMINA_EMPTY,
            TEXTE_ID.WEAPON_BROKEN,
            TEXTE_ID.WEAPON_NULL,
        };
        createXML(ids, texts);

        preloadAll(() -> {
            for (TEXTE_ID id : TEXTE_ID.values()) { System.out.println(getText(id).toString()); }
        });
    }
}
