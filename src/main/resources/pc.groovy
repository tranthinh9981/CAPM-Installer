import capm.installer.TOOL.Utilities;
import groovy.transform.InheritConstructors
import capm.installer.Installer
import capm.installer.INTERFACE.Monitoring
import capm.installer.SHARE.SharedResources
import capm.installer.MODEL.ShellSSH
import Shell

@InheritConstructors
class pc extends Shell{
	//your code below!
	String binPath = SharedResources.getResource('*.bin path');
	String extractedPath = SharedResources.getResource('extracted path');
	String rootUser = SharedResources.getResource('root username');
	String daIP = SharedResources.getResource('data aggregator ip');
	String mysqlPwd = SharedResources.getResource('mysql pwd');
	
	def String install() {
		init();
		if(!pc_installation())
			return response;
		return "";
	}
	def String uninstall() {
		init();
		if(!pc_uninstallation())
			return response;
		return "";
	}
	
	def boolean pc_installation(){
		String script = "chmod a+x $binPath\n"+
				"rm -rf $extractedPath\n"+
				"$binPath\n";

		TreeMap<String, String> configCommand = new TreeMap<String, String>();
		configCommand.put('Choose Locale','1');
		configCommand.put("prompt and make changes",'');
		configCommand.put("PRESS <ENTER> TO CONTINUE","");
		configCommand.put("DO YOU ACCEPT THE TERMS OF THIS LICENSE AGREEMENT?","y");
		configCommand.put("ENTER THE NUMBER OF THE DESIRED CHOICE, OR PRESS <ENTER> TO ACCEPT THE","1");
		configCommand.put("ENTER AN ABSOLUTE PATH", extractedPath);
		configCommand.put("INSTALL FOLDER IS","y");
		configCommand.put("requires a database password to continue",mysqlPwd);
		configCommand.put("Confirm the database password",mysqlPwd);
		configCommand.put("Specify the location where the installer should create the MySQL","")
		configCommand.put('Console Service',"\r");
		configCommand.put('Set the maximum memory','\r');
		configCommand.put('Device Manager Service',"\n");
		configCommand.put('Event Manager Service',"\n");
		configCommand.put('MySql Data Directory',"\n");
		configCommand.put('MySql Temp Directory',"\n");
		configCommand.put('Disk Space Information','');
		configCommand.put('Installation Complete','\necho $?');
		
		return excuteScript(script, configCommand);
	}
	def boolean pc_uninstallation() {
		String script = "$extractedPath/PerformanceCenter/uninstall/Uninstall_PerformanceCenter\n";

		TreeMap<String, String> configCommand = new TreeMap<String, String>();
		configCommand.put('This will remove features installed by InstallAnywhere','');
		configCommand.put('Some items could not be removed', 'echo $?');
		configCommand.put('Uninstall Complete', 'echo $?');
		configCommand.put('could not complete due to an error', 'echo $?');
		
		return excuteScript(script, configCommand);
	}
}