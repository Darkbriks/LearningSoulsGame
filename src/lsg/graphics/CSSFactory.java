package lsg.graphics;

import java.util.Objects;

public class CSSFactory
{
    private final static String CSS_DIR = "css/";

    public static String getStyleSheet(String filename)
    {
        return Objects.requireNonNull(CSSFactory.class.getResource(CSS_DIR + filename)).toExternalForm();
    }
}
