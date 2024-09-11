package com.caliberly;

import com.caliberly.settings.config.GameSettings;
import com.caliberly.settings.config.GameSettingsLoaderFile;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class GameRunner {
	protected static final Logger logger = LogManager.getLogger(GameRunner.class);

	public static void main(String[] args) {
		Configurator.setRootLevel(Level.DEBUG);

		String configFilePath;
		int bettingAmountValue;

		Options options = new Options();

		Option config = new Option("c", "config", true, "configuration file path");
		config.setRequired(true);
		options.addOption(config);

		Option bettingAmount = new Option("b", "betting-amount", true, "betting amount");
		bettingAmount.setRequired(true);
		options.addOption(bettingAmount);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();

		try {
			CommandLine cmd = parser.parse(options, args);

			configFilePath = cmd.getOptionValue("config");
			bettingAmountValue = Integer.parseInt(cmd.getOptionValue("betting-amount"));

			logger.debug("Loaded param config: {}", configFilePath);
			logger.debug("Loaded param betting amount: {}", bettingAmountValue);

		} catch (ParseException e) {
			logger.error(e.getMessage());
			formatter.printHelp("utility-name", options);
			return;
		}


		GameSettings gameSettings = new GameSettings(new GameSettingsLoaderFile(configFilePath));
		new GameMachine(gameSettings, bettingAmountValue);
	}

}