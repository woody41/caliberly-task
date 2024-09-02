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

	public SymbolTile(Point2D.Float coordinates, List<Symbol> availableSymbols, Map<String, Integer> probabilities) {
		this.coordinates = coordinates;
		this.probabilities = probabilities;
		this.generateSymbol(availableSymbols);
	}

	private void generateSymbol(List<Symbol> symbols) {
		int totalProbability = 0;
		for (int value : this.probabilities.values()) {
			totalProbability += value;
		}

		Map<String, Double> cumulativeProbabilities = new HashMap<>();
		double cumulativeSum = 0.0;

		if (totalProbability != 0) {
			for (Map.Entry<String, Integer> entry : probabilities.entrySet()) {
				cumulativeSum += (double) entry.getValue() / totalProbability;
				cumulativeProbabilities.put(entry.getKey(), cumulativeSum);
			}
		}

		String selectedSymbol = selectSymbolBasedOnProbability(cumulativeProbabilities);

		this.symbol = symbols.stream().filter(s -> s.getName().equals(selectedSymbol)).findFirst().orElseThrow();
	}

	public static String selectSymbolBasedOnProbability(Map<String, Double> cumulativeProbabilities) {
		Random random = new Random();
		double randomValue = random.nextDouble();

		for (Map.Entry<String, Double> entry : cumulativeProbabilities.entrySet()) {
			if (randomValue <= entry.getValue()) {
				return entry.getKey();
			}
		}
		return null;
	}
}
