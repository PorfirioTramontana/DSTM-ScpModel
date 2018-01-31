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
	
	public enum Transition implements general.Machine.Transition {
		T01,
		T02,
		T03,
		T04,
		T07,
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
							state = State.idle;
							StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
							System.out.println("next node = " + name + "_idle");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case idle:						
						System.out.println("current node = " + name + "_idle");
						if (true) {
							transition=Transition.T02;
							StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
							System.out.println("firing transition = " + name + "_T02");
							Machine m = new MSessionEstablishment(this, control.MSessionEstablishment.State.initial, PchFrom, PchTo);
							this.registerAsChildren(State.establishement,m);
							state = State.establishement;
							StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
							System.out.println("next node = " + name + "_establishement");
							System.out.println("**********");
						} else {
							assignTokenToChildren();
						}
						break;
					case establishement:						
						System.out.println("current node = " + name + "_establishement");
						if (this.terminatedChildred.get(State.establishement).size()>0) {
							Machine m = this.terminatedChildred.get(State.establishement).remove(0);
							MSessionEstablishment.State exitingState = (control.MSessionEstablishment.State) this.exitingState.remove(m);
							if (exitingState == control.MSessionEstablishment.State.entry) {
								transition=Transition.T03;
								StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
								System.out.println("firing transition = " + name + "_T03");
								state = State.exiting;
								StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
								System.out.println("next node = " + name + "_exiting");
								System.out.println("**********");
								this.notifyTerminationToParent();
							} else if (exitingState == control.MSessionEstablishment.State.som) {
								transition=Transition.T04;
								StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
								System.out.println("firing transition = " + name + "_T04");
								state = State.exiting;
								StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
								System.out.println("next node = " + name + "_exiting");
								System.out.println("**********");
								this.notifyTerminationToParent();
							} else if (exitingState == control.MSessionEstablishment.State.aborted) {
								transition=Transition.T07;
								StateTransitionCoverage.writeCoverage(name+",T,"+((Transition)transition).name());
								System.out.println("firing transition = " + name + "_T07");
								state = State.exiting;
								StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
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
						StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
						System.out.println("current node = " + name + "_entry");
						System.out.println("**********");
						/*assignTokenToChildren();*/
						break;
						
					case som:
						StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
						System.out.println("current node = " + name + "_som");
						System.out.println("**********");
						/*assignTokenToChildren();*/
						break;
					
					case exiting:
						StateTransitionCoverage.writeCoverage(name+",S,"+((State)state).name());
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
