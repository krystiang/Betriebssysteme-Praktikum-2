package semaphore;

import java.util.concurrent.*;

public class Kasse {

	private Semaphore semaphore;
	private int nummer;
	private int warteschlangenLaenge = 0;

	public Kasse(int nummer) {
		this.semaphore = new Semaphore(1, true);
		this.nummer = nummer;
	}
	
	public int getNummer(){
		return nummer;
	}

	public int getWarteschlangenLaenge() {
		return warteschlangenLaenge;
	}
	
	public void erhoeheWarteschlangenLaenge() {
		warteschlangenLaenge++;
	}
	
	public void erniedrigeWarteschlangenLaenge() {
		warteschlangenLaenge--;
	}
	
	public Semaphore getSemaphore(){
		return semaphore;
	}

}
