package lab.controller.enums;

public enum NeighbourhoodType {
	MOORE("Moore"), VON_NEUMANN("Von Neumann"), ONE_DIMENSIONAL("One-dimensional");
	
	private final String toString;
	
	NeighbourhoodType(String toString) {
		this.toString = toString;
	}
	
	@Override
	public String toString() {
		return toString;
	}
}
