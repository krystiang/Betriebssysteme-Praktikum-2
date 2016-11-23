package monitore2;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Agent extends Thread {

	private Table table;

	public Agent(Table table) {
		this.table = table;
	}

	public void run() {
		while (!isInterrupted()) {

			//Erstellung des Sets zum Aufbewahren zwei unterschiedlicher Zutaten, die mit Random erzeugt werden
			Set<Integer> zufallsZutaten = new HashSet<Integer>();
			while (zufallsZutaten.size() < 2) {
				Random rnd = new Random();
				zufallsZutaten.add(rnd.nextInt(3) + 1);
			}
			
			//Uebergeben der Zutaten an den Tisch
			Iterator<Integer> iter = zufallsZutaten.iterator();
			table.legeZutatenHin(iter.next(),iter.next());
		}
	}

}
