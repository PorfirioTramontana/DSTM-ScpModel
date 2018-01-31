package control;

import java.util.ArrayList;

import coverageEvaluation.StateTransitionCoverage;
import general.ExternalChannel;
import general.Machine;
import general.Semaphore;

public class MManageTrain extends Machine {
	
	private String name = "MManageTrain";
	
	private ExternalChannel PchFrom;
	private ExternalChannel PchTo;
	
	public enum State implements general.Machine.State {
		initial,
		idle,
		establishement,
		entry,
		som,
		giveMA,
		exiting,
	}
	
	public MManageTrain(Machine parent, State entry, ExternalChannel p1, ExternalChannel p2) {
		super(parent, entry);
		PchFrom = p1;
		PchTo = p2;
		this.terminatedChildred.put(State.establishement, new ArrayList<Machine>());
		this.terminatedChildred.put(State.entry, new ArrayList<Machine>());
		this.terminatedChildred.put(State.som, new ArrayList<Machine>());
		this.terminatedChildred.put(State.giveMA, new ArrayList<Machine>());
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
						StateTransitionCoverage.writeCoverage(name+",S,"+s.name());
						if (true) {
							StateTransitionCoverage.writeCoverage(name+",T,1");
							System.out.println("firing transition = " + name + "_T01");
							state = State.idle;
							System.out.println("next node = " + name + "_idle");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case idle:
						StateTransitionCoverage.writeCoverage(name+",S,"+s.name());
						System.out.println("current node = " + name + "_idle");
						if (true) {
							StateTransitionCoverage.writeCoverage(name+",T,2");
							System.out.println("firing transition = " + name + "_T02");
							Machine m = new MSessionEstablishment(this, control.MSessionEstablishment.State.initial, PchFrom, PchTo);
							this.registerAsChildren(State.establishement,m);
							state = State.establishement;
							System.out.println("next node = " + name + "_establishement");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case establishement:
						StateTransitionCoverage.writeCoverage(name+",S,"+s.name());
						System.out.println("current node = " + name + "_establishement");
						if (this.terminatedChildred.get(State.establishement).size()>0) {
							Machine m = this.terminatedChildred.get(State.establishement).remove(0);
							MSessionEstablishment.State exitingState = (control.MSessionEstablishment.State) this.exitingState.remove(m);
							if (exitingState == control.MSessionEstablishment.State.entry) {
								StateTransitionCoverage.writeCoverage(name+",T,3");
								System.out.println("firing transition = " + name + "_T03");
								state = State.exiting;
								System.out.println("next node = " + name + "_exiting");
								System.out.println("**********");
								this.notifyTerminationToParent();
							} else if (exitingState == control.MSessionEstablishment.State.som) {
								StateTransitionCoverage.writeCoverage(name+",T,4");
								System.out.println("firing transition = " + name + "_T04");
								state = State.exiting;
								System.out.println("next node = " + name + "_exiting");
								System.out.println("**********");
								this.notifyTerminationToParent();
							} else if (exitingState == control.MSessionEstablishment.State.aborted) {
								StateTransitionCoverage.writeCoverage(name+",T,7");
								System.out.println("firing transition = " + name + "_T07");
								state = State.exiting;
								System.out.println("next node = " + name + "_exiting");
								System.out.println("**********");
								this.notifyTerminationToParent();
							}
						} else {
							System.out.println("**********");
							this.assignTokenToChildren();
						}
						break;
					case entry:
						StateTransitionCoverage.writeCoverage(name+",S,"+s.name());
						System.out.println("current node = " + name + "_entry");
						System.out.println("**********");
						/*assignTokenToChildren();*/
						break;
						
					case som:
						StateTransitionCoverage.writeCoverage(name+",S,"+s.name());
						System.out.println("current node = " + name + "_som");
						System.out.println("**********");
						/*assignTokenToChildren();*/
						break;
					
					case exiting:
						StateTransitionCoverage.writeCoverage(name+",S,"+s.name());
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
