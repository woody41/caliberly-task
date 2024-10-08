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
	private final Random rand = new Random();

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
				this.gameArea.put(coordinates, new SymbolTile(coordinates, this.settings.getSymbols(SymbolType.STANDARD), availableSymbols));
			}
		}
		//this.settings.getSymbolsProbabilities()
		this.addBonusSymbol();
	}

	private void addBonusSymbol() {
		int col = this.rand.nextInt(this.settings.getColumns());
		int row = this.rand.nextInt(this.settings.getRows());
		this.gameArea.get(new Point2D.Float(col, row)).generateSymbol(this.settings.getSymbols(SymbolType.BONUS), this.settings.getSymbolsProbabilities());
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
			String bonus = this.getAppliedBonus();
			if (bonus != null) {
				resultDto.setAppliedBonusSymbol(this.getAppliedBonus());
			}
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement jsonDto = gson.toJsonTree(resultDto);

		logger.info(jsonDto.toString()); //this is final requirement
	}

	private int getFinalBettingAmount(List<WinningCombination> winningCombinations, double bettingAmount) {

		if (!winningCombinations.isEmpty()) {
			//get final result for standard symbols
			bettingAmount = this.getSumFromWinningCombinations(winningCombinations, bettingAmount);

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

			logger.debug("Result: {}", (int) bettingAmount);
			return (int) bettingAmount;
		} else {
			logger.debug("Game lost");
			return 0;
		}
	}


	/**
	 * Sum of winning combination by Symbol.
	 * (SYMBOL_1 * WIN_COMBINATION_1_FOR_SYMBOL_1 * WIN_COMBINATION_2_FOR_SYMBOL_1) + (SYMBOL_2 * WIN_COMBINATION_1_FOR_SYMBOL_2)
	 *
	 * @param winningCombinations
	 * @param bettingAmount
	 * @return
	 */
	private double getSumFromWinningCombinations(List<WinningCombination> winningCombinations, double bettingAmount) {
		List<Symbol> winningSymbols = new LinkedList<>();
		double result = 0;
		for (WinningCombination winningCombination : winningCombinations) {
			if (!winningSymbols.contains(winningCombination.getSymbol())) {
				winningSymbols.add(winningCombination.getSymbol());
			}
		}
		for (Symbol symbol : winningSymbols) {
			double multiplier = 1;
			for (WinningCombination winningCombination : winningCombinations) {
				if (winningCombination.getSymbol().equals(symbol)) {
					if (winningCombination.getWhen().equals(CombinationWhen.SAME_SYMBOL)) {
						multiplier *= winningCombination.getSymbol().getReward_multiplier();
					}
					if (winningCombination.getReward_multiplier() != 0) {
						multiplier *= winningCombination.getReward_multiplier();
					}
				}
			}
			result += bettingAmount * multiplier;
		}
		return result;
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

	private String getAppliedBonus() {
		for (Map.Entry<Point2D.Float, SymbolTile> entry : this.gameArea.entrySet()) {
			if (entry.getValue().getSymbol().getSymbolType().equals(SymbolType.BONUS)) {
				return entry.getValue().getSymbol().getName();
			}
		}
		return null;
	}

	/**
	 * Not necessary, for future purpose
	 */
	private void gameEnd() {
		logger.info("Game over");
		System.exit(1);
	}
}
