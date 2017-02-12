package lab.model.exceptions;

public class CellBeyondBoardException extends RuntimeException {
	public CellBeyondBoardException() {
	}

	public CellBeyondBoardException(String message) {
		super(message);
	}
}
