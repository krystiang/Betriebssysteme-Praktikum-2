package monitore2;

public class Smoker extends Thread {

	private int zutat;
	private Table table;
	
	public Smoker(int zutat, Table table){
		this.zutat = zutat;
		this.table = table;
		
	}
	
	public void run(){
		while (!isInterrupted()) {
            //Zutat vom Tisch nehmen
            table.nehmeZutatenWeg(zutat);
        }
	}
}
