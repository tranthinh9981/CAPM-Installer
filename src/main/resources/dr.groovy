import capm.installer.TOOL.Utilities;
import groovy.transform.InheritConstructors
import capm.installer.INTERFACE.Monitoring
import capm.installer.SHARE.SharedResources
import capm.installer.MODEL.ShellSSH
import capm.installer.Installer
import Shell

@InheritConstructors
class Groovy extends Shell{
	//your code below!
	String binPath = SharedResources.getResource('*.bin path');
	String extractedPath = SharedResources.getResource('extracted path');
	String adminUser = SharedResources.getResource('database admin user');
	String adminPwd = SharedResources.getResource('database admin pwd');
	String dbName = SharedResources.getResource('database name');

	def String install() {
		init();

		if(!vertica_environment_initiation())
			return response;
		if(!vertica_bin_extraction())
			return response;
		if(!vertica_installation())
			return response;
		if(!vertica_database_creation())
			return response;
		return "";
	}
	def String uninstall() {
		init();
		if(!vertica_uninstallation())
			return response;
		return "";
	}
	def boolean vertica_environment_initiation(){
		String script =  Utilities.readFileResources("vertica_evm_script");
		return excuteScript(script, null);
	}
	def boolean vertica_bin_extraction(){
		String script = "chmod a+x $binPath\n"+
				"rm -rf $extractedPath\n"+
				"$binPath";
		TreeMap<String, String> configCommand = new TreeMap<String, String>();

		configCommand.put('Choose Locale','1');
		configCommand.put("by typing 'quit'",'');
		configCommand.put("PRESS <ENTER> TO CONTINUE","");
		configCommand.put('DO YOU ACCEPT THE TERMS OF THIS LICENSE AGREEMENT?','y');
		configCommand.put("ENTER AN ABSOLUTE PATH", extractedPath);
		configCommand.put("INSTALL FOLDER IS","y");
		configCommand.put("Installation Complete","");
		configCommand.put('PRESS <ENTER> TO EXIT THE INSTALLER', 'echo $?');

		return excuteScript(script, configCommand);
	}
	def boolean vertica_installation(){
		String script = "if (rpm -qa | grep vertica); then rpm -e \$(rpm -qa | grep vertica); fi\n"+
				"fileName=\$(ls $extractedPath/resources | grep RHEL6)\n"+
				"rpm -ivh $extractedPath/resources/\$fileName\n"+
				"/opt/vertica/sbin/install_vertica -s 127.0.0.1 -u $adminUser -g $adminUser -l /home/$adminUser -d /home/$adminUser -L $extractedPath/resources/vlicense.dat -r $extractedPath/resources/\$fileName --dba-user-password-disabled --failure-threshold FAIL\n" +
				'echo $?';

		return excuteScript(script, null);
	}
	def boolean vertica_database_creation() {
		String script = "systemctl stop vertica_agent\n"+
				"systemctl disable vertica_agent\n"+
				"sudo su - $adminUser\n"+
				"/opt/vertica/bin/admintools -t create_db -s 127.0.0.1 -d '$dbName' -p '$adminPwd' -P always --skip-fs-check\n";

		TreeMap<String, String> configCommand = new TreeMap<String, String>();
		configCommand.put('Vertica LP','ACCEPT');
		configCommand.put('EntIT Software LLC','ACCEPT');
		configCommand.put('created successfully','\necho $?');
		configCommand.put('already exists','\necho $?');

		return excuteScript(script, configCommand);
	}
	def boolean vertica_uninstallation() {
		String script = "sudo su - $adminUser\n"+
				"/opt/vertica/bin/admintools -t stop_db -d $dbName -p $adminPwd -F\n"+
				"/opt/vertica/bin/adminTools -t drop_db -d $dbName";

		TreeMap<String, String> configCommand = new TreeMap<String, String>();
		configCommand.put('after the extraction','');
		configCommand.put('Some items could not be removed', 'echo $?');
		configCommand.put('Uninstall Complete', 'echo $?');
		configCommand.put('could not complete due to an error', 'echo $?');
		configCommand.put('EntIT Software LLC','ACCEPT');

		return excuteScript(script, configCommand);
	}
}