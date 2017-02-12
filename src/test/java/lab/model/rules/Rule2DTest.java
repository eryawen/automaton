package lab.model.rules;

import lab.model.exceptions.IllegalRuleFormatException;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Rule2DTest {

	@org.junit.Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void correctRules_DifferentCases() {
		Rule2D first = new Rule2D("2,6;8");
		Rule2D second = new Rule2D("256,6;");
		Rule2D third = new Rule2D(";8");
		Rule2D fourth = new Rule2D("2;0");
		Rule2D fifth = new Rule2D("2;01");

		assertEquals(first.getRuleForAliveCells(), Arrays.asList(2, 6));
		assertEquals(first.getRuleForDeadCells(), Arrays.asList(8));
		assertEquals(second.getRuleForAliveCells(), Arrays.asList(256, 6));
		assertEquals(second.getRuleForDeadCells(), Arrays.asList());
		assertEquals(third.getRuleForAliveCells(), Arrays.asList());
		assertEquals(third.getRuleForDeadCells(), Arrays.asList(8));
		assertEquals(fourth.getRuleForAliveCells(), Arrays.asList(2));
		assertEquals(fifth.getRuleForAliveCells(), Arrays.asList(2));
		assertEquals(fifth.getRuleForDeadCells(), Arrays.asList(1));
	}

	@Test
	public void incorrectRuleLetters_throwsException() {
		thrown.expect(IllegalRuleFormatException.class);
		Rule2D rule2D = new Rule2D("a,b;a");
	}

	@Test
	public void incorrectRuleAdditionalCharacter_throwsException() {
		thrown.expect(IllegalRuleFormatException.class);
		Rule2D rule2D = new Rule2D("5';4");
	}

	@Test
	public void incorrectRuleComaFirst_throwsException() {
		thrown.expect(IllegalRuleFormatException.class);
		Rule2D rule2D = new Rule2D(",5;4");
	}

	@Test
	public void incorrectRuleTooManyComas_throwsException() {
		thrown.expect(IllegalRuleFormatException.class);
		Rule2D rule2D = new Rule2D("5,,,,3;4");
	}
}