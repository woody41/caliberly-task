package com.caliberly.utils;

import com.caliberly.model.Symbol;
import com.caliberly.model.SymbolTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class GameAreaPrinter {
	protected static final Logger logger = LogManager.getLogger();

	/**
	 * @param tiles = current game area
	 */
	public static void print(Map<Point2D.Float, SymbolTile> tiles) {
		float row = 0;
		float col = 0;
		for (Map.Entry<Point2D.Float, SymbolTile> tile : tiles.entrySet()) {
			row = Math.max(tile.getValue().getCoordinates().x, row);
			col = Math.max(tile.getValue().getCoordinates().y, col);
		}
		print(tiles, (int) row, (int) col);
	}

	public static void print(Map<Point2D.Float, SymbolTile> tiles, int rows, int cols) {
		for (int row = 0; row <= rows; row++) {
			System.out.println("-------------------------------");
			for (int col = 0; col <= cols; col++) {
				System.out.print("| ");
				try {
					System.out.print(getSymbolTileByCoords(tiles, row, col).getSymbol().getName() + " ");
				} catch (NoSuchElementException e) {
					logger.error("Game area is not either square or rectangle or accessing area out of bounds");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}

	private static SymbolTile getSymbolTileByCoords(Map<Point2D.Float, SymbolTile> symbolTiles, int row, int col) throws NoSuchElementException {
		if (symbolTiles.containsKey(new Point2D.Float(row, col))) {
			return symbolTiles.get(new Point2D.Float(row, col));
		} else throw new NoSuchElementException();
	}
}
