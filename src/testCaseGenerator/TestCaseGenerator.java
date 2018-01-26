/**
 * 
 */
package testCaseGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Random;

import input.InputData;

/**
 * @author Porfirio
 *
 */
public class TestCaseGenerator {

	public TestCaseGenerator() {
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void generate(InputData testBase, int length, int numTestCase, String technique,
			String outputFolder) {
		if (technique.equals("UniformRandom")) {
			//TODO: Esegui un ciclo su numTestCase
				//apri un file di output
				//esegui un ciclo su length
					//aggiungi un test a caso da testBase
				//chiudi file di output
			for (int i=0;i<numTestCase;i++) {
				OutputStream outputStream;
				try {
					outputStream = new FileOutputStream(outputFolder+"testdata"+Integer.toString(i)+".csv");
					Writer       outputStreamWriter = new OutputStreamWriter(outputStream);
					outputStreamWriter.write("eventType,msgField1_1,msgField2_1,msgField3_1,msgField4_1,msgField1_2,msgField2_2,msgField1_3,msgField2_3\r\n");
					for (int j=0;j<length;j++) {
						Random r = new Random();
						int choice = r.nextInt(testBase.d.size());
						outputStreamWriter.write(testBase.d.get(choice).toString()+"\r\n");
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
		}
		
	}



}
