package general;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public abstract class Machine extends Thread {
	
	public interface State {
	}
	
	public interface Transition {
	}
	
	//for internal execution
	protected State state;
	protected Transition transition;
	protected boolean token = false;
	protected boolean termination = false;
	
	//for managing hierarchical structure
	protected Machine parent;
	//for each machine (child), it gives the box through which it has been generated
	protected Hashtable<Machine, State> children = new Hashtable<Machine, State>();
	//for each machine (child), it gives the exiting state
	protected Hashtable<Machine, State> exitingState = new Hashtable<Machine, State>();
	//for each instantiating box, it gives the list of terminated children
	protected Hashtable<State, List<Machine>> terminatedChildred = new Hashtable<State, List<Machine>>();
	
	protected Machine(Machine parent, State entry) {
		this.parent = parent;
		this.state = entry;
	}
	
	synchronized public boolean getTokenState() {
		boolean ret = this.token;
		if (!ret) {
			Enumeration<Machine> mchEnum = this.children.keys();
			while (!ret && mchEnum.hasMoreElements()) {
				ret = mchEnum.nextElement().getTokenState();
			}
		}
		return ret;
	}
	
	synchronized public void obtainToken() {
		this.token = true;
	}
	
	synchronized public void terminate() {
		Enumeration<Machine> mchEnum = this.children.keys();
		while (mchEnum.hasMoreElements()) {
			mchEnum.nextElement().terminate();
		}
		this.termination = true;
	}
	
	synchronized public void assignTokenToChildren() {
		Enumeration<Machine> mchEnum = this.children.keys();
		while (mchEnum.hasMoreElements()) {
			mchEnum.nextElement().obtainToken();
		}
		Thread.yield();
	}
	
	synchronized public void registerAsChildren(State s, Machine m) {
		this.children.put(m, s);
		m.start();
		m.obtainToken();
	}
	
	synchronized public void notifyTerminationToParent() {
		this.parent.notifyChildTermination(this, state);
	}
	
	synchronized public void notifyChildTermination(Machine m, State s) {
		this.exitingState.put(m, s);
		State box = this.children.get(m);
		this.terminatedChildred.get(box).add(m);
	}
	
	synchronized public void terminateAChild(Machine m) {
		m.terminate();
	}
	
}
