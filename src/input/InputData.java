package input;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class InputData {
	public ArrayList<Datum> d;
	
	public InputData(String string) {
		// string e' il nome del file con i valori
		// numEventi e' il numero di test della sequenza 
		
		d=new ArrayList();
		//apri file con i dati 		
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(string);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		//riempio tutta la test suite
		String s=null;
		try {
			s=br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		} //brucia la prima riga
		do{
			try {
				s=br.readLine();				
			} catch (IOException e) {
				s=null;
			}	
			if (s != null && !s.isEmpty()){
				StringTokenizer st0 = new StringTokenizer( s, "," );
				d.add(new Datum(st0));
			}
		} while (s!=null);
		
		try {
			fstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
