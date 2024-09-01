package com.caliberly;

import com.caliberly.combinations.WinCombination;
import com.caliberly.model.SymbolTile;
import com.caliberly.settings.config.GameSettings;
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
		GameAreaPrinter.print(this.gameArea);
	}

	private void showResults() {
		for (WinCombination winCombination : this.settings.getWinCombinations()) {

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
