package lab.model.coords;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public final class Coords2D implements CellCoordinates {
	private final int x;
	private final int y;
}
