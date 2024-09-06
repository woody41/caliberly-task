package com.caliberly.model;

import com.caliberly.enums.SymbolType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Symbol {
	@Setter
	protected String name;
	@Setter
	protected SymbolType symbolType;
	protected double reward_multiplier = 1;
	protected double extra = 0;


	@Override
	public int hashCode() {
		return Objects.hash(name, symbolType, reward_multiplier, extra);
	}
	/**
	 * Only combination of name and symbol type is unique
	 *
	 * */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if(!Objects.equals(this.name, ((Symbol) o).name)) return false;
		return Objects.equals(this.symbolType, ((Symbol) o).symbolType);
	}
}
