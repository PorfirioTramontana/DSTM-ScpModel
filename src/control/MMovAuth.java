package control;

import general.ExternalChannel;
import general.Machine;
import general.Semaphore;

public class MMovAuth extends Machine {
	
private String name = "MMovAuth";
	
	private ExternalChannel PchFrom;
	private ExternalChannel PchTo;
	
	public enum State implements general.Machine.State {
		initial,
		idle,
		exiting,
	}
	
	public MMovAuth(Machine parent, State entry, ExternalChannel p1, ExternalChannel p2) {
		super(parent, entry);
		PchFrom = p1;
		PchTo = p2;
	}
	
	@Override
	public void run() {
		while (!termination) {
			synchronized (Semaphore.getInstance()) {
				if (token) {
					token = false;
					State s = (State) this.state;
					switch (s) {
					case initial:						
						System.out.println("current node = " + name + "_initial");
						if (true) {
							System.out.println("firing transition = " + name + "_T01");
							state = State.idle;
							System.out.println("next node = " + name + "_idle");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case idle:
						System.out.println("current node = " + name + "_idle");
						if (true) {
							System.out.println("firing transition = " + name + "_T02");
							state = State.exiting;
							System.out.println("next node = " + name + "_exiting");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case exiting:
						System.out.println("current node = " + name + "_exiting");
						System.out.println("**********");
						assignTokenToChildren();
						break;
					default:
						break;
					}
				} else {
					Thread.yield();
				}
			}
		}
	}
}
