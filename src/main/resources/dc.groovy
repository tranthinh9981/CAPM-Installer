import capm.installer.TOOL.Utilities;
import groovy.transform.InheritConstructors
import capm.installer.Installer
import capm.installer.INTERFACE.Monitoring
import capm.installer.SHARE.SharedResources
import capm.installer.MODEL.ShellSSH

@InheritConstructors
class Groovy extends Shell{
	
	//your code below!
	String binPath = SharedResources.getResource('*.bin path');
	String extractedPath = SharedResources.getResource('extracted path');
	String rootUser = SharedResources.getResource('root username');
	String daIP = SharedResources.getResource('data aggregator ip');
	def String install() {
		init();
		if(!dc_installation())
			return response;
		return "";
	}
	def String uninstall() {
		init();
		if(!dc_uninstallation())
			return response;
		return "";
	}
	
	def boolean dc_installation(){
		String script = "chmod a+x $binPath\n"+
				"rm -rf $extractedPath\n"+
				"$binPath\n";

		TreeMap<String, String> configCommand = new TreeMap<String, String>();
		configCommand.put('Choose Locale','1');
		configCommand.put("by typing 'quit'",'');
		configCommand.put("PRESS <ENTER> TO CONTINUE","");
		configCommand.put("DO YOU ACCEPT THE TERMS OF THIS LICENSE AGREEMENT?","y");
		configCommand.put("ENTER THE NUMBER OF THE DESIRED CHOICE, OR PRESS <ENTER> TO ACCEPT THE","1");
		configCommand.put("ENTER AN ABSOLUTE PATH", extractedPath);
		configCommand.put("INSTALL FOLDER IS","y");
		configCommand.put('the maximum memory that the DC',"");
		configCommand.put('Disk Space Information','');
		configCommand.put('Data Aggregator Host',daIP);
		configCommand.put('Installation Complete','\necho $?');

		return excuteScript(script, configCommand);
	}
	def boolean dc_uninstallation() {
		String script = "$extractedPath/Uninstall/Uninstall\n";

		TreeMap<String, String> configCommand = new TreeMap<String, String>();
		configCommand.put('This will remove features installed by InstallAnywhere','');
		configCommand.put('Some items could not be removed', 'echo $?');
		configCommand.put('Uninstall Complete', 'echo $?');
		configCommand.put('could not complete due to an error', 'echo $?');
		
		return excuteScript(script, configCommand);
	}
}