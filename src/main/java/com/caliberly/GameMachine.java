package com.caliberly;

import com.caliberly.combinations.WinCombination;
import com.caliberly.combinations.WinningCombination;
import com.caliberly.enums.CombinationWhen;
import com.caliberly.enums.SymbolType;
import com.caliberly.model.export.ResultDto;
import com.caliberly.model.Symbol;
import com.caliberly.model.SymbolTile;
import com.caliberly.settings.config.GameSettings;
import com.caliberly.utils.CombinationChecker;
import com.caliberly.utils.GameAreaPrinter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.Point2D;
import java.util.*;

public class GameMachine {
	protected static final Logger logger = LogManager.getLogger(GameMachine.class);

	private Map<Point2D.Float, SymbolTile> gameArea = new HashMap<>();
	private final GameSettings settings;

	public GameMachine(GameSettings gameSettings, int amount) {
		logger.debug("Game machine dispatched");
		this.settings = gameSettings;
		//this line could be in a loop to keep playing
		this.startGame(amount);

		this.gameEnd();
	}

	public void startGame(int amount) {
		this.generateArea();
		GameAreaPrinter.print(this.gameArea); // visualization of game area
		List<WinningCombination> winningCombinations = this.checkResults();
		this.exportResults(winningCombinations, amount);
	}

	/**
	 * Simple game area generation based on settings
	 */
	private void generateArea() {
		for (int row = 0; row < this.settings.getRows(); row++) { //loop for generating rows
			for (int column = 0; column < this.settings.getColumns(); column++) { //loop for generating columns
				Point2D.Float coordinates = new Point2D.Float();
				coordinates.setLocation(row, column);
				Map<String, Integer> availableSymbols = new HashMap<>(this.settings.getPointSymbolsProbabilities().get(new Point2D.Float(row, column)));
				availableSymbols.putAll(this.settings.getSymbolsProbabilities());
				this.gameArea.put(coordinates, new SymbolTile(coordinates, this.settings.getSymbols(), availableSymbols));
			}
		}
	}

	private List<WinningCombination> checkResults() {
		List<WinningCombination> winningCombinations = new LinkedList<>();
		for (WinCombination winCombination : this.settings.getWinCombinations()) {
			if (winCombination.getWhen().equals(CombinationWhen.SAME_SYMBOL)) {
				//check for same symbol
				for (Symbol symbol : settings.getSymbols()) {
					if (symbol.getSymbolType().equals(SymbolType.STANDARD)) {
						WinningCombination combination = CombinationChecker.getSameSymbolResult(winCombination, gameArea, symbol);
						if (combination != null) {
							winningCombinations.add(combination);
						}
					}
				}
			}
			if (winCombination.getWhen().equals(CombinationWhen.LINEAR_SYMBOL)) {
				winningCombinations.addAll(CombinationChecker.getLinearSymbolResult(winCombination, gameArea));
			}
		}
		this.showResults(winningCombinations);
		return winningCombinations;
	}

	private void showResults(List<WinningCombination> winningCombinations) {
		if (winningCombinations.isEmpty()) {
			logger.debug("No winning combinations");
		}
		for (WinningCombination winningCombination : winningCombinations) {
			logger.debug("{} is winning combinations. Winning symbol: {}", winningCombination.getName(), winningCombination.getSymbol().getName());
		}
	}

	private void exportResults(List<WinningCombination> winningCombinations, double bettingAmount) {


		ResultDto resultDto = new ResultDto();
		resultDto.setReward(this.getFinalBettingAmount(winningCombinations, bettingAmount)); //setting reward
		resultDto.setMatrix(this.getGameAreaMatrix());
		if (!winningCombinations.isEmpty()) {
			resultDto.setAppliedWinningCombinations(winningCombinations);
			resultDto.setAppliedBonusSymbol(this.getAppliedBonus());
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement jsonDto = gson.toJsonTree(resultDto);

		logger.info(jsonDto.toString()); //this is final requirement
	}

	private double getFinalBettingAmount(List<WinningCombination> winningCombinations, double bettingAmount) {

		if (!winningCombinations.isEmpty()) {
			//Check for bonus which adds points
			for (Map.Entry<Point2D.Float, SymbolTile> symbolTile : this.gameArea.entrySet()) {
				if (symbolTile.getValue().getSymbol().getSymbolType().equals(SymbolType.BONUS))
					bettingAmount += symbolTile.getValue().getSymbol().getExtra();
			}
			//Check for bonus which adds points
			for (Map.Entry<Point2D.Float, SymbolTile> symbolTile : this.gameArea.entrySet()) {
				if (symbolTile.getValue().getSymbol().getSymbolType().equals(SymbolType.BONUS))
					if (symbolTile.getValue().getSymbol().getReward_multiplier() != 0) {
						bettingAmount *= symbolTile.getValue().getSymbol().getReward_multiplier();
					}
			}
			//Check for winning combination which multiply points
			for (WinningCombination winningCombination : winningCombinations) {
				if (winningCombination.getWhen().equals(CombinationWhen.SAME_SYMBOL)) {
					bettingAmount *= winningCombination.getSymbol().getReward_multiplier();
				}
				if (winningCombination.getReward_multiplier() != 0) {
					bettingAmount *= winningCombination.getReward_multiplier();
				}
			}
			logger.debug("Result: {}", bettingAmount);
			return bettingAmount;
		} else {
			logger.debug("Game lost");
			return 0;
		}
	}

	private List<List<String>> getGameAreaMatrix() {
		List<List<String>> gameMap = new LinkedList<>();
		for (int row = 0; row < this.settings.getRows(); row++) {
			List<String> rowArray = new LinkedList<>();
			for (int col = 0; col < this.settings.getColumns(); col++) {
				rowArray.add(this.gameArea.get(new Point2D.Float(row, col)).getSymbol().getName());
			}
			gameMap.add(rowArray);
		}
		return gameMap;
	}

	private List<String> getAppliedBonus() {
		List<String> results = new LinkedList<>();
		for (Map.Entry<Point2D.Float, SymbolTile> entry : this.gameArea.entrySet()) {
			if (entry.getValue().getSymbol().getSymbolType().equals(SymbolType.BONUS)) {
				results.add(entry.getValue().getSymbol().getName());
			}
		}
		return results;
	}

	/**
	 * Not necessary, for future purpose
	 */
	private void gameEnd() {
		logger.info("Game over");
		System.exit(1);
	}
}
