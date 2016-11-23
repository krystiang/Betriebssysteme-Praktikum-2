package semaphore;

import java.util.List;
import java.util.Random;

public class Student extends Thread {
	
	private int matNr;
	private Mensa mensa;
	
	Student(int matNr, Mensa mensa){
		this.matNr = matNr;
		this.mensa = mensa;
	}
	
	public int getMatNr(){
		return matNr;
	}
	
	public Kasse besteKasseSuchen(){
		//kürzeste Kasse aussuchen
		List<Kasse> kassenListe = mensa.getKassenListe();
		Kasse kasseMitKleinsterSchlange = kassenListe.get(0);
		for( Kasse kasse : kassenListe){
			if(kasse.getWarteschlangenLaenge() < kasseMitKleinsterSchlange.getWarteschlangenLaenge()){
				kasseMitKleinsterSchlange = kasse;
			}
		}	
		return kasseMitKleinsterSchlange;
	}
	
	private void bezahlen(){
		
		try {
			mensa.getSemaphore().acquire();
		} catch (InterruptedException e){
			Thread.currentThread().interrupt();
		}
		
		Kasse kasse = besteKasseSuchen();
		System.err.println(this.getMatNr() + " stellt sich an Kasse: " + kasse.getNummer() + " mit " + (kasse.getWarteschlangenLaenge()) +" vor ihm an!" );
		kasse.erhoeheWarteschlangenLaenge();
		mensa.getSemaphore().release();
	
		
		try {
			kasse.getSemaphore().acquire();
		} catch (InterruptedException e) {
			// Erneutes Setzen des Interrupt-Flags für den ausführenden Thread
			Thread.currentThread().interrupt();
		}
		
	
		Random rnd = new Random();
		int bezahlZeit = rnd.nextInt(2000);

		try {
			// Ausführenden Thread blockieren
			System.err.println(this.getMatNr() + " bezahlt" + " an Kasse: " + kasse.getNummer());
			Thread.sleep(bezahlZeit);
		} catch (InterruptedException e) {
			// Erneutes Setzen des Interrupt-Flags für den ausführenden Thread
			Thread.currentThread().interrupt();
		}
		
		kasse.erniedrigeWarteschlangenLaenge();
		kasse.getSemaphore().release();
	
	}
	
	private void essen(){
		Random rnd = new Random();
		int essensZeit = rnd.nextInt(2000);

		try {
			// Ausführenden Thread blockieren
			System.err.println(this.getMatNr() + " isst!");
			Thread.sleep(essensZeit);
		} catch (InterruptedException e) {
			// Erneutes Setzen des Interrupt-Flags für den ausführenden Thread
			Thread.currentThread().interrupt();
		}
	}
	
	public void run(){
		while(!Thread.interrupted()){
			bezahlen();
			essen();
		}
	}

}
