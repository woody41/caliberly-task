package com.caliberly.utils;

import com.caliberly.combinations.GeneralWinCombination;
import com.caliberly.combinations.WinCombination;
import com.caliberly.combinations.WinningCombination;
import com.caliberly.model.Symbol;
import com.caliberly.model.SymbolTile;

import java.util.ArrayList;
import java.util.List;

/**
 * Static util for combination checker
 */
public final class CombinationChecker {
	public static <T extends GeneralWinCombination> WinningCombination getSameSymbolResult(T combination, List<SymbolTile> gameArea, Symbol symbol) {
		List<SymbolTile> symbolTiles = new ArrayList<>(gameArea);
		symbolTiles.removeIf(tile -> !tile.getSymbol().equals(symbol));
		if (symbolTiles.size() == combination.getCount()) {
			return new WinningCombination((WinCombination) combination, symbol);
		}
		return null;
	}

}
