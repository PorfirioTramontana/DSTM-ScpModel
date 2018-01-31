package coverageEvaluation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;



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
	
	public static void calculateStateCoverage() {
		//DEVO AVERE IL NUMERO/ELENCO COMPLETO DEGLI STATI/COVERAGE
	}

	public static void calculateTransitionCoverage() {
		
	}
}
