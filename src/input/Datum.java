package input;

import java.util.StringTokenizer;

public class Datum {
	public Datum(StringTokenizer st0) {
		// st0 è la stringa divisa in token
		eventType=(Integer.parseInt(st0.nextToken()));
		msgField1_1=(Integer.parseInt(st0.nextToken()));
		msgField2_1=(Integer.parseInt(st0.nextToken()));
		msgField3_1=(Integer.parseInt(st0.nextToken()));
		msgField4_1=(Integer.parseInt(st0.nextToken()));
		msgField1_2=(Integer.parseInt(st0.nextToken()));
		msgField2_2=(Boolean.parseBoolean(st0.nextToken()));
		msgField1_3=(Integer.parseInt(st0.nextToken()));
		msgField2_3=(Integer.parseInt(st0.nextToken()));
	}
	// Compreso tra 0 e 3
	public int eventType;
	// Compreso tra 0 e  data.msgId.values().length-1 = 4
	public int msgField1_1;
	// Compreso tra 0 e  data.area.values().length-1 = 1
	public int msgField2_1;
	//Compreso tra 0 e Integer.MAXVALUE
	public int msgField3_1;
	//Compreso tra 0 e Integer.MAXVALUE
	public int msgField4_1;
	
	// Compreso tra 0 e  data.msgId.values().length-1 =4
	public int msgField1_2;
	public boolean msgField2_2;
	
	// Compreso tra 0 e  data.msgId.values().length-1 = 4
	public int msgField1_3;	
	//Compreso tra 0 e data.registration.values().length-1 = 1
	public int msgField2_3;
}
