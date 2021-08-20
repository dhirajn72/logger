package org.logger;

public enum OperationType {
	DELETE, UPDATE, INSERT;

	public static OperationType getOperationType(String operation) {

		if (operation.equalsIgnoreCase("update")) {
			return UPDATE;
		} else if (operation.equalsIgnoreCase("delete")) {
			return DELETE;
		} else if (operation.equalsIgnoreCase("insert")) {
			return INSERT;
		} else
			throw new IllegalArgumentException("Supported operation types are [insert|delete|update].");

	}
}
