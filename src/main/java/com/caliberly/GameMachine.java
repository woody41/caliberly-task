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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameMachine {
	protected static final Logger logger = LogManager.getLogger();

	private List<SymbolTile> gameArea = new ArrayList<>();
	private final GameSettings settings;

	public GameMachine(GameSettings gameSettings) {
		logger.info("Game machine dispatched");
		this.settings = gameSettings;
		//this line could be in a loop to keep playing
		this.startGame(1000);

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
				this.gameArea.add(new SymbolTile(coordinates, this.settings.getSymbols()));
			}
		}
	}

	private void checkResults() {
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
				//check for rest
				//TODO
			}
		}
		this.showResults(winningCombinations);
	}

	private void showResults(List<WinningCombination> winningCombinations) {
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
