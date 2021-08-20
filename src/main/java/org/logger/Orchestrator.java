package org.logger;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Orchestrator {
	private static Logger logger;

	private static final String LOGGING_ROOT_DIR="src/loggerdir";
	private static final String LOGGING="LOGGING";

	public Logger getLogger(Map<String, String> params) {
		String loggingRootDir = params.get(LOGGING);
		logger = LoggingUtil.getLogger(Level.INFO, 1, "componentname", OperationType.UPDATE, loggingRootDir);
		return logger;

	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put(LOGGING, LOGGING_ROOT_DIR);
		Orchestrator orchaestrator = new Orchestrator();
		orchaestrator.getLogger(map);
		logger.info("-------------------Logging started----------------");
		Employee employee = new Employee(1, "joe biden");
		logger.info(employee.getId()+":"+employee.getName());

		logger.info("----------logger ends---------------");

	}

	static class Employee {
		int id;
		String name;

		public Employee(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}
}
