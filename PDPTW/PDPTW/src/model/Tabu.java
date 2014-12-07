package model;

public class Tabu {
	
	private static Tabu tabu = new Tabu();
	
	private Eigenvalue[] queue;
	private int currentPosition;

	private Tabu() {
		queue =  new Eigenvalue[5];					// size of tabu list
		currentPosition = 0;
	}
	
	public void offerAndPoll(Eigenvalue e) {
		if (isExist(e)) {
			return;
		}
		if (currentPosition < queue.length) {
			queue[currentPosition] = e;
			currentPosition++;
		}
		else {
			for (int i = queue.length-1; i > 0; i--) {
				queue[i-1] = queue[i];
			}
			queue[queue.length-1] = e;
		}
	}
	
	public boolean isExist(Eigenvalue e) {
		for (int i=0;i<currentPosition;i++) {
			if (e.isSame(queue[i])) {
				return true;
			}
		}
		return false;
	}
	
	public void clear() {
		for (int i=0;i<queue.length;i++) {
			queue[i] = null;
		}
		currentPosition = 0;
	}
	
	public static Tabu getSingleInstance() {
		return tabu;
	}
}
