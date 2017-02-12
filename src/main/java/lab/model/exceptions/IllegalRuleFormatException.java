package lab.model.exceptions;

public class IllegalRuleFormatException extends RuntimeException {
	public IllegalRuleFormatException() {
	}
	
	public IllegalRuleFormatException(String message) {
		super(message);
	}
}
