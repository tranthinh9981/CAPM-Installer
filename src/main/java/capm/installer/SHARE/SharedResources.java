package capm.installer.SHARE;
import java.util.HashMap;
public class SharedResources{
	private static HashMap<String, Object> context = new HashMap<String, Object>();
	private static int scriptCursor = 1;
	public enum Step {
	    NEW,
	    RETRY,
	    IGNORE
	  }
	private static Step step = Step.NEW;
	
	public static void putResource(String key, Object value) {
		context.put(key, value);
	}
	public static Object getResource(String key) {
		return context.get(key);
	}
	public static int getScriptCursor() {
		return scriptCursor;
	}
	public static void setScriptCursor(int i) {
		scriptCursor = i;
	}
	public static void resetScriptCursor() {
		SharedResources.setScriptCursor(1);
	}
	public static Step getStep() {
		return step;
	}
	public static void setStep(Step step) {
		SharedResources.step = step;
	}
}
