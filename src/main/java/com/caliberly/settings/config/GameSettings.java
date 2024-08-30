package com.caliberly.settings.config;

import com.caliberly.Symbol;
import com.caliberly.WinCombination;
import lombok.Getter;

import java.util.List;

@Getter
public class GameSettings {

	private final int rows;
	private final int columns;
	private final List<Symbol> symbols;
	private final List<WinCombination> winCombinations;

	public GameSettings(GameSettingsLoader gameSettingsLoader) {
		this.symbols = gameSettingsLoader.getSymbols();
		this.rows = gameSettingsLoader.getRows();
		this.columns = gameSettingsLoader.getColumns();
		this.winCombinations = gameSettingsLoader.getWinCombinations();
	}

}
