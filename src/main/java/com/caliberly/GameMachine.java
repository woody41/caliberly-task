package com.caliberly;

import com.caliberly.combinations.WinCombination;
import com.caliberly.combinations.WinningCombination;
import com.caliberly.enums.CombinationWhen;
import com.caliberly.enums.SymbolType;
import com.caliberly.model.Symbol;
import com.caliberly.model.SymbolTile;
import com.caliberly.settings.config.GameSettings;
import com.caliberly.utils.CombinationChecker;
import com.caliberly.utils.GameAreaPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.Point2D;
import java.util.*;

public class GameMachine {
	protected static final Logger logger = LogManager.getLogger();

	private Map<Point2D.Float, SymbolTile> gameArea = new HashMap<>();
	private final GameSettings settings;

	public GameMachine(GameSettings gameSettings, int amount) {
		logger.info("Game machine dispatched");
		this.settings = gameSettings;
		//this line could be in a loop to keep playing
		this.startGame(amount);

		this.gameEnd();
	}

	public void startGame(int amount) {
		this.generateArea();
		GameAreaPrinter.print(this.gameArea); // visualization of game area
		this.checkResults();
	}

	/**
	 * Simple game area generation based on settings
	 */
	private void generateArea() {
		for (int row = 0; row < this.settings.getRows(); row++) { //loop for generating rows
			for (int column = 0; column < this.settings.getColumns(); column++) { //loop for generating columns
				Point2D.Float coordinates = new Point2D.Float();
				coordinates.setLocation(row, column);
				Map<String, Integer> availableSymbols = new HashMap<>(this.settings.getPointSymbolsProbabilities().get(new Point2D.Float(row, column)));
				availableSymbols.putAll(this.settings.getSymbolsProbabilities());
				this.gameArea.put(coordinates, new SymbolTile(coordinates, this.settings.getSymbols(), availableSymbols));
			}
		}
	}

	private List<WinningCombination> checkResults() {
		List<WinningCombination> winningCombinations = new LinkedList<>();
		for (WinCombination winCombination : this.settings.getWinCombinations()) {
			if (winCombination.getWhen().equals(CombinationWhen.SAME_SYMBOL)) {
				//check for same symbol
				for (Symbol symbol : settings.getSymbols()) {
					if (symbol.getSymbolType().equals(SymbolType.STANDARD)) {
						WinningCombination combination = CombinationChecker.getSameSymbolResult(winCombination, gameArea, symbol);
						if (combination != null) {
							winningCombinations.add(combination);
						}
					}
				}
			}
			if (winCombination.getWhen().equals(CombinationWhen.LINEAR_SYMBOL)) {
				winningCombinations.addAll(CombinationChecker.getLinearSymbolResult(winCombination, gameArea));
			}
		}
		this.showResults(winningCombinations);
		return winningCombinations;
	}

	private void showResults(List<WinningCombination> winningCombinations) {
		if (winningCombinations.isEmpty()) {
			logger.debug("No winning combinations");
		}
		for (WinningCombination winningCombination : winningCombinations) {
			logger.debug(winningCombination.getName() + " is winning combinations. Winning symbol: " + winningCombination.getSymbol().getName());
		}
	}

	/**
	 * Not necessary, for future purpose
	 */
	private void gameEnd() {
		logger.info("Game over");
		System.exit(1);
	}
}
