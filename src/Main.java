import java.io.IOException;

import input.InputData;
import control.DSTM;
import strategy.DeterministicStrategy;
import strategy.Strategy;


public class Main {
	
	public static void main(String[] args) throws IOException {
		//Strategy s = new RandomStrategy();
		Strategy s= new DeterministicStrategy(new InputData(".\\testdata.csv"));
		for (int i = 0; i < 1; i++) {
			Thread t = new DSTM(s);
			t.start();
			while(t.isAlive()) {
				Thread.yield();
			}
			System.out.println("---END---");
		}
	}
	
}
