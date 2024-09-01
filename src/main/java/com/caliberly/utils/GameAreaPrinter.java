package com.caliberly.utils;

import com.caliberly.model.SymbolTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.NoSuchElementException;

public class GameAreaPrinter {
	protected static final Logger logger = LogManager.getLogger();

	/**
	 * @param tiles = current game area
	 */
	public static void print(List<SymbolTile> tiles) {
		float row = 0;
		float col = 0;
		for (SymbolTile tile : tiles) {
			row = Math.max(tile.getCoordinates().x, row);
			col = Math.max(tile.getCoordinates().y, col);
		}
		print(tiles, (int) row, (int) col);
	}

	public static void print(List<SymbolTile> tiles, int rows, int cols) {
		for (int row = 0; row <= rows; row++) {
			System.out.println("-------------------------------");
			for (int col = 0; col <= cols; col++) {
				System.out.print("| ");
				try {
					getSymbolTileByCoords(tiles, row, col);
					System.out.print(getSymbolTileByCoords(tiles, row, col).getSymbol().getName() + " ");
				} catch (NoSuchElementException e) {
					logger.error("Game area is not either square or rectangle or accessing area out of bounds");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}

	private static SymbolTile getSymbolTileByCoords(List<SymbolTile> symbolTiles, int row, int col) throws NoSuchElementException {
		return symbolTiles.stream().filter(t -> t.getCoordinates().x == row && t.getCoordinates().y == col).findFirst().orElseThrow();
	}
}
