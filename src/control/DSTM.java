package control;

import java.util.ArrayList;

import channel.CFromTrain;
import channel.CToTrain;
import general.ExternalChannel;
import general.Machine;
import general.Semaphore;
import strategy.Strategy;

public class DSTM extends Machine {
	
	//PORF: Strategy unico parametro di DSTM
	private Strategy strategy;
	
	public enum State implements general.Machine.State {
		working
	}
	
	public DSTM(Strategy s) {
		super(null, State.working);
		this.terminatedChildred.put(State.working, new ArrayList<Machine>());
		this.strategy = s;
	}

	@Override
	public void run() {
		//PORF: DSTM è unico thread
		//PORF: 1 canale in ingresso:CFromTrain
		//PORF: 1 canale in uscita:CToTrain
		ExternalChannel cFrom = new CFromTrain("CFromTrain");
		ExternalChannel cTo = new CToTrain("CToTrain");
		Machine m = new MManageTrain(this, control.MManageTrain.State.initial, cFrom, cTo);
		synchronized (Semaphore.getInstance()) {
			//canali di comunicazione
			//PORF: caso speciale: primo messaggio
			cFrom.newStep(strategy);
			//cTo.newStep(strategy);
			
			this.registerAsChildren(State.working, m);
		}
		while (!termination) {
			boolean deadlock = false;
			while (!deadlock) {
				boolean ret;
				synchronized (Semaphore.getInstance()) {
					ret = m.getTokenState();
				}
				if (!ret)
					deadlock=true;
				else {
					Thread.yield();
				}
			}
			synchronized (Semaphore.getInstance()) {
				if(terminatedChildred.get(State.working).size()>0) {
					this.terminate();
				} else {
					System.out.println("----------------------------------");
					//canali di comunicazione
					//PORF: prossimo messaggio
					cFrom.newStep(strategy);
					//cTo.newStep(strategy);
					
					m.obtainToken();
				}
			}
		}
	}
}
