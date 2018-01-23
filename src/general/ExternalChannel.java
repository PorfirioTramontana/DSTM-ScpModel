package general;

import strategy.Strategy;

public abstract class ExternalChannel {
	
	private final String name;
	
	private Message currentMessage = null;
	private Message nextMessage = null;
	
	private int stepNumber = 0;
	
	public ExternalChannel(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	synchronized public Message receiveMessage() {
		return this.currentMessage;
	}
	
	synchronized public boolean sendMessage(Message m) {
		boolean ret;
		if (this.nextMessage == null) {
			this.nextMessage = m;
			ret = true;
		} else {
			ret = false;
		} 
		return ret;
	}
	
	synchronized public void newStep(Strategy s) {	
		if (this.nextMessage == null) {
			this.currentMessage = s.generateMessage(this, stepNumber);
		} else {
			//PORF: Quando e' stato scritto il nextMessage?
			this.currentMessage = this.nextMessage;
			this.nextMessage = null;
		}
		stepNumber++;
	}
	
}
