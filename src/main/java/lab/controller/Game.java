package lab.controller;

import javafx.scene.control.Alert;
import lab.controller.controllers.BaseGameController;
import lab.controller.controllers.Controller2D;
import lab.controller.controllers.GameOfLifeController;
import lab.controller.controllers.OneDimController;
import lab.controller.enums.AutomatonType;
import lab.controller.enums.GameState;
import lab.controller.enums.NeighbourhoodType;
import lab.model.automatons.*;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.cellStateFactories.GeneralStateFactory;
import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords1D;
import lab.model.coords.Coords2D;
import lab.model.exceptions.IllegalRadiusValueException;
import lab.model.exceptions.IllegalRuleFormatException;
import lab.model.neighbourhoods.CellNeighbourhood;
import lab.model.neighbourhoods.MooreNeighbourhood;
import lab.model.neighbourhoods.Neighbourhood1D;
import lab.model.neighbourhoods.VonNeumanNeighbourhood;
import lab.model.rules.Rule;
import lab.model.rules.Rule1D;
import lab.model.rules.Rule2D;
import lab.model.states.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static lab.controller.enums.AutomatonType.ONE_DIMENSIONAL;

public class Game {
	private final static Random RANDOM = new Random();
	BaseGameController baseControler;
	@Getter
	@Setter
	private GameState gameState = GameState.STOPPED;
	@Setter
	@Getter
	private boolean isInitialized = false;
	
	private Automaton automaton;
	private AutomatonType automatonType;
	private CellStateFactory cellStateFactory;
	private int cols;
	private int rows;
	
	public Game(BaseGameController baseControler) {
		this.baseControler = baseControler;
	}
	
	public void init() {
		getGameDataFromUser();
		if (isInitialized) {
			gameState = GameState.STARTED;
		}
	}
	
	public void getGameDataFromUser() {
		automatonType = baseControler.getAutomatonType();
		
		Rule rule = null;
		try {
			if (automatonType == automatonType.GAME_OF_LIFE) {
				rule = createRuleFromString(((GameOfLifeController) baseControler).getRule());
			}
			if (automatonType == automatonType.ONE_DIMENSIONAL) {
				rule = createRuleFromString(((OneDimController) baseControler).getRule());
			}
		} catch (IllegalRuleFormatException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			return;
		}
		
		cols = baseControler.getNumberOfColsInAutomaton();
		rows = baseControler.getNumberOfRowsInAutomaton();
		
		NeighbourhoodType neighbourhoodType = baseControler.getNeighbourhoodKind();
		boolean wrapping = baseControler.withWrapping();
		
		int radius;
		CellNeighbourhood neighbourhood = null;
		
		if (automatonType != ONE_DIMENSIONAL) {
			
			try {
				radius = Integer.parseInt(((Controller2D) baseControler).getRadius());
				neighbourhood = this.createNeighbourhood(neighbourhoodType, wrapping, radius);
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Radius must be integer greater than 0");
				alert.showAndWait();
				return;
			} catch (IllegalRadiusValueException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				return;
			}
		} else {
			neighbourhood = createNeighbourhood(neighbourhoodType, wrapping, 1);
		}
		
		boolean stateIsRandom = baseControler.isStateRandom();
		cellStateFactory = (stateIsRandom) ? getRandomBoard() : baseControler.getStateFactory();
		
		createAutomaton(neighbourhood, rule, cellStateFactory);
	}
	
	private GeneralStateFactory getRandomBoard() {
		switch (automatonType) {
			case ONE_DIMENSIONAL:
				return getFactoryWithRandomBoard1D();
			case LANGTON_ANT:
				return getFactoryWithAntsInRandomCells();
			default:
				return getFactoryWithRandomBoard2D();
		}
	}
	
	private CellNeighbourhood createNeighbourhood(NeighbourhoodType neighbourhoodType, boolean wrapping, int radius) {
		switch (neighbourhoodType) {
			case ONE_DIMENSIONAL:
				return new Neighbourhood1D(wrapping, cols);
			case VON_NEUMANN:
				return new VonNeumanNeighbourhood(wrapping, rows, cols, radius);
			default:
				return new MooreNeighbourhood(wrapping, rows, cols, radius);
		}
	}
	
	public void createAutomaton(CellNeighbourhood neighbourhood, Rule rule, CellStateFactory cellStateFactory) {
		switch (automatonType) {
			case QUADLIFE:
				automaton = new QuadLife(neighbourhood, rows, cols, cellStateFactory);
				break;
			case GAME_OF_LIFE:
				automaton = new GameOfLife(neighbourhood, (Rule2D) rule, rows, cols, cellStateFactory);
				break;
			case WIREWORLD:
				automaton = new WireWorld(neighbourhood, rows, cols, cellStateFactory);
				break;
			case LANGTON_ANT:
				automaton = new LangtonAnt(neighbourhood, rows, cols, cellStateFactory);
				break;
			case ONE_DIMENSIONAL:
				automaton = new Automaton1DimImpl(neighbourhood, cols, (Rule1D) rule, cellStateFactory);
		}
		automaton.init();
		baseControler.setAutomaton(automaton);
		isInitialized = true;
	}
	
	public GeneralStateFactory getFactoryWithRandomBoard2D() {
		GeneralStateFactory stateFactory = new GeneralStateFactory();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				CellState cellState = getRandomState();
				stateFactory.add(new Coords2D(i, j), cellState);
			}
		}
		return stateFactory;
	}
	
	public GeneralStateFactory getFactoryWithRandomBoard1D() {
		GeneralStateFactory stateFactory = new GeneralStateFactory();
		for (int j = 0; j < cols; j++) {
			BinaryState binaryState = (BinaryState) getRandomState();
			stateFactory.add(new Coords1D(j), binaryState);
		}
		return stateFactory;
	}
	
	public CellState getRandomState() {
		List<BinaryState> binaryStateValues = Arrays.asList(BinaryState.values());
		List<WireElectronState> wireElectronStateValues = Arrays.asList(WireElectronState.values());
		List<QuadState> quadStateValues = Arrays.asList(QuadState.values());
		
		switch (automatonType) {
			case QUADLIFE:
				return quadStateValues.get(RANDOM.nextInt(quadStateValues.size()));
			case WIREWORLD:
				return wireElectronStateValues.get(RANDOM.nextInt(wireElectronStateValues.size()));
			default:
				return binaryStateValues.get(RANDOM.nextInt(binaryStateValues.size()));
		}
	}
	
	public GeneralStateFactory getFactoryWithAntsInRandomCells() {
		List<AntState> antStateValues = Arrays.asList(AntState.NORTH, AntState.EAST, AntState.SOUTH, AntState.WEST);
		
		int numberOfAnts = RANDOM.nextInt(3) + 1;
		
		Map<CellCoordinates, CellState> initialStates = new HashMap<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				initialStates.put(new Coords2D(i, j), new LangtonCellState(BinaryState.DEAD, new HashSet<>()));
			}
		}
		
		AntState randomAntState;
		for (int i = 0; i < numberOfAnts; i++) {
			Coords2D randomCoords = randCoords2D();
			randomAntState = antStateValues.get(RANDOM.nextInt(antStateValues.size()));
			HashSet<LangtonCellState.Ant> ants = new HashSet<>();
			ants.add(new LangtonCellState.Ant(randomAntState));
			initialStates.put(randomCoords, new LangtonCellState(BinaryState.DEAD, ants));
		}
		
		return new GeneralStateFactory(initialStates);
	}
	
	public Coords2D randCoords2D() {
		int x = RANDOM.nextInt(rows - 1);
		int y = RANDOM.nextInt(cols - 1);
		return new Coords2D(x, y);
	}
	
	private Rule createRuleFromString(String rule) {
		return (automatonType == ONE_DIMENSIONAL) ? new Rule1D(rule) : new Rule2D(rule);
	}
}
