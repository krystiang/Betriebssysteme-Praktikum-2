package semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Mensa {


	private List<Kasse> kassenListe = new ArrayList<Kasse>();
	private Semaphore semaphore = new Semaphore(1);

	Mensa(int anzahlKassen) {
		for (int i = 1; i <= anzahlKassen; i++) {
			kassenListe.add(new Kasse(i));
		}
	}
	
	public Semaphore getSemaphore(){
		return semaphore;
	}
	
	public List<Kasse> getKassenListe(){
		return kassenListe;
	}

	public static void main(String[] args) {
		Mensa mensa = new Mensa(3);

		List<Student> studentenListe = new ArrayList<Student>();
		
		for(int i = 0; i<10;i++){
			studentenListe.add(new Student(i,mensa));
		}
		
		for(Student student : studentenListe){
			student.start();
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// Erneutes Setzen des Interrupt-Flags für den ausführenden Thread
			Thread.currentThread().interrupt();
		}
		
		for(Student student : studentenListe){
			student.interrupt();
		}
	}
}
