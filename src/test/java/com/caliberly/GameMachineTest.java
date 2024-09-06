package com.caliberly;

import com.caliberly.combinations.WinCombination;
import com.caliberly.combinations.WinningCombination;
import com.caliberly.enums.CombinationWhen;
import com.caliberly.enums.SymbolType;
import com.caliberly.model.Symbol;
import com.caliberly.model.SymbolTile;
import com.caliberly.settings.config.GameSettings;
import com.caliberly.settings.config.GameSettingsLoader;
import com.caliberly.utils.CombinationChecker;
import com.caliberly.utils.GameAreaPrinter;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GameMachineTest {

	private Map<Point2D.Float, SymbolTile> gameArea = new HashMap<>();
	private GameSettings settings;

	public GameMachineTest() {
		this.settings = new GameSettings(new GameSettingsLoader("config.json"));
		this.generateArea();
	}

	/**
	 * generating area:
	 * A | A | A
	 * B | A | +1000
	 * C | C | A
	 */
	private void generateArea() {
		Map<String, Integer> propbability = new HashMap<>();
		propbability.put("A", 1);
		Point2D.Float coords = new Point2D.Float(0, 0);
		SymbolTile symbolTile = new SymbolTile(coords,
				List.of(new Symbol("A", SymbolType.STANDARD, 5, 0)), propbability);
		this.gameArea.put(coords, symbolTile);

		propbability = new HashMap<>();
		propbability.put("A", 1);
		coords = new Point2D.Float(0, 1);
		symbolTile = new SymbolTile(coords,
				List.of(new Symbol("A", SymbolType.STANDARD, 5, 0)), propbability);
		this.gameArea.put(coords, symbolTile);

		propbability = new HashMap<>();
		propbability.put("A", 1);
		coords = new Point2D.Float(0, 2);
		symbolTile = new SymbolTile(coords,
				List.of(new Symbol("A", SymbolType.STANDARD, 5, 0)), propbability);
		this.gameArea.put(coords, symbolTile);

		propbability = new HashMap<>();
		propbability.put("B", 1);
		coords = new Point2D.Float(1, 0);
		symbolTile = new SymbolTile(coords,
				List.of(new Symbol("B", SymbolType.STANDARD, 3, 0)), propbability);
		this.gameArea.put(coords, symbolTile);

		propbability = new HashMap<>();
		propbability.put("A", 1);
		coords = new Point2D.Float(1, 1);
		symbolTile = new SymbolTile(coords,
				List.of(new Symbol("A", SymbolType.STANDARD, 5, 0)), propbability);
		this.gameArea.put(coords, symbolTile);

		propbability = new HashMap<>();
		propbability.put("+1000", 1);
		coords = new Point2D.Float(1, 2);
		symbolTile = new SymbolTile(coords,
				List.of(new Symbol("+1000", SymbolType.BONUS, 1, 1000)), propbability);
		this.gameArea.put(coords, symbolTile);

		propbability = new HashMap<>();
		propbability.put("C", 1);
		coords = new Point2D.Float(2, 0);
		symbolTile = new SymbolTile(coords,
				List.of(new Symbol("C", SymbolType.STANDARD, 2.5, 0)), propbability);
		this.gameArea.put(coords, symbolTile);

		propbability = new HashMap<>();
		propbability.put("C", 1);
		coords = new Point2D.Float(2, 1);
		symbolTile = new SymbolTile(coords,
				List.of(new Symbol("C", SymbolType.STANDARD, 2.5, 0)), propbability);
		this.gameArea.put(coords, symbolTile);

		propbability = new HashMap<>();
		propbability.put("A", 1);
		coords = new Point2D.Float(2, 2);
		symbolTile = new SymbolTile(coords,
				List.of(new Symbol("A", SymbolType.STANDARD, 5, 0)), propbability);
		this.gameArea.put(coords, symbolTile);

		GameAreaPrinter.print(this.gameArea);
	}

	/**
	 * This test is testing loading of settings and testing mock game area. This basically cover huge part of everything
	 */
	@Test
	public void CheckWinningCombinations() {
		assertEquals("same_symbol_5_times", this.settings.getWinCombinations().stream().filter(c -> c.getName().equals("same_symbol_5_times")).findFirst().orElseThrow().getName());
		assertEquals("same_symbols_horizontally", this.settings.getWinCombinations().stream().filter(c -> c.getName().equals("same_symbols_horizontally")).findFirst().orElseThrow().getName());
		assertEquals("same_symbols_diagonally_left_to_right", this.settings.getWinCombinations().stream().filter(c -> c.getName().equals("same_symbols_diagonally_left_to_right")).findFirst().orElseThrow().getName());
	}

}