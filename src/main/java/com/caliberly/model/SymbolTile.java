package com.caliberly.model;

import lombok.Getter;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

@Getter
public class SymbolTile {
	private Symbol symbol;
	private final Point2D.Float coordinates;

	public SymbolTile(Point2D.Float coordinates, List<Symbol> availableSymbols) {
		this.coordinates = coordinates;
		this.generateSymbol(availableSymbols);
	}

	private void generateSymbol(List<Symbol> symbols) {
		Random random = new Random();
		this.symbol = symbols.get(random.nextInt(symbols.size()));
	}
}
