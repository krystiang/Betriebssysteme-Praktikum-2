package monitore;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Table {

	// Den int Werten eine Bedeutung zuordnen
	public static final int TABAK = 1;
	public static final int PAPIER = 2;
	public static final int STREICHHOLZ = 3;
	
	// Set für die Zutaten, die auf den Tisch gelegt werden
	private Set<Integer> zutaten = new HashSet<Integer>();

	public synchronized void legeZutatenHin(int ersteZutat, int zweiteZutat) {

		// solange Zutaten auf dem Tisch sind wartet der Agent.
		while (!zutaten.isEmpty()) {

			System.out.println("Agent wartet nun!");
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
		// Sind keine zutaten auf dem Tisch, werden die neuen Zutaten hingelegt.
		System.out.println("Agent legt " + Table.zutatName(ersteZutat)
				+ " und " + Table.zutatName(zweiteZutat) + " auf dem Tisch.");
		zutaten.add(ersteZutat);
		zutaten.add(zweiteZutat);
		// meldet an alle, dass neue Sachen auf dem Tisch liegen
		notifyAll();
	}
	
	public synchronized void nehmeZutatenWeg(int zutat) {


        //falls Tisch leer ist oder Zutaten nicht passen
        //wartet der Smoker
        while (zutaten.isEmpty() || zutaten.contains(zutat)) {
            //System.out.println("Smoker mit " + itemString(hasIngredient) + " wartet!");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        //falls die Zutaten passen
        //nimmt der Smoker und f�ngt an zu rauchen
        //das Zeit kostet.
        //Tisch ist dann leer.
        System.out.print("Smoker mit " + zutatName(zutat) + " nimmt die Zutaten und fängt an zu rauchen...");
  
        
        Random rnd = new Random();
        int sleepTime = rnd.nextInt(1500);
        try {
            Thread.sleep(sleepTime);
            System.out.println("fertig geraucht\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        zutaten.clear();

        // meldet allen, dass er fertig ist
        notifyAll();


    }
	
	
    //wandelt die zahlen in String um
    public static String zutatName(int item) {
        String res = null;

        if (item == 1) {
            res = "Tabak";
        }

        if (item == 2) {
            res = "Papier";
        }

        if (item == 3) {
            res = "Streichholz";
        }

        return res;
    }
}
