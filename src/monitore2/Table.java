package monitore2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {

	// Den int Werten eine Bedeutung zuordnen
	public static final int TABAK = 1;
	public static final int PAPIER = 2;
	public static final int STREICHHOLZ = 3;
	
	// Set für die Zutaten, die auf den Tisch gelegt werden
	private Set<Integer> zutaten = new HashSet<Integer>();
	final Lock lock = new ReentrantLock();
	final Condition agentWarteschlange = lock.newCondition();
	final Condition smokerWarteschlange = lock.newCondition();

	public void legeZutatenHin(int ersteZutat, int zweiteZutat) {
		try {
			lock.lock();
			// solange Zutaten auf dem Tisch sind wartet der Agent.
			while (!zutaten.isEmpty()) {

				System.out.println("Agent wartet nun!");
				agentWarteschlange.await();
			}
			// Sind keine zutaten auf dem Tisch, werden die neuen Zutaten
			// hingelegt.
			System.out.println("Agent legt " + Table.zutatName(ersteZutat)
					+ " und " + Table.zutatName(zweiteZutat)
					+ " auf dem Tisch.");
			zutaten.add(ersteZutat);
			zutaten.add(zweiteZutat);
			smokerWarteschlange.signalAll();
		} catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
			lock.unlock();
		}
	}

	public void nehmeZutatenWeg(int zutat) {

		try {
			lock.lock();
			// falls Tisch leer ist oder Zutaten nicht passen
			// wartet der Smoker
			while (zutaten.isEmpty() || zutaten.contains(zutat)) {

				smokerWarteschlange.await();
			}
			// falls die Zutaten passen
			// nimmt der Smoker und faengt an zu rauchen.
			// Tisch ist dann leer.
			System.out.print("Smoker mit " + zutatName(zutat)
					+ " nimmt die Zutaten und fängt an zu rauchen...");

			try{
				Random rnd = new Random();
				int sleepTime = rnd.nextInt(1500);
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }


			System.out.println("fertig geraucht\n");
			zutaten.clear();
			agentWarteschlange.signal();
		} catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
			lock.unlock();
		}
	}

	// wandelt die Zahlen in Strings um die die eigentliche Bedeutung der Zahlen widerspiegeln
	public static String zutatName(int zutat) {
		String res = null;

		if (zutat == 1) {
			res = "Tabak";
		}

		if (zutat == 2) {
			res = "Papier";
		}

		if (zutat == 3) {
			res = "Streichholz";
		}

		return res;
	}
}
