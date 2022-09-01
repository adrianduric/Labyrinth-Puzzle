import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SortRuteGUI extends RuteGUI
{
    public SortRuteGUI(int xPos, int yPos, LabyrintGUI labyrint, JPanel labyrintPanel)
    {
        super(xPos, yPos, labyrint, labyrintPanel);
        ruteKnapp.setBackground(Color.GRAY);
    }

    @Override
    public void gaa(RuteGUI forrige, ArrayList<Tuppel> sti){}

    @Override
    char tilTegn()
    {
        return '#';
    }
}
