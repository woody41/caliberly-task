package com.caliberly;

import com.caliberly.settings.config.GameSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GameMachine {
	protected static final Logger logger = LogManager.getLogger();

	private List<List<Symbol>> gameArea;

	public GameMachine(GameSettings gameSettings) {
		logger.info("Game machine dispatched");
		logger.debug(String.format("Settings rows: %d", gameSettings.getRows()));
		logger.debug(String.format("Settings columns: %d", gameSettings.getColumns()));

		this.gameEnd();
	}

	public void generateArea() {
		//generate area based on settings and probabilities
	}

	/**
	 * Not necessary, for future purpose
	 */
	private void gameEnd() {
		logger.info("Game over");
		System.exit(1);
	}
}
