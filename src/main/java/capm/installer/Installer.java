package capm.installer;

import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.groovy.control.CompilationFailedException;

import capm.installer.INTERFACE.Monitoring;
import capm.installer.MODEL.ShellCommandException;
import capm.installer.MODEL.ShellSSH;
import capm.installer.TOOL.Utilities;

public class Installer{

	protected Monitoring monitor = new ConsoleMonitor();
	protected ShellSSH shell;

	public ShellSSH getShell() {
		return shell;
	}

	public void setShell(ShellSSH shell) {
		this.shell = shell;
	}

	public Monitoring getMonitor() {
		return monitor;
	}

	public void setMonitor(Monitoring monitor) {
		this.monitor = monitor;
	}

	public Installer(ShellSSH shell) {
		this.shell = shell;
	}
	
	public void run(String function, String groovyfile) throws CompilationFailedException, URISyntaxException, IOException, ShellCommandException {
		try {
			Utilities.excuteGroovyFromResources(function, groovyfile);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
