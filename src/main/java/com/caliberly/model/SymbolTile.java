package com.caliberly.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.awt.geom.Point2D;
import java.util.*;

@Getter
@EqualsAndHashCode
public class SymbolTile {
	private Symbol symbol;
	private final Point2D.Float coordinates;
	private final Map<String, Integer> probabilities;
	private static final Random random = new Random();

	public SymbolTile(Point2D.Float coordinates, List<Symbol> availableSymbols, Map<String, Integer> probabilities) {
		this.coordinates = coordinates;
		this.probabilities = probabilities;
		this.generateSymbol(availableSymbols, this.probabilities);
	}

	/**
	 * Probability magic:
	 * Excluded bonus symbols:
	 * Percentage A =(1/21)×100 = 4.76%
	 * Percentage B =(2/21)×100 = 9.52%
	 *
	 * @param symbols
	 */
	public void generateSymbol(List<Symbol> symbols, Map<String, Integer> localProbabilities) {
		int totalProbability = 0;
		for (int value : localProbabilities.values()) {
			totalProbability += value;
		}

		Map<String, Double> cumulativeProbabilities = new HashMap<>();
		double cumulativeSum = 0.0;

		if (totalProbability != 0) {
			for (Map.Entry<String, Integer> entry : localProbabilities.entrySet()) {
				cumulativeSum += (double) entry.getValue() / totalProbability;
				cumulativeProbabilities.put(entry.getKey(), cumulativeSum);
			}
		}

		String selectedSymbol = selectSymbolBasedOnProbability(cumulativeProbabilities);

		this.symbol = symbols.stream().filter(s -> s.getName().equals(selectedSymbol)).findFirst().orElseThrow();
	}

	public static String selectSymbolBasedOnProbability(Map<String, Double> cumulativeProbabilities) {
		double randomValue = random.nextDouble();

		for (Map.Entry<String, Double> entry : cumulativeProbabilities.entrySet()) {
			if (randomValue <= entry.getValue()) {
				return entry.getKey();
			}
		}
		return null;
	}
}
