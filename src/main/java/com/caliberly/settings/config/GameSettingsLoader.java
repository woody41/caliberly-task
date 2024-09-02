package com.caliberly.settings.config;

import com.caliberly.model.Symbol;
import com.caliberly.combinations.WinCombination;
import com.caliberly.enums.CombinationWhen;
import com.caliberly.enums.SymbolType;
import com.google.gson.*;

import java.awt.geom.Point2D;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Game setting loader for JSON file, this class could have an interface for other setting formats.
 */
public class GameSettingsLoader {

	private JsonObject allSettings;

	//Constructor for loading settings directly from a file
	public GameSettingsLoader(String filePath) {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(filePath)) {

			this.allSettings = gson.fromJson(reader, JsonObject.class);

		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
			// Handle the exception properly
		}

	}

	public int getRows() {
		return this.allSettings.getAsJsonPrimitive("rows").getAsInt();
	}

	public int getColumns() {
		return this.allSettings.getAsJsonPrimitive("columns").getAsInt();
	}

	public List<Symbol> getSymbols() {
		Gson gson = new Gson();
		List<Symbol> symbols = new LinkedList<>();

		JsonObject symbolsJsonObject = this.allSettings.getAsJsonObject("symbols");

		for (Map.Entry<String, JsonElement> entry : symbolsJsonObject.entrySet()) {
			String name = entry.getKey();
			JsonObject symbolDetails = entry.getValue().getAsJsonObject();

			Symbol symbol = gson.fromJson(symbolDetails, Symbol.class);
			symbol.setName(name);
			symbol.setSymbolType(SymbolType.fromString(symbolDetails.getAsJsonPrimitive("type").getAsString()));
			symbols.add(symbol);
		}

		return symbols;
	}

	public List<WinCombination> getWinCombinations() {
		Gson gson = new Gson();
		List<WinCombination> winCombination = new LinkedList<>();

		JsonObject winCOmbinationsJsonObject = this.allSettings.getAsJsonObject("win_combinations");

		for (Map.Entry<String, JsonElement> entry : winCOmbinationsJsonObject.entrySet()) {
			String name = entry.getKey();
			JsonObject symbolDetails = entry.getValue().getAsJsonObject();

			WinCombination combination = gson.fromJson(symbolDetails, WinCombination.class);
			combination.setName(name);
			combination.setWhen(CombinationWhen.fromString(symbolDetails.getAsJsonPrimitive("when").getAsString()));
			winCombination.add(combination);
		}

		return winCombination;
	}

	public Map<Point2D.Float, Map<String, Integer>> getCoordsBasedProbabilities() {
		Map<Point2D.Float, Map<String, Integer>> standardSymbols = new HashMap<>();

		JsonObject probabilities = this.allSettings.getAsJsonObject("probabilities");

		for (Map.Entry<String, JsonElement> entry : probabilities.entrySet()) {
			if (entry.getKey().equals("standard_symbols")) {
				//loop standard_symbols array
				for (JsonElement standardSymbolElement : entry.getValue().getAsJsonArray()) {

					Point2D.Float coords = new Point2D.Float();
					Map<String, Integer> probs = new HashMap<>();

					for (Map.Entry<String, JsonElement> standardSymbolEntry : standardSymbolElement.getAsJsonObject().entrySet()) {
						if (standardSymbolEntry.getKey().equals("row")) {
							coords.x = standardSymbolEntry.getValue().getAsFloat();
						}
						if (standardSymbolEntry.getKey().equals("column")) {
							coords.y = standardSymbolEntry.getValue().getAsFloat();
						}
						if (standardSymbolEntry.getKey().equals("symbols")) {
							for (Map.Entry<String, JsonElement> probValue : standardSymbolEntry.getValue().getAsJsonObject().entrySet()) {
								probs.put(probValue.getKey(), probValue.getValue().getAsInt());
							}
						}
					}
					standardSymbols.put(coords, probs);
				}
			}
		}
		return standardSymbols;
	}

	public Map<String, Integer> getSymbolBasedProbabilities() {
		JsonObject probabilities = this.allSettings.getAsJsonObject("probabilities");
		Map<String, Integer> probs = new HashMap<>();

		for (Map.Entry<String, JsonElement> entry : probabilities.entrySet()) {
			if (entry.getKey().equals("bonus_symbols")) {
				//loop standard_symbols array


				for (Map.Entry<String, JsonElement> standardSymbolEntry : entry.getValue().getAsJsonObject().entrySet()) {
					if (standardSymbolEntry.getKey().equals("symbols")) {
						for (Map.Entry<String, JsonElement> probValue : standardSymbolEntry.getValue().getAsJsonObject().entrySet()) {
							probs.put(probValue.getKey(), probValue.getValue().getAsInt());
						}
					}
				}
			}
		}
		return probs;
	}
}
