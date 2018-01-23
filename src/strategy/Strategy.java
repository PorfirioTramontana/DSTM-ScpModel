package strategy;

import general.ExternalChannel;
import general.Message;

public interface Strategy {
	
	Message generateMessage(ExternalChannel c, int stepNumber);
	
}
