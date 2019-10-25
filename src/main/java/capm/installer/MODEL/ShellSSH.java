package capm.installer.MODEL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class ShellSSH{
	private String host = null;
	private String username = null;
	private String password = null;
	protected BufferedReader receiver;
	protected PrintStream sender;
	private Session session;
	public Channel channel;
	
	
	public ShellSSH(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}
	
	public boolean connect(int timeout) throws JSchException, IOException {
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		JSch jsch = new JSch();
		session = jsch.getSession(username, host, 22);
		session.setPassword(password);
		session.setConfig(config);
		session.connect();
		channel = session.openChannel("shell");
		((ChannelShell) channel).setEnv("LANG", "en_US.UTF-8");
		((ChannelShell) channel).setPty(true);
		((ChannelShell) channel).setPtyType("dumb");
		
		receiver = new BufferedReader(new InputStreamReader(channel.getInputStream()));
		sender = new PrintStream(channel.getOutputStream());
		channel.connect(timeout);
		return channel.isConnected();
	}
	
	public BufferedReader getReceiver() {
		return receiver;
	}
	public PrintStream getSender() {
		return sender;
	}
	
	public void close() throws IOException {
		receiver.close();
		sender.close();
		channel.disconnect();
		session.disconnect();
	}
	
}
