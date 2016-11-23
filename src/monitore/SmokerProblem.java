package monitore;

import java.util.ArrayList;
import java.util.List;

public class SmokerProblem {

	private static List<Smoker> smokerList = new ArrayList<Smoker>();

	public static void main(String[] args) {
		// Tisch wird initialisiert
		Table table = new Table();

		// Die 3 Smoker werden initialisiert
		for (int i = 1; i <= 3; i++) {
			smokerList.add(new Smoker(i, table));
		}

		// Die 3 Smoker Threads werden gestartet
		for (Smoker smoker : smokerList) {
			smoker.start();
		}

		// Agenten Thread wird initialisiert und gestartet
		Agent agent = new Agent(table);
		agent.start();

		// main wartet 5 Sekunden bevor er den Agenten und die Smoker wieder
		// interrupted
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// Erneutes Setzen des Interrupt-Flags für den ausführenden Thread
			Thread.currentThread().interrupt();
		}

		agent.interrupt();

		for (Smoker smoker : smokerList) {
			smoker.interrupt();
		}
	}

}

