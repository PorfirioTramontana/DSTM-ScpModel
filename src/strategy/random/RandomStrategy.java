package strategy.random;

import input.InputData;

import java.util.Random;

import channel.CFromTrain;
import channel.CToTrain;
import data.MAck;
import data.MMovementAuthority;
import data.MSessionEstablished;
import data.MSystemVersion;
import data.MTrainRegistration;
import general.ExternalChannel;
import general.Message;
import strategy.Strategy;

public class RandomStrategy implements Strategy {
	
	@Override
	public Message generateMessage(ExternalChannel c, int stepNumber) {
		//PORF: Scelta della strategia per l'effettivo canale
		if (c instanceof CFromTrain)
			return this.RndCFromTrain((CFromTrain) c);
		else if (c instanceof CToTrain)
			return this.RndCToTrain((CToTrain) c);
		else 
			return null;
	}

	
	private Message RndCFromTrain(CFromTrain c) {
		//PORF: Strategia di generazione specifica per questo canale		
		Message m;
		//TODO: Aggiungere dipendenza da seed per ragioni di replicabilità degli esperimenti
		Random r = new Random();
		int choice = r.nextInt(4);
		//PORF: 4 valori possibili --> 4 elementi da classe di equivalenza
		switch (choice) {
		case 0:
			m = null;
			System.out.println(c.getName() + ": generated null");
			break;
		case 1:
			m = new MSessionEstablished();
			//PORF: msgID
			((MSessionEstablished) m).field1 = data.msgId.values()[r.nextInt(data.msgId.values().length)];
			//PORF: msgValue
			((MSessionEstablished) m).field2 = data.area.values()[r.nextInt(data.area.values().length)];
			//PORF: compreso tra 0 e MAXVALUE;
			((MSessionEstablished) m).field3 = r.nextInt(Integer.MAX_VALUE);
			//PORF: compreso tra 0 e MAXVALUE;
			((MSessionEstablished) m).field4 = r.nextInt(Integer.MAX_VALUE);
			System.out.println(c.getName() + ": generated MSessionEstablished <" + 
					((MSessionEstablished) m).field1 + ", " +
					((MSessionEstablished) m).field2 + ", " + 
					((MSessionEstablished) m).field3 + ", " + 
					((MSessionEstablished) m).field4 + ">");
			break;
		case 2:
			m = new MAck();
			((MAck) m).field1 = data.msgId.values()[r.nextInt(data.msgId.values().length)];
			((MAck) m).field2 = r.nextBoolean();
			System.out.println(c.getName() + ": generated MAck <" + 
					((MAck) m).field1 + ", " + 
					((MAck) m).field2 + ">");
			break;
		case 3:
			m = new MTrainRegistration();
			((MTrainRegistration) m).field1 = data.msgId.values()[r.nextInt(data.msgId.values().length)];
			((MTrainRegistration) m).field2 = data.registration.values()[r.nextInt(data.registration.values().length)];
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
	
	private Message RndCToTrain(CToTrain c) {
		Message m = null;
		Random r = new Random();
		int choice = r.nextInt(3);
		switch (choice) {
		case 0:
			System.out.println(c.getName() + ": generated null");
			break;
		case 1:
			System.out.println(c.getName() + ": generated MSystemVersion");
			m = new MSystemVersion();
			((MSystemVersion) m).field1 = data.msgId.values()[r.nextInt(data.msgId.values().length)];
			((MSystemVersion) m).field2 = data.version.values()[r.nextInt(data.version.values().length)];
			break;
		case 2:
			System.out.println(c.getName() + ": generated MMovementAuthority");
			m = new MMovementAuthority();
			((MMovementAuthority) m).field1 = data.msgId.values()[r.nextInt(data.msgId.values().length)];
			((MMovementAuthority) m).field2 = r.nextInt(Integer.MAX_VALUE);
			break;
		default:
			break;
		}
		return m;
	}

}
