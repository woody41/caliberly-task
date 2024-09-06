package com.caliberly.combinations;

import com.caliberly.enums.CombinationWhen;
import com.caliberly.enums.SymbolType;
import com.caliberly.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class WinningCombination extends WinCombination {

	private final Symbol symbol;

	public WinningCombination(String name, int count, double reward_multiplier, CombinationWhen when, String group, List<List<String>> coveredAreas, Symbol symbol) {
		super(name, count, reward_multiplier, when, group, coveredAreas);
		this.symbol = symbol;
	}

	public <T extends GeneralWinCombination> WinningCombination(T winCombination, Symbol symbol) {
		super(winCombination.getName(), winCombination.getCount(), winCombination.getReward_multiplier(), winCombination.getWhen(), winCombination.getGroup(), winCombination.getCovered_areas());
		this.symbol = symbol;
	}

	@Override
	public int hashCode() {
		return symbol.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		return this.symbol.equals(((WinningCombination) o).symbol);
	}
}
