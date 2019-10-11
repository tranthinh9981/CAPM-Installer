package capm.installer;

import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.groovy.control.CompilationFailedException;

import capm.installer.INTERFACE.Monitoring;
import capm.installer.MODEL.ShellCommandException;
import capm.installer.MODEL.ShellSSH;
import capm.installer.TOOL.Utilities;

public class Installer{
	private String groovyFileName = "dr.groovy";
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

	public Installer(ShellSSH shell, String groovyFile) {
		this.shell = shell;
		this.groovyFileName = groovyFile;
	}

	public void install() throws CompilationFailedException, URISyntaxException, IOException, ShellCommandException {
		try {
			Utilities.excuteGroovyFromResources("install", groovyFileName);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void uninstall() throws IOException, ShellCommandException, CompilationFailedException, URISyntaxException {
		try {
			Utilities.excuteGroovyFromResources("uninstall", groovyFileName);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
