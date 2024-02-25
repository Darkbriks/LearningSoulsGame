package user_mods;

import javax.swing.*;

public abstract class ModdedWindow extends JFrame
{
    public ModdedWindow(String title)
    {
        super(title);
    }

    public void showWindow()
    {
        setVisible(true);
    }

    public void hideWindow()
    {
        setVisible(false);
    }

    public void closeWindow()
    {
        dispose();
    }
}
