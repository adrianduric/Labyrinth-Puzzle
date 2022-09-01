import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AapningGUI extends HvitRuteGUI
{
    public AapningGUI(int xPos, int yPos, LabyrintGUI labyrint, JPanel labyrintPanel)
    {
        super(xPos, yPos, labyrint, labyrintPanel);
    }

    @Override
    public void gaa(RuteGUI forrige, ArrayList<Tuppel> sti)
    {
        ArrayList<Tuppel> nySti = new ArrayList<>(sti);
        nySti.add(new Tuppel(xPos, yPos));
        labyrint.getUtveier().add(nySti);
    }
}
