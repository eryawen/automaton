package lab.controller.enums;

public enum AutomatonType {
	GAME_OF_LIFE("Game of Life"), QUADLIFE("Quadlife"), LANGTON_ANT("Langton Ant"), WIREWORLD(
		   "Wireworld"), ONE_DIMENSIONAL("One-dimensional");
	
	private final String toString;
	
	AutomatonType(String s) {
		toString = s;
	}
	
	@Override
	public String toString() {
		return toString;
	}
}
