package lab.model.rules;

import lab.model.exceptions.IllegalRuleFormatException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Rule2D implements Rule {
	private List<Integer> ruleForAliveCells = new ArrayList<>();
	private List<Integer> ruleForDeadCells = new ArrayList<>();
	
	public Rule2D(String rule) {
		rule = " " + rule + " ";
		String ruleForAlive = rule.split(";")[0];
		String ruleForDead = rule.split(";")[1];
		
		ruleForAlive = ruleForAlive.replaceAll(" ", "");
		ruleForDead = ruleForDead.replaceAll(" ", "");
		
		Pattern pattern = Pattern.compile("^\\d+(,\\d+)*$");
		
		if (!ruleForAlive.equals("")) {
			Matcher matcherBorn = pattern.matcher(ruleForAlive);
			if (!matcherBorn.find()) {
				throw new IllegalRuleFormatException("Wrong rule format. Example: Born: 2,3  Dead 3");
			}
			ruleForAlive = ruleForAlive + ",";
		}
		if (!ruleForDead.equals("")) {
			Matcher matcherDead = pattern.matcher(ruleForDead);
			if (!matcherDead.find()) {
				throw new IllegalRuleFormatException("Wrong rule format. Example: Born: 2,3  Dead 3");
			}
			ruleForDead = ruleForDead + ",";
		}
		
		int positionInRuleString = 0;
		String currentRule = "";
		
		while (positionInRuleString < ruleForAlive.length()) {
			if (ruleForAlive.charAt(positionInRuleString) == ',') {
				ruleForAliveCells.add(Integer.valueOf(currentRule));
				positionInRuleString++;
				currentRule = "";
				continue;
			}
			currentRule = currentRule + ruleForAlive.charAt(positionInRuleString);
			positionInRuleString++;
		}
		
		positionInRuleString = 0;
		currentRule = "";
		
		while (positionInRuleString < ruleForDead.length()) {
			if (ruleForDead.charAt(positionInRuleString) == ',') {
				ruleForDeadCells.add(Integer.valueOf(currentRule));
				positionInRuleString++;
				currentRule = "";
				continue;
			}
			currentRule = currentRule + ruleForDead.charAt(positionInRuleString);
			positionInRuleString++;
		}
	}
}

