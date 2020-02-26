package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class OSUtil {

	public static String getScriptsDir() {
		PropertiesManager properties = new PropertiesManager();
		String scriptsDir = properties.getProperty("scripts_dir");
		properties.close();
		return scriptsDir;
	}
	
	public static void runScript(String[] command, String resultsFile) throws IOException, InterruptedException {
		PrintWriter writer = new PrintWriter(resultsFile, "UTF-8");
		
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();

		BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		StringBuilder builder = new StringBuilder();
		String line1 = null;
		while ((line1 = reader1.readLine()) != null) {
			builder.append(line1 + "\n");
		}

		String line2 = null;
		while ((line2 = reader2.readLine()) != null) {
			builder.append(line2 + "\n");
		}

		String result = builder.toString();

		writer.write(result);
		process.waitFor();
			
		writer.close();
	}

}
