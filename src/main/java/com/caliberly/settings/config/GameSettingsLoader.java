package com.caliberly.settings.config;

import com.caliberly.Symbol;
import com.caliberly.WinCombination;
import com.caliberly.enums.SymbolType;
import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
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
			//combination.setSymbolType(SymbolType.fromString(symbolDetails.getAsJsonPrimitive("type").getAsString()));
			winCombination.add(combination);
		}

		return winCombination;
	}
}
