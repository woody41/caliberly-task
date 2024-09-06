package com.caliberly.utils;

import com.caliberly.combinations.GeneralWinCombination;
import com.caliberly.combinations.WinCombination;
import com.caliberly.combinations.WinningCombination;
import com.caliberly.enums.SymbolType;
import com.caliberly.exceptions.NoWinningCombinationException;
import com.caliberly.model.Symbol;
import com.caliberly.model.SymbolTile;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Static util for combination checker
 */
public final class CombinationChecker {

	static final Logger logger = LogManager.getLogger(CombinationChecker.class);

	private CombinationChecker() {
	}

	public static <T extends GeneralWinCombination> WinningCombination getSameSymbolResult(T combination, Map<Point2D.Float, SymbolTile> gameArea, Symbol symbol) {
		Map<Point2D.Float, SymbolTile> symbolTiles = new HashMap<>(gameArea);
		symbolTiles.entrySet().removeIf(tile -> !tile.getValue().getSymbol().equals(symbol));
		if (symbolTiles.size() == combination.getCount()) {
			return new WinningCombination((WinCombination) combination, symbol);
		}
		return null;
	}

	public static <T extends GeneralWinCombination> List<WinningCombination> getLinearSymbolResult(T combination, Map<Point2D.Float, SymbolTile> gameArea) {
		List<WinningCombination> winningCombinations = new LinkedList<>();
		//Loop for all covered areas, one winning combinations could be there twice with different symbols
		for (List<String> area : combination.getCovered_areas()) {

			try {

				Symbol currentSymbol = null;
				for (Point2D.Float coords : getCoveredArea(area)) {
					if (currentSymbol == null && gameArea.get(coords).getSymbol().getSymbolType().equals(SymbolType.STANDARD)) {
						currentSymbol = gameArea.get(coords).getSymbol();
					} else if (!gameArea.get(coords).getSymbol().equals(currentSymbol)) {
						throw new NoWinningCombinationException();
					}
				}
				if (currentSymbol == null) {
					//this could happen if there are only bonuses
					throw new NoWinningCombinationException();
				}
				winningCombinations.add(new WinningCombination(combination, currentSymbol));
			} catch (NoWinningCombinationException e) {
				//Not winning combination
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				logger.error("ERROR - Config file is probably corrupted:");
				logger.error(e.getMessage());
			}
		}
		return winningCombinations;

	}

	/**
	 * Processing X:Y to Point2D.Float
	 *
	 * @param areaList
	 * @return
	 */
	private static List<Point2D.Float> getCoveredArea(List<String> areaList) throws NumberFormatException, IndexOutOfBoundsException {
		List<Point2D.Float> area = new LinkedList<>();
		for (String pointString : areaList) {
			String[] pointArray = pointString.split(":");
			area.add(new Point2D.Float(Float.parseFloat(pointArray[0]), Float.parseFloat(pointArray[1])));
		}
		return area;
	}

}
