package com.caliberly;

import com.caliberly.settings.config.GameSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameMachine {
	protected static final Logger logger = LogManager.getLogger();

	public GameMachine(GameSettings gameSettings) {
		logger.info("Game machine dispatched");
		logger.debug("Settings rows: " + gameSettings.getRows());
		logger.debug("Settings columns: " + gameSettings.getColumns());

		this.gameEnd();
	}

	/**
	 * Not necessary, for future purpose
	 */
	private void gameEnd() {
		logger.info("Game over");
		System.exit(1);
	}
}
