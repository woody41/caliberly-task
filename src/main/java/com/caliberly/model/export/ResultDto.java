package com.caliberly.model.export;

import com.caliberly.combinations.WinningCombination;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
@Data
public class ResultDto {
	private List<List<String>> matrix;
	private int reward;
	@SerializedName("applied_bonus_symbol")
	private String appliedBonusSymbol;
	@SerializedName("applied_winning_combinations")
	private Map<String, List<String>> appliedWinningCombinations;

	public void setAppliedWinningCombinations(List<WinningCombination> winningCombinations) {
		for (WinningCombination winningCombination : winningCombinations) {
			if (this.appliedWinningCombinations != null && this.appliedWinningCombinations.containsKey(winningCombination.getSymbol().getName())) {
				List<String> oldValues = new LinkedList<>(this.appliedWinningCombinations.get(winningCombination.getSymbol().getName())); //prevent of immutable list
				oldValues.add(winningCombination.getName());
				this.appliedWinningCombinations.replace(winningCombination.getSymbol().getName(), oldValues);
			} else if(this.appliedWinningCombinations != null) {
				this.appliedWinningCombinations.put(winningCombination.getSymbol().getName(), List.of(winningCombination.getName()));
			} else {
				this.appliedWinningCombinations = new HashMap<>();
				this.appliedWinningCombinations.put(winningCombination.getSymbol().getName(), List.of(winningCombination.getName()));
			}
		}
	}
}
