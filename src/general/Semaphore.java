package general;

public class Semaphore {
	
	private static Semaphore instance;
	
	private Semaphore() {
	}
	
	public static Semaphore getInstance() {
		if (instance == null) {
			instance = new Semaphore();
	    }
	    return instance; 
	}
}
