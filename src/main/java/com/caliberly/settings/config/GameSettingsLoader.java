package com.caliberly.settings.config;

import com.caliberly.combinations.WinCombination;
import com.caliberly.enums.CombinationWhen;
import com.caliberly.enums.SymbolType;
import com.caliberly.model.Symbol;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.awt.geom.Point2D;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Interface for settings loader - in case we want to load settings from different location
 */
public interface GameSettingsLoader {

	public int getRows();

	public int getColumns();

	public List<Symbol> getSymbols();

	public List<WinCombination> getWinCombinations();

	public Map<Point2D.Float, Map<String, Integer>> getCoordsBasedProbabilities();

	public Map<String, Integer> getSymbolBasedProbabilities();
}
