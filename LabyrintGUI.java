import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class LabyrintGUI
{
    private int radAntall; //Y-aksen
    private int kolonneAntall; //X-aksen
    private RuteGUI[][] rutenett;
    private ArrayList<ArrayList<Tuppel>> utveier = new ArrayList<>();

    private JPanel valgknapperPanel;
    private JButton nesteKnapp;
    private JButton forrigeKnapp;
    private JLabel antallUtveierUtskrift;

    public LabyrintGUI() throws FileNotFoundException
    {
        JFrame vindu = new JFrame("Labyrint");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel hovedPanel = new JPanel();
        hovedPanel.setLayout(new BoxLayout(hovedPanel, BoxLayout.PAGE_AXIS));

        JLabel overskriftPanel = new JLabel("Trykk paa en rute for aa se utveiene derfra!");
        overskriftPanel.setFont(new Font("Arial", Font.PLAIN, 20));
        hovedPanel.add(overskriftPanel);

        JPanel labyrintPanel = new JPanel();
        hovedPanel.add(labyrintPanel);

        valgknapperPanel = new JPanel();

        forrigeKnapp = new JButton("Forrige");
        forrigeKnapp.setFont(new Font("Arial", Font.PLAIN, 20));
        valgknapperPanel.add(forrigeKnapp);

        antallUtveierUtskrift = new JLabel("Rute ikke valgt");
        antallUtveierUtskrift.setFont(new Font("Arial", Font.PLAIN, 20));
        valgknapperPanel.add(antallUtveierUtskrift);

        nesteKnapp = new JButton("Neste");
        nesteKnapp.setFont(new Font("Arial", Font.PLAIN, 20));
        valgknapperPanel.add(nesteKnapp);
        hovedPanel.add(valgknapperPanel);

        vindu.add(hovedPanel);

        JFileChooser velger = new JFileChooser();
        int resultat = velger.showOpenDialog(null);
        if (resultat != JFileChooser.APPROVE_OPTION) System.exit(1);
        File fil = velger.getSelectedFile();

        Scanner sc = new Scanner(fil);
        radAntall = Integer.parseInt(sc.next());
        kolonneAntall = Integer.parseInt(sc.next());
        rutenett = new RuteGUI[kolonneAntall][radAntall];

        labyrintPanel.setLayout(new GridLayout(radAntall, kolonneAntall)); //Format: Y, X

        while (sc.hasNext())
        {
            for (int y = 0; y < radAntall; y++)
            {
                char[] nyRad = sc.next().toCharArray();
                for (int x = 0; x < kolonneAntall; x++)
                {
                    RuteGUI nyRute;
                    if (nyRad[x] == '#')
                    {
                        nyRute = new SortRuteGUI(x, y, this, labyrintPanel);
                    }
                    else
                    {
                        if (y == 0 || x == 0 || y == radAntall - 1 || x == kolonneAntall - 1)
                        {
                            nyRute = new AapningGUI(x, y, this, labyrintPanel);
                        }
                        else
                        {
                            nyRute = new HvitRuteGUI(x, y, this, labyrintPanel);
                        }
                    }
                    rutenett[x][y] = nyRute;
                }
            }  
        }
        sc.close();
        for (int y = 0; y < radAntall; y++)
            {
                for (int x = 0; x < kolonneAntall; x++)
                {
                    RuteGUI denneRuten = rutenett[x][y];
                    if (0 < y) //Nordlig nabo
                    {
                        RuteGUI naboNord = rutenett[x][y - 1];
                        denneRuten.setNaboNord(naboNord);
                    }
                    if (y < radAntall - 1) //Sørlig
                    {
                        RuteGUI naboSyd = rutenett[x][y + 1];
                        denneRuten.setNaboSyd(naboSyd);
                    }
                    if (0 < x) //Vestlig 
                    {
                        RuteGUI naboVest = rutenett[x - 1][y];
                        denneRuten.setNaboVest(naboVest);
                    }  
                    if (x < kolonneAntall - 1) //Østlig
                    {
                        RuteGUI naboOst = rutenett[x + 1][y];
                        denneRuten.setNaboOst(naboOst);
                    }  
                }
            }
        vindu.pack();
        vindu.setVisible(true);
    }

    
    
          
    public ArrayList<ArrayList<Tuppel>> finnUtveiFra(int xPos, int yPos)
    {
        RuteGUI start = rutenett[xPos][yPos];
        start.finnUtvei();
        ArrayList<ArrayList<Tuppel>> temp = new ArrayList<>(utveier);
        utveier.clear();   
        return temp;
    }

    public ArrayList<ArrayList<Tuppel>> finnUtveiFra(RuteGUI start)
    {
        start.finnUtvei();
        ArrayList<ArrayList<Tuppel>> temp = new ArrayList<>(utveier);
        utveier.clear();   
        return temp;
    }

    public void refreshUtveier()
    {
        for (RuteGUI[] rad : rutenett)
        {
            for (RuteGUI rute : rad)
            {
                if (rute instanceof HvitRuteGUI) 
                {
                    rute.getRuteKnapp().setBackground(Color.WHITE);
                    rute.getRuteKnapp().setText("");
                }
            }
        }
    }

    public void visUtvei(ArrayList<Tuppel> utvei)
    {
        for (Tuppel tuppel : utvei)
        {
            RuteGUI tempRute = getRuteAt(tuppel.getxPos(), tuppel.getyPos());
            JButton ruteKnapp = tempRute.getRuteKnapp();
            ruteKnapp.setBackground(Color.GREEN);
            ruteKnapp.setText("X");
            ruteKnapp.setFont(new Font("Arial", Font.PLAIN, 30));
        }
    }

    public ArrayList<ArrayList<Tuppel>> getUtveier()
    {
        return utveier;
    }

    public RuteGUI getRuteAt(int xPos, int yPos)
    {
        return rutenett[xPos][yPos];
    }

    public JPanel getValgknapperPanel()
    {
        return valgknapperPanel;
    }
    
    public JLabel getAntallUtveierUtskrift()
    {
        return antallUtveierUtskrift;
    }

    public JButton getForrigeKnapp()
    {
        return forrigeKnapp;
    }

    public JButton getNesteKnapp()
    {
        return nesteKnapp;
    }
}


