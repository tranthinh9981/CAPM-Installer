import capm.installer.TOOL.Utilities;
import groovy.transform.InheritConstructors
import capm.installer.Installer
import capm.installer.INTERFACE.Monitoring
import capm.installer.SHARE.SharedResources
import capm.installer.MODEL.ShellSSH
import Shell

@InheritConstructors
class Groovy extends Shell{
	
	//your code below!
	String binPath = SharedResources.getResource('*.bin path');
	String extractedPath = SharedResources.getResource('extracted path');
	String dbUser = SharedResources.getResource('database user');
	String dbPwd = SharedResources.getResource('database pwd');
	String adminUser = SharedResources.getResource('database admin user');
	String adminPwd = SharedResources.getResource('database admin pwd');
	String dbName = SharedResources.getResource('database name');
	String rootUser = SharedResources.getResource('root username');
	String verticaIP = SharedResources.getResource('vertica ip');
	
	def String install() {
		init();
		if(!da_installation())
			return response;
		return "";
	}
	def String uninstall() {
		init();
		if(!da_uninstallation())
			return response;
		return "";
	}
	def boolean da_installation(){
		String script = "chmod a+x $binPath\n"+
				"rm -rf $extractedPath\n"+
				"$binPath\n";

		TreeMap<String, String> configCommand = new TreeMap<String, String>();
		configCommand.put('Choose Locale','1');
		configCommand.put("by typing 'quit'",'');
		configCommand.put("PRESS <ENTER> TO CONTINUE","");
		configCommand.put("DO YOU ACCEPT THE TERMS OF THIS LICENSE AGREEMENT?","y");
		configCommand.put("ENTER THE NUMBER OF THE DESIRED CHOICE, OR PRESS <ENTER> TO ACCEPT THE","");
		configCommand.put("ENTER AN ABSOLUTE PATH", extractedPath);
		configCommand.put("INSTALL FOLDER IS","y");
		configCommand.put('Enter the maximum memory that the Data Aggregator process can allocate',"");
		configCommand.put('Enter the maximum memory that the ActiveMQ process can allocate',"");
		configCommand.put('Provide the name for the database accessed by Data Aggregator',verticaIP);
		configCommand.put('Enter port of the data repository server',"");
		configCommand.put('Enter the name for the database accessed by Data Aggregator',dbName);
		configCommand.put('Choose "Yes" to drop the existing schema','');
		configCommand.put('and this username/password combination will be added', dbUser);
		configCommand.put('Data Repository User Password', dbPwd);
		configCommand.put('Data repository admin username is the database', adminUser);
		configCommand.put('Data repository admin user password', adminPwd);
		configCommand.put('Failed to pass the pre-requisite checking for the Vertica database',"1");
		configCommand.put('DEFAULT: 1','\necho $?');
		configCommand.put('The HTTP port number','');
		configCommand.put('The SSH port number','');
		configCommand.put('Disk Space Information','');
		configCommand.put('Installation Complete','\necho $?');

		return excuteScript(script, configCommand);
	}
	def boolean da_uninstallation() {
		String script = "$extractedPath/Uninstall/Uninstall\n";

		TreeMap<String, String> configCommand = new TreeMap<String, String>();
		configCommand.put('This will remove features installed by InstallAnywhere','');
		configCommand.put('Some items could not be removed', 'echo $?');
		configCommand.put('Uninstall Complete', 'echo $?');
		configCommand.put('could not complete due to an error', 'echo $?');
		
		return excuteScript(script, configCommand);
	}
}