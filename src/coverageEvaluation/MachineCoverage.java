package coverageEvaluation;

import java.util.HashMap;

public class MachineCoverage {

	String machineName;
	
	Integer numberOfStates;
	Integer numberOfTransitions;
	
	HashMap<String,Integer> stateCoverage;
	HashMap<String,Integer> transitionCoverage;
	
	MachineCoverage(){
		numberOfStates=0;
		numberOfTransitions=0;
	}

	public MachineCoverage(String machineName2, int parseInt, int i) {
		machineName=machineName2;
		numberOfStates=parseInt;
		numberOfTransitions=i;
		stateCoverage=new HashMap<String,Integer>();
		transitionCoverage=new HashMap<String,Integer>();
	}
	
}
