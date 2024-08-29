package com.caliberly;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class GameRunner {
	protected static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		Configurator.setRootLevel(Level.INFO);

		logger.info("Hello world!");
		logger.info(args.toString());
	}
}