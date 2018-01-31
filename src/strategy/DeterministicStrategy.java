package strategy;

import input.Datum;
import input.InputData;
import channel.CFromTrain;
import channel.CToTrain;
import control.MManageTrain.Transition;
import coverageEvaluation.StateTransitionCoverage;
import data.MAck;
import data.MSessionEstablished;
import data.MTrainRegistration;
import general.ExternalChannel;
import general.Message;

public class DeterministicStrategy implements Strategy {

	private InputData inputData;

	public DeterministicStrategy(InputData i) {
		inputData=i;
	}

	@Override
	public Message generateMessage(ExternalChannel c, int stepNumber) {
		//PORF: Scelta della strategia per l'effettivo canale
		if (c instanceof CFromTrain)
			return this.DeterministicCFromTrain((CFromTrain) c,this.inputData,stepNumber);
		else if (c instanceof CToTrain)
			//return this.RndCToTrain((CToTrain) c);
			return null;
		else 
			return null;
	}
	
	private Message DeterministicCFromTrain(CFromTrain c,InputData i, int step){
		StateTransitionCoverage.writeCoverage("Step,"+step);
		Message m;
		Datum d=(Datum)i.d.get(step);
		
		//PORF: 4 valori possibili --> 4 elementi da classe di equivalenza
		switch (d.eventType) {
		case 0:
			m = null;
			System.out.println(c.getName() + ": generated null");
			break;
		case 1:
			m = new MSessionEstablished();
			((MSessionEstablished) m).field1 = data.msgId.values()[d.msgField1_1];
			((MSessionEstablished) m).field2 = data.area.values()[d.msgField2_1];
			((MSessionEstablished) m).field3 = d.msgField3_1;
			((MSessionEstablished) m).field4 = d.msgField4_1;
			System.out.println(c.getName() + ": generated MSessionEstablished <" + 
					((MSessionEstablished) m).field1 + ", " +
					((MSessionEstablished) m).field2 + ", " + 
					((MSessionEstablished) m).field3 + ", " + 
					((MSessionEstablished) m).field4 + ">");
			break;
		case 2:
			m = new MAck();
			((MAck) m).field1 = data.msgId.values()[d.msgField1_2];
			((MAck) m).field2 = d.msgField2_2;
			System.out.println(c.getName() + ": generated MAck <" + 
					((MAck) m).field1 + ", " + 
					((MAck) m).field2 + ">");
			break;
		case 3:
			m = new MTrainRegistration();
			((MTrainRegistration) m).field1 = data.msgId.values()[d.msgField1_3];
			((MTrainRegistration) m).field2 = data.registration.values()[d.msgField2_3];
			System.out.println(c.getName() + ": generated MTrainRegistration <" +
					((MTrainRegistration) m).field1 + ", " + 
					((MTrainRegistration) m).field2 + ">");
			break;
		default:
			m = null;
			break;
		}
		return m;		
	}


}
