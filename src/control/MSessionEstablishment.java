package control;

import control.MManageTrain.State;
import control.MManageTrain.Transition;
import coverageEvaluation.StateTransitionCoverage;
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
	
	public static enum State implements general.Machine.State {
		initial,
		waitForAck,
		waitForSessEstab,
		aborted,
		som,
		entry,
	}
	
	public static enum Transition implements general.Machine.Transition {
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
		StateTransitionCoverage.writeCoverage(name+",S#,"+(State.values().length));
		StateTransitionCoverage.writeCoverage(name+",T#,"+(Transition.values().length));		
		StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
		while (!termination) {
			synchronized (Semaphore.getInstance()) {
				if (token) {
					token = false;
					State s = (State) this.state;
					switch (s) {
					case initial:					
						System.out.println("current node = " + name + "_initial");
						if (true) {
							transition=Transition.T01;
							StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
							System.out.println("firing transition = " + name + "_T01");
							Message m = new MSystemVersion();
							((MSystemVersion) m).field1 = msgId.SystemVersion;
							((MSystemVersion) m).field2 = version.V1;
							PchTo.sendMessage(m);
							state = State.waitForAck;
							StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
							System.out.println("next node = " + name + "_waitForAck");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case waitForAck:					
						System.out.println("current node = " + name + "_waitForAck");
						if (PchFrom.receiveMessage()!=null && !(PchFrom.receiveMessage() instanceof MAck)) {
							transition=Transition.T02;
							StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
							System.out.println("firing transition = " + name + "_T02");
							state = State.aborted;
							StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
							System.out.println("next node = " + name + "_aborted");
							System.out.println("**********");
							this.notifyTerminationToParent();
						} else if (PchFrom.receiveMessage() instanceof MAck) {
							transition=Transition.T03;
							StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
							System.out.println("firing transition = " + name + "_T03");
							state = State.waitForSessEstab;
							StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
							System.out.println("next node = " + name + "_waitForSessEstab");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case waitForSessEstab:						
						System.out.println("current node = " + name + "_waitForSessEstab");
						if (PchFrom.receiveMessage()!=null && !(PchFrom.receiveMessage() instanceof MSessionEstablished)) {
							transition=Transition.T04;
							StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
							System.out.println("firing transition = " + name + "_T04");
							state = State.aborted;
							StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
							System.out.println("next node = " + name + "_aborted");
							System.out.println("**********");
							this.notifyTerminationToParent();
						} else if ((PchFrom.receiveMessage() instanceof MSessionEstablished) && (((MSessionEstablished) PchFrom.receiveMessage()).field2==area.L0)) {
							transition=Transition.T05;
							StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
							System.out.println("firing transition = " + name + "_T05");
							state = State.som;
							StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
							System.out.println("next node = " + name + "_som");
							System.out.println("**********");
							this.notifyTerminationToParent();
						} else if ((PchFrom.receiveMessage() instanceof MSessionEstablished) && (((MSessionEstablished) PchFrom.receiveMessage()).field2==area.L1)) {
							transition=Transition.T06;
							StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
							System.out.println("firing transition = " + name + "_T06");
							state = State.entry;
							StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
							System.out.println("next node = " + name + "_entry");
							System.out.println("**********");
							this.notifyTerminationToParent();
						} else {
							assignTokenToChildren();
						}
						break;
					case aborted:
						StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
						System.out.println("current node = " + name + "_aborted");
						System.out.println("**********");
						assignTokenToChildren();
						break;
					case som:
						StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
						System.out.println("current node = " + name + "_som");
						System.out.println("**********");
						assignTokenToChildren();
						break;
					case entry:
						StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
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
