import capm.installer.TOOL.Utilities;
import groovy.transform.InheritConstructors
import capm.installer.Installer
import capm.installer.INTERFACE.Monitoring
import capm.installer.SHARE.SharedResources
import capm.installer.MODEL.ShellSSH

@InheritConstructors
class Shell {
	PrintStream sender;
	BufferedReader receiver;
	Monitoring monitor;
	int currentIndex = 1;
	String response;
	def void init() {
		ShellSSH shell = ((Installer)SharedResources.getResource("installer")).getShell();
		sender = shell.getSender();
		receiver = shell.getReceiver();
		monitor = ((Installer)SharedResources.getResource("installer")).getMonitor();
		switch(SharedResources.getStep()) {
			case SharedResources.Step.NEW:
				SharedResources.resetScriptCursor();
				break;
			case SharedResources.Step.RETRY:
			//nothing to do
				break;
			case SharedResources.Step.IGNORE:
				SharedResources.setScriptCursor(SharedResources.getScriptCursor()+1);
				break;
		}
	}
	def int excute(String command, TreeMap<String, String> configCommand) {
		if(currentIndex < SharedResources.getScriptCursor()) {
			currentIndex++;
			return -1;
		}
		sender.print(command+"\n");
		sender.print('echo $?\n');
		sender.flush();
		int exitCode;
		boolean isLooping = true;
		while ((response = receiver.readLine()) != null && isLooping) {
			System.out.println(response);
			monitor.println(response);
			if(configCommand != null) {
				for(Map.Entry<String, String> entry : configCommand.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if(response.indexOf(key)> -1) {
						sender.print(value+"\r");
						sender.flush();
						break;
					}
				}
			}
			exitCode = getExitCode(response);
			if(exitCode == 0) {
				currentIndex++;
			}
			if (exitCode > -1) {
				monitor.println(String.valueOf(exitCode));
				SharedResources.setScriptCursor(currentIndex);
				return exitCode;
			}
		}
	}
	def int getExitCode(String response) {
		if (response.contains('echo $?')) {
			try {
				return Integer.parseInt(receiver.readLine());
			}catch(NumberFormatException e) {
				return -1;
			}
		}
		return -1;
	}
}