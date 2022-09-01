import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class HovedprogramGUI 
{
    // 2021  Versjon 1.0
    public static void main(String[] args) {
        LabyrintGUI l = null;
        try {
            l = new LabyrintGUI(); //Valg av fil skjer nå i konstruktøren til labyrinten via JFileChooser
        } catch (FileNotFoundException e) {
            System.out.printf("FEIL: Kunne ikke lese fil");
            System.exit(1);
        }
        // les start-koordinater fra standard input

//Siden resten av oppgaven løses inni brukergrensesnittet, er resten av koden
//ikke lenger nødvendig

/*
        Scanner inn = new Scanner(System.in);
        System.out.println("Skriv inn koordinater <kolonne> <rad> ('a' for aa avslutte)");
        String[] ord = inn.nextLine().split(" ");
        while (!ord[0].equals("a")) {
            try {
                int startKol = Integer.parseInt(ord[0]);
                int startRad = Integer.parseInt(ord[1]);
                System.out.println("Utveier:");
                ArrayList<ArrayList<Tuppel>> utveier = l.finnUtveiFra(startKol, startRad);
                for (ArrayList<Tuppel> lis: utveier) {
                    for(Tuppel t: lis)
                        System.out.println(t);
                    System.out.println();
                }
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Ugyldig input!");
            }         
            System.out.println("Skriv inn nye koordinater ('a' for aa avslutte)");
            ord = inn.nextLine().split(" ");
        }
*/
    }
}