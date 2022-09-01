import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HvitRuteGUI extends RuteGUI
{
    public HvitRuteGUI(int xPos, int yPos, LabyrintGUI labyrint, JPanel labyrintPanel)
    {
        super(xPos, yPos, labyrint, labyrintPanel);
    }

    @Override
    public void gaa(RuteGUI forrige, ArrayList<Tuppel> sti) 
    { 
        super.gaa(forrige, sti);
    }

    @Override
    char tilTegn()
    {
        return '.';
    }
}
