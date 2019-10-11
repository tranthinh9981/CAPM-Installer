package capm.installer.TOOL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.codehaus.groovy.control.CompilationFailedException;

import capm.installer.CAPMInstallerGUI;
import capm.installer.MODEL.ShellCommandException;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

public class Utilities {
	private static GroovyClassLoader classLoader = new GroovyClassLoader();
	public static String readFileResources(String fileName) {
		File file = new File("src/main/resources/"+fileName);
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void excuteGroovyFromResources(String funcName, String fileName) throws IOException, ShellCommandException, InstantiationException, IllegalAccessException, CompilationFailedException, URISyntaxException{

		Class<?> groovy = classLoader.parseClass(new File("src/main/resources/"+fileName));
		GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
        String result = (String) groovyObj.invokeMethod(funcName, null);
		if(!result.isEmpty()) throw new ShellCommandException(result);
	}
}
