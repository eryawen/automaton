package lab.controller.enums;

import javafx.scene.paint.Color;

public enum Colour {
	BLACK, WHITE, RED, GREEN, BLUE, YELLOW;

	public Color getFXColor() {
		switch (this) {
			case BLACK:
				return Color.BLACK;
			case RED:
				return Color.RED;
			case GREEN:
				return Color.GREEN;
			case YELLOW:
				return Color.YELLOW;
			case BLUE:
				return Color.BLUE;
			default:
				return Color.WHITE;
		}
	}
}
