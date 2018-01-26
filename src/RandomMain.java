import java.io.File;
import java.io.IOException;

import input.InputData;
import control.DSTM;
import strategy.DeterministicStrategy;
import strategy.Strategy;
import testCaseGenerator.TestCaseGenerator;


public class RandomMain {
	
	public static void main(String[] args) throws IOException {
		//Strategy s = new RandomStrategy();
		InputData testBase=new InputData(".\\testdata.csv");
		int length=100;
		int numTestCase=1000;
		String technique="UniformRandom";
		String outputFolder=new String(".\\testsuite\\");
		new File(outputFolder).mkdir();
		//String outputFolder=new String("");
		TestCaseGenerator.generate(testBase,length,numTestCase,technique,outputFolder);
		
		for (int i=0;i<numTestCase;i++) {
			Strategy s= new DeterministicStrategy(new InputData(outputFolder+"testdata"+Integer.toString(i)+".csv"));
			
			for (int j = 0; j < 1; j++) {
				Thread t = new DSTM(s);
				t.start();
				while(t.isAlive()) {
					Thread.yield();
				}
				System.out.println("---END---");
			}
		}
	}
	
}
