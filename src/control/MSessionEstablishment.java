package control;

import data.MAck;
import data.MSessionEstablished;
import data.MSystemVersion;
import data.area;
import data.msgId;
import data.version;
import general.ExternalChannel;
import general.Machine;
import general.Message;
import general.Semaphore;

public class MSessionEstablishment extends Machine {
	
	private String name = "MSessionEstablishment";
	
	private ExternalChannel PchFrom;
	private ExternalChannel PchTo;
	
	public enum State implements general.Machine.State {
		initial,
		waitForAck,
		waitForSessEstab,
		aborted,
		som,
		entry,
	}
	
	public enum Transition implements general.Machine.Transition {
		T01,
		T02,
		T03,
		T04,
		T05,
		T06,
	}
	
	public MSessionEstablishment(Machine parent, State entry, ExternalChannel p1, ExternalChannel p2) {
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
							Message m = new MSystemVersion();
							((MSystemVersion) m).field1 = msgId.SystemVersion;
							((MSystemVersion) m).field2 = version.V1;
							PchTo.sendMessage(m);
							state = State.waitForAck;
							System.out.println("next node = " + name + "_waitForAck");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case waitForAck:					
						System.out.println("current node = " + name + "_waitForAck");
						if (PchFrom.receiveMessage()!=null && !(PchFrom.receiveMessage() instanceof MAck)) {
							System.out.println("firing transition = " + name + "_T02");
							state = State.aborted;
							System.out.println("next node = " + name + "_aborted");
							System.out.println("**********");
							this.notifyTerminationToParent();
						} else if (PchFrom.receiveMessage() instanceof MAck) {
							System.out.println("firing transition = " + name + "_T03");
							state = State.waitForSessEstab;
							System.out.println("next node = " + name + "_waitForSessEstab");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case waitForSessEstab:						
						System.out.println("current node = " + name + "_waitForSessEstab");
						if (PchFrom.receiveMessage()!=null && !(PchFrom.receiveMessage() instanceof MSessionEstablished)) {
							System.out.println("firing transition = " + name + "_T04");
							state = State.aborted;
							System.out.println("next node = " + name + "_aborted");
							System.out.println("**********");
							this.notifyTerminationToParent();
						} else if ((PchFrom.receiveMessage() instanceof MSessionEstablished) && (((MSessionEstablished) PchFrom.receiveMessage()).field2==area.L0)) {
							System.out.println("firing transition = " + name + "_T05");
							state = State.som;
							System.out.println("next node = " + name + "_som");
							System.out.println("**********");
							this.notifyTerminationToParent();
						} else if ((PchFrom.receiveMessage() instanceof MSessionEstablished) && (((MSessionEstablished) PchFrom.receiveMessage()).field2==area.L1)) {
							System.out.println("firing transition = " + name + "_T06");
							state = State.entry;
							System.out.println("next node = " + name + "_entry");
							System.out.println("**********");
							this.notifyTerminationToParent();
						} else {
							assignTokenToChildren();
						}
						break;
					case aborted:
						System.out.println("current node = " + name + "_aborted");
						System.out.println("**********");
						assignTokenToChildren();
						break;
					case som:
						System.out.println("current node = " + name + "_som");
						System.out.println("**********");
						assignTokenToChildren();
						break;
					case entry:
						System.out.println("current node = " + name + "_entry");
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
