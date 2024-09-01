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

	public WinningCombination(String name, int count, CombinationWhen when, String group, List<List<String>> coveredAreas, Symbol symbol) {
		super(name, count, when, group, coveredAreas);
		this.symbol = symbol;
	}

	public <T extends GeneralWinCombination> WinningCombination(T winCombination, Symbol symbol) {
		super(winCombination.getName(), winCombination.getCount(), winCombination.getWhen(), winCombination.getGroup(), winCombination.getCovered_areas());
		this.symbol = symbol;
	}
}
