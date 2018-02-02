import java.io.File;
import java.io.IOException;

import input.InputData;
import control.DSTM;
import coverageEvaluation.StateTransitionCoverage;
import strategy.DeterministicStrategy;
import strategy.Strategy;
import testCaseGenerator.TestCaseGenerator;


public class RandomMain {
	
	public static void main(String[] args) throws IOException {
		//Strategy s = new RandomStrategy();
		InputData testBase=new InputData(".\\testdata.csv");
		int length=1000;
		int numTestCase=100;
		String technique="UniformRandom";
		String outputFolder=new String(".\\testsuite\\");
		String outputCoverageFolder= new String(".\\coverage");
		new File(outputFolder).mkdir();
		new File(outputCoverageFolder).mkdir();
		//String outputFolder=new String("");
		TestCaseGenerator.generate(testBase,length,numTestCase,technique,outputFolder);
		
		for (int i=0;i<numTestCase;i++) {
			StateTransitionCoverage.openFile(outputCoverageFolder,"testcoverage"+Integer.toString(i)+".csv");
			Strategy s= new DeterministicStrategy(new InputData(outputFolder+"testdata"+Integer.toString(i)+".csv"));
			
			for (int j = 0; j < 1; j++) {
				Thread t = new DSTM(s);
				t.start();
				while(t.isAlive()) {
					Thread.yield();
				}
				System.out.println("---END---");
			}
			StateTransitionCoverage.closeFile();
		}
		StateTransitionCoverage.calculateCoverage(outputCoverageFolder);
	}
	
}
