package transactions;

public class Transaction {
	public int qualifier = 0;
	public int ID = 0;
	public int timeTaken = 0;
	public int methodsLoaded = 0;
	public int classesLoaded = 0;
	
	public Transaction() {
		
	}
	
	public Transaction(int qualifier, int id, int time, int mloaded, int cloaded) {
		this.qualifier = qualifier;
		this.ID = id;
		timeTaken = time;
		methodsLoaded = mloaded;
		classesLoaded = cloaded;
	}
	
}
