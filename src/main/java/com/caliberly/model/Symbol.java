package com.caliberly.model;

import com.caliberly.enums.SymbolType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Symbol {
	@Setter
	protected String name;
	@Setter
	protected SymbolType symbolType;
	protected double reward_multiplier = 1;
	protected double extra = 0;
}
