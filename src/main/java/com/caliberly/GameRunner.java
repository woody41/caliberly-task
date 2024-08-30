package com.caliberly;

import com.caliberly.settings.config.GameSettings;
import com.caliberly.settings.config.GameSettingsLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class GameRunner {

	public static void main(String[] args) {
		Configurator.setRootLevel(Level.INFO);
		GameSettings gameSettings = new GameSettings(new GameSettingsLoader("config.json"));
		new GameMachine(gameSettings);
	}

}