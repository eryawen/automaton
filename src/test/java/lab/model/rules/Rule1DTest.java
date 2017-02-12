package lab.model.rules;

import lab.model.exceptions.IllegalRuleFormatException;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Rule1DTest {
	@org.junit.Rule
	public ExpectedException thrown = ExpectedException.none();
	Map<String, Integer> nextStates;

	public void initMap(int a, int b, int c, int d, int e, int f, int g, int h) {
		nextStates = new HashMap<>();
		nextStates.put("111", a);
		nextStates.put("110", b);
		nextStates.put("101", c);
		nextStates.put("100", d);
		nextStates.put("011", e);
		nextStates.put("010", f);
		nextStates.put("001", g);
		nextStates.put("000", h);
	}

	@Test
	public void Rule1D_51() throws Exception {
		Rule1D rule = new Rule1D("51");
		initMap(0, 0, 1, 1, 0, 0, 1, 1);
		assertEquals(rule.getNextStates(), nextStates);
	}

	@Test
	public void Rule1D_30() throws Exception {
		Rule1D rule = new Rule1D("30");
		initMap(0, 0, 0, 1, 1, 1, 1, 0);
		assertEquals(rule.getNextStates(), nextStates);
	}

	@Test
	public void Rule1D_90() throws Exception {
		Rule1D rule = new Rule1D("90");
		initMap(0, 1, 0, 1, 1, 0, 1, 0);
		assertEquals(rule.getNextStates(), nextStates);
	}

	@Test
	public void Rule1D_RadiusIsTooBig() {
		thrown.expect(IllegalRuleFormatException.class);
		Rule1D rule = new Rule1D("256");
	}

	@Test
	public void Rule1D_RadiusIsNotInteger() {
		thrown.expect(IllegalRuleFormatException.class);
		Rule1D rule = new Rule1D("30.6");
	}

	@Test
	public void Rule1D_RadiusIsNotNumber() {
		thrown.expect(IllegalRuleFormatException.class);
		Rule1D rule = new Rule1D("j");
	}
}
