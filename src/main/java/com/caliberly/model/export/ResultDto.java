package com.caliberly.model.export;

import com.caliberly.combinations.WinningCombination;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
@Data
public class ResultDto {
	private List<List<String>> matrix;
	private double reward;
	private List<String> appliedBonusSymbol;
	private Map<String, List<String>> appliedWinningCombinations = new HashMap<>();

	public void setAppliedWinningCombinations(List<WinningCombination> winningCombinations) {
		for (WinningCombination winningCombination : winningCombinations) {
			if (this.appliedWinningCombinations.containsKey(winningCombination.getSymbol().getName())) {
				List<String> oldValues = new LinkedList<>(this.appliedWinningCombinations.get(winningCombination.getSymbol().getName())); //prevent of immutable list
				oldValues.add(winningCombination.getName());
				this.appliedWinningCombinations.replace(winningCombination.getSymbol().getName(), oldValues);
			} else {
				this.appliedWinningCombinations.put(winningCombination.getSymbol().getName(), List.of(winningCombination.getName()));
			}
		}
	}
}
