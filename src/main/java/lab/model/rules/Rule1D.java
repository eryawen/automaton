package lab.model.rules;

import lab.model.exceptions.IllegalRuleFormatException;
import lab.model.states.BinaryState;

import java.util.HashMap;
import java.util.Map;

public class Rule1D implements Rule {
	private Map<String, Integer> nextStates = new HashMap<>();
	private String ruleInBinary;
	
	public Rule1D(String rule) {
		Integer ruleAsInteger;
		try {
			ruleAsInteger = Integer.parseInt(rule);
		} catch (NumberFormatException e) {
			throw new IllegalRuleFormatException("Rule must be integer between 30 and 255");
		}
		if (ruleAsInteger < 30 || ruleAsInteger > 255) {
			throw new IllegalRuleFormatException("Rule must be integer between 30 and 255");
		}
		
		ruleInBinary = String.format("%8s", Integer.toBinaryString(ruleAsInteger)).replace(' ', '0');
		
		nextStates.put("111", getIntValue(0));
		nextStates.put("110", getIntValue(1));
		nextStates.put("101", getIntValue(2));
		nextStates.put("100", getIntValue(3));
		nextStates.put("011", getIntValue(4));
		nextStates.put("010", getIntValue(5));
		nextStates.put("001", getIntValue(6));
		nextStates.put("000", getIntValue(7));
	}
	
	public int getIntValue(int i) {
		return Character.getNumericValue(ruleInBinary.charAt(i));
	}
	
	public BinaryState getNextBinaryState(String cellsStates) {
		return (nextStates.get(cellsStates) == 0) ? BinaryState.DEAD : BinaryState.AlIVE;
	}
	
	public Map<String, Integer> getNextStates() {
		return nextStates;
	}
}
