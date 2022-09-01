import java.util.ArrayList;
import java.util.Random;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class RuteGUI 
{
    protected int yPos; //Radplassering
    protected int xPos; //Kolonneplassering
    protected LabyrintGUI labyrint;
    protected JPanel labyrintPanel;
    protected JButton ruteKnapp;

    protected RuteGUI naboNord;
    protected RuteGUI naboSyd;
    protected RuteGUI naboVest;
    protected RuteGUI naboOst;

    public RuteGUI(int xPos, int yPos, LabyrintGUI labyrint, JPanel labyrintPanel)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.labyrint = labyrint;
        this.labyrintPanel = labyrintPanel;
        ruteKnapp = new JButton();
        ruteKnapp.setPreferredSize(new Dimension(30, 30));
        ruteKnapp.setBackground(Color.WHITE);
        ruteKnapp.setOpaque(true);
        ruteKnapp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ruteKnapp.addActionListener(new UtveiViser(this, labyrint));
        labyrintPanel.add(ruteKnapp);
    }

    public void gaa(RuteGUI forrige, ArrayList<Tuppel> sti)
    {
        ArrayList<Tuppel> nySti = new ArrayList<>(sti);
        nySti.add(new Tuppel(xPos, yPos));
        RuteGUI[] naboruter = {naboNord, naboOst, naboSyd, naboVest};
        for (RuteGUI nabo: naboruter)
        {
            if (nabo != forrige)
            {
                nabo.gaa(this, nySti);
            }
        }
    }

    public void finnUtvei()
    {
        ArrayList<Tuppel> sti = new ArrayList<>();
        gaa(this, sti);
    }

    class UtveiViser implements ActionListener
    {
        RuteGUI rute;
        LabyrintGUI labyrint;
        int indeks = 0;

        public UtveiViser(RuteGUI rute, LabyrintGUI labyrint)
        {
            this.rute = rute;
            this.labyrint = labyrint;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            labyrint.refreshUtveier();
            ArrayList<ArrayList<Tuppel>> alleUtveier = labyrint.finnUtveiFra(rute);
            int antallUtveier = alleUtveier.size();
            
            if (antallUtveier != 0) //Rute med minst en utvei er valgt:
            {
                labyrint.getAntallUtveierUtskrift().setText("Viser utvei 1 av " + antallUtveier);
                ArrayList<Tuppel> forsteUtvei = alleUtveier.get(0);
                labyrint.visUtvei(forsteUtvei);

                JButton forrigeKnapp = labyrint.getForrigeKnapp();
                JButton nesteKnapp = labyrint.getNesteKnapp();
    
                ActionListener[] visere = forrigeKnapp.getActionListeners();
                for (int i = 0; i < visere.length; i++)
                {
                    forrigeKnapp.removeActionListener(visere[i]);
                    System.out.println("Sletter gammel Forrige-knapp...");
                }
                visere = nesteKnapp.getActionListeners();
                for (int i = 0; i < visere.length; i++)
                {
                    nesteKnapp.removeActionListener(visere[i]);
                    System.out.println("Sletter gammel Neste-knapp...");
                }
                forrigeKnapp.addActionListener(new ForrigeKnapp(alleUtveier, indeks));
                nesteKnapp.addActionListener(new NesteKnapp(alleUtveier, indeks));
            }
            else labyrint.getAntallUtveierUtskrift().setText("Ingen utveier herfra!"); 
        }
    }

    class UtveiVelger implements ActionListener
    {
        ArrayList<ArrayList<Tuppel>> alleUtveier;
        int indeks;

        public UtveiVelger(ArrayList<ArrayList<Tuppel>> alleUtveier, int indeks)
        {
            this.alleUtveier = alleUtveier;
            this.indeks = indeks;
        }

        
        @Override
        public void actionPerformed(ActionEvent e){}
    }

    class NesteKnapp extends UtveiVelger
    {
        public NesteKnapp(ArrayList<ArrayList<Tuppel>> alleUtveier, int indeks)
        {
            super(alleUtveier, indeks);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            int antallUtveier = alleUtveier.size();
            labyrint.refreshUtveier();       
            if (indeks == alleUtveier.size() - 1)
            {
                indeks = 0;
            } 
            else
            {
                indeks++;
            } 
            labyrint.getAntallUtveierUtskrift().setText("Viser utvei " + (indeks + 1) + " av " + antallUtveier);
            labyrint.visUtvei(alleUtveier.get(indeks));
        }
    }

    class ForrigeKnapp extends UtveiVelger
    {
        public ForrigeKnapp(ArrayList<ArrayList<Tuppel>> alleUtveier, int indeks)
        {
            super(alleUtveier, indeks);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            int antallUtveier = alleUtveier.size();
            labyrint.refreshUtveier();       
            if (indeks == 0) 
            {
                indeks = alleUtveier.size() - 1;
            }
            else
            {
                indeks--;
            }
            labyrint.getAntallUtveierUtskrift().setText("Viser utvei " + (indeks + 1) + " av " + antallUtveier);
            labyrint.visUtvei(alleUtveier.get(indeks));
        }
    }



    abstract char tilTegn();
    public void skrivUtKoordinater()
    {
        System.out.println("(" + xPos + "," + yPos + ")");
    }
    public void skrivUtNaboer()
    {
        System.out.print("Mine koordinater: ");
        skrivUtKoordinater();
        System.out.print("Nord: ");
        if (naboNord != null) naboNord.skrivUtKoordinater();
        else System.out.println("Ingen");
        System.out.print("Syd: ");
        if (naboSyd != null) naboSyd.skrivUtKoordinater();
        else System.out.println("Ingen");
        System.out.print("Ost: ");
        if (naboOst != null) naboOst.skrivUtKoordinater();
        else System.out.println("Ingen");
        System.out.print("Vest: ");
        if (naboVest != null) naboVest.skrivUtKoordinater();
        else System.out.println("Ingen");
    }

    public void setNaboNord(RuteGUI nabo)
    {
        this.naboNord = nabo;
    }
    public void setNaboSyd(RuteGUI nabo)
    {
        this.naboSyd = nabo;
    }
    public void setNaboVest(RuteGUI nabo)
    {
        this.naboVest = nabo;
    }
    public void setNaboOst(RuteGUI nabo)
    {
        this.naboOst = nabo;
    }

    public RuteGUI getNaboNord()
    {
        return naboNord;
    }
    public RuteGUI getNaboSyd()
    {
        return naboSyd;
    }
    public RuteGUI getNaboVest()
    {
        return naboVest;
    }
    public RuteGUI getNaboOst()
    {
        return naboOst;
    }

    public JButton getRuteKnapp()
    {
        return ruteKnapp;
    }
}
