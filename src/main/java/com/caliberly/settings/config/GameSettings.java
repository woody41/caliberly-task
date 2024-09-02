package com.caliberly.settings.config;

import com.caliberly.model.Symbol;
import com.caliberly.combinations.WinCombination;
import lombok.Getter;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

@Getter
public class GameSettings {

	private final int rows;
	private final int columns;
	private final List<Symbol> symbols;
	private final List<WinCombination> winCombinations;
	private final Map<Point2D.Float, Map<String, Integer>> pointSymbolsProbabilities;
	private final Map<String, Integer> symbolsProbabilities;

	public GameSettings(GameSettingsLoader gameSettingsLoader) {
		this.symbols = gameSettingsLoader.getSymbols();
		this.rows = gameSettingsLoader.getRows();
		this.columns = gameSettingsLoader.getColumns();
		this.winCombinations = gameSettingsLoader.getWinCombinations();
		this.pointSymbolsProbabilities = gameSettingsLoader.getCoordsBasedProbabilities();
		this.symbolsProbabilities = gameSettingsLoader.getSymbolBasedProbabilities();
	}

}
