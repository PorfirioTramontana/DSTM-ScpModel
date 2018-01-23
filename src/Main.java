import java.io.IOException;

import input.InputData;
import control.DSTM;
import strategy.DeterministicStrategy;
import strategy.Strategy;
import testCaseGenerator.TestCaseGenerator;


public class Main {
	
	public static void main(String[] args) throws IOException {		
			Strategy s= new DeterministicStrategy(new InputData(".\\testdata.csv"));
			
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
