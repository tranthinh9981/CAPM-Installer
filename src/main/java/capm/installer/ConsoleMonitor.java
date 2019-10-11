package capm.installer;

import capm.installer.INTERFACE.Monitoring;

public class ConsoleMonitor implements Monitoring {

	public void append(String str) {
		System.out.print(str);
	}

	public void println(String line) {
		System.out.println(line);
	}

	public void error(String message) {
		System.err.println("Error: " + message);
	}

}
