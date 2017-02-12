package lab.model.exceptions;

public class IllegalRadiusValueException extends RuntimeException {
	public IllegalRadiusValueException() {
	}

	public IllegalRadiusValueException(String message) {
		super(message);
	}
}
