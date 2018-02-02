package coverageEvaluation;

import general.Machine;
import input.Datum;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import control.MManageTrain;



public class StateTransitionCoverage {
	
	
	private static FileOutputStream outputStream;
	private static String outputCoverageFolder;
	private static String outputCoverageFile;
	private static OutputStreamWriter outputStreamWriter;

	public static void openFile(String s1, String s2) {
		outputCoverageFolder=s1;
		outputCoverageFile=s2;
		try {
			outputStream = new FileOutputStream(outputCoverageFolder+"\\"+outputCoverageFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outputStreamWriter = new OutputStreamWriter(outputStream);
		return;	
	}
	
	public static void writeCoverage(String s) {
		try {
			outputStreamWriter.write(s+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void closeFile() {
		try {
			outputStreamWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void calculateCoverage(String folderName) {
		ArrayList<MachineCoverage> mc = new ArrayList<MachineCoverage>();		
		
		//Per ogni file coverage dal folder dato
			//Apri il file di coverage

		
		File folder = new File(folderName+"\\");
		File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].getName().contains("testcoverage")){
	    		FileInputStream fstream = null;
	    		
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(listOfFiles[i]));
	    		} catch (FileNotFoundException e) {
	    			e.printStackTrace();
	    		}
	    		//DataInputStream in = new DataInputStream(fstream);
	    		//BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    		String s=null;
	    		do{
	    			try {
	    				s=br.readLine();				
	    			} catch (IOException e) {
	    				s=null;
	    			}	
	    			if (s != null && !s.isEmpty()){
	    				StringTokenizer st0 = new StringTokenizer( s, "," );
	    				evaluateCoverageRow(st0,mc);
	    			}
	    		}while (s!=null);
	    		try {
	    			br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
		coverageReport(folderName,mc);
		
	}

	private static void coverageReport(String folderName,
			ArrayList<MachineCoverage> mc) {
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream(folderName+"\\coverageReport.csv");
			Writer       outputStreamWriter = new OutputStreamWriter(outputStream);
			for (MachineCoverage m : mc){
				outputStreamWriter.write("Machine : "+m.machineName+"\r\n");
				outputStreamWriter.write("State coverage : "+m.stateCoverage.size()+"/"+m.numberOfStates+" ("+(double)(100*m.stateCoverage.size()/m.numberOfStates)+"%)\r\n");
				outputStreamWriter.write("Transition coverage : "+m.transitionCoverage.size()+"/"+m.numberOfTransitions+" ("+(double)(100*m.transitionCoverage.size()/m.numberOfTransitions)+"%)\r\n");				
			}
			outputStreamWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

	private static void evaluateCoverageRow(StringTokenizer st0,
			ArrayList<MachineCoverage> mc) {
		//S#: setto il numero di stati di una macchina
		//T#: setto il numero di transizioni di una macchina
		//S: aumeno la copertura di uno stato di una macchina
		//T: aumento la copertura di uno stato di una macchina
		String machineName;
		String t=st0.nextToken();
		if (t.equals("Step"))
			return;
		machineName=t;
		t=st0.nextToken();
		if (t.equals("S#")){
			t=st0.nextToken();
			if (mc!=null)
				for (MachineCoverage m : mc){
					
					if (m.machineName.equals(machineName)){
						m.numberOfStates=Integer.parseInt(t);
						return;
					}
				}
				mc.add(new MachineCoverage(machineName,Integer.parseInt(t),0));
			return;
		}else if (t.equals("T#")){
			t=st0.nextToken();
			for (MachineCoverage m : mc){				
				if (m.machineName.equals(machineName)){
					m.numberOfTransitions=Integer.parseInt(t);
					return;
				}
			}
		}else if (t.equals("S")){
			t=st0.nextToken();
			for (MachineCoverage m : mc){
				if (m.machineName.equals(machineName)){
					if (m.stateCoverage.containsKey(t)){
						m.stateCoverage.put(t, m.stateCoverage.get(t) + 1);
						return;
					} else{
						m.stateCoverage.put(t, 1);
						return;
					}						
				}
			}
		}else if (t.equals("T")){
			t=st0.nextToken();
			for (MachineCoverage m : mc){
				if (m.machineName.equals(machineName)){
					if (m.transitionCoverage.containsKey(t)){
						m.transitionCoverage.put(t, m.transitionCoverage.get(t) + 1);
						return;
					} else{
						m.transitionCoverage.put(t, 1);
						return;
					}						
				}
			}
		}
		else return;
			
	}


}
