package capm.installer.TOOL;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.codehaus.groovy.control.CompilationFailedException;
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
		classLoader.clearCache();
		File f = new File("src/main/resources/"+fileName);
		Class<?> groovy = classLoader.parseClass(f);
		GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
        String result = (String) groovyObj.invokeMethod(funcName, null);
		if(!result.isEmpty()) throw new ShellCommandException(result);
	}
	
	public static void executeFile(String fileName) throws IOException {
		Desktop.getDesktop().open(new File("src/main/resources/"+fileName));
	}
}
