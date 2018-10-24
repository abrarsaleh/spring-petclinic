package transactions;

import java.util.ArrayList;

public class TransactionLog {
	public ArrayList<Transaction> log = new ArrayList<Transaction>();
	private boolean active = false;
	private int depth = 0;
	Transaction pointer = new Transaction();
	int idtracker = 0;
	
	public TransactionLog() {
		log.clear();
	}
	
	public boolean add(int qualifier, int id, int time, int mloaded, int cloaded) {
		Transaction x = new Transaction(qualifier, id, time, mloaded, cloaded);
		
		try {
			log.add(x);
			idtracker = Math.max(id, idtracker);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean add(Transaction t) {
		try {
			log.add(t);
			idtracker++;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Transaction createNewEmpty() {
		if(active) {
			return pointer;
		}
		
		else {
			active = true;
			depth++;
			idtracker++;
			Transaction t = new Transaction();
			pointer = t;
			return pointer;
		}
		
	}
	
}
