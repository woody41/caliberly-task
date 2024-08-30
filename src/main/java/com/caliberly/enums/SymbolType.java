package com.caliberly.enums;

import lombok.Getter;

@Getter
public enum SymbolType {
	STANDARD("standard"),
	BONUS("bonus");

	private final String type;

	SymbolType(String type) {
		this.type = type;
	}

	public static SymbolType fromString(String type) {
		if (type == null) {
			return null;
		}
		for (SymbolType symbolType : SymbolType.values()) {
			if (symbolType.getType().equalsIgnoreCase(type)) {
				return symbolType;
			}
		}
		throw new IllegalArgumentException("Unknown symbol type: " + type);
	}
}
