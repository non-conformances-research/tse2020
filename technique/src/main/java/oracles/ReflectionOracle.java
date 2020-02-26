package oracles;

import util.PropertiesManager;
import util.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionOracle {

	private static String basePath;
	static {
		basePath = new PropertiesManager().getProperty("results_path");
	}

	public static void compareResultsInDifferentJVM(String jvm1, String jvm2) {
		String path1 = basePath + jvm1 + ".txt";
		String path2 = basePath + jvm2 + ".txt";
		File file1 = new File(path1);
		File file2 = new File(path2);
		System.out.println("Comparing " + jvm1 + " results to " + jvm2 + " results...");
		compareResults(file1, file2, jvm1, jvm2);
	}

	private static void compareResults(File file1, File file2, String os1Name, String os2Name) {
		try (BufferedReader br = new BufferedReader(new FileReader(file1)); BufferedReader br2 = new BufferedReader(new FileReader(file2))) {
			String file1Line, file2Line;
			List<String> file1Lines = new ArrayList<>();
			List<String> file2Lines = new ArrayList<>();
			List<String> file2Keys = new ArrayList<>();
			while ((file1Line = br.readLine()) != null && (file2Line = br2.readLine()) != null) {
				file1Lines.add(file1Line);
				file2Lines.add(file2Line);
				String[] tokens = file2Line.split(":");
				if (tokens.length > 0) {
					file2Keys.add(tokens[0]);
				}
			}

			Object[] sortedFile2Lines = file2Lines.toArray();
			Arrays.sort(sortedFile2Lines);
			Object[] sortedFile2Keys = file2Keys.toArray();
			Arrays.sort(sortedFile2Keys);
			for (int i = 0; i < file1Lines.size(); i++) {
				file1Line = file1Lines.get(i);
				String[] file1LineTokens = file1Line.split(":");
				if (file1LineTokens.length > 0) {
					String key = file1LineTokens[0];
					int indexLine = getLineIndex(key, sortedFile2Keys);
					String[] keyTokens = key.split("#");
					String method = key;
					if (keyTokens.length == 5) {
						method = keyTokens[2] + "." + keyTokens[3];
					}
					if (indexLine < 0) {
						logNonConformance(file1Line, null, "***Result not found in " + os2Name + " file(" + method + "):\n" + key, os1Name, os2Name);
					} else {
						String file1Value = "";
						if (file1LineTokens.length == 2) {
							file1Value = file1LineTokens[1];
						}
						file2Line = (String) sortedFile2Lines[indexLine];
						List<String> file2Values = new ArrayList<>();
						String[] file2LineTokens = file2Line.split(":");
						String file2Key = "";
						String file2Value = "";
						if (file2LineTokens.length == 2) {
							file2Key = file2LineTokens[0];
							file2Value = file2LineTokens[1];
						}
						if (key.equals(file2Key)) {
							if (!file2Value.contains(";")) {
								if (file1Value.contains("@")) {
									file1Value = file1Value.split("@")[0];
								}
								if (file2Value.contains("@")) {
									file2Value = file2Value.split("@")[0];
								}
								if (!file1Value.equals(file2Value)) {
									logNonConformance(file1Line, file2Line, "***Different values(" + method + "):" + file1Value + "\n" + file2Value, os1Name, os2Name);
								}
							} else {
								String[] tokens = file2Value.split(";");
								for (String t : tokens) {
									file2Values.add(t);
								}
								String[] file1ValueTokens = file1Value.split(";");
								if (file1ValueTokens.length > 1) {
									for (String v : file1ValueTokens) {
										if (!file2Values.contains(v)) {
											logNonConformance(file1Line, file2Line, "***Specific value mismatch(" + method + "):" + v, os1Name, os2Name);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getLineIndex(String key, Object[] lines) {
		return Arrays.binarySearch(lines, key);
	}

	private static void logNonConformance(String os1Line, String os2Line, String message, String jvm1, String jvm2) {
		String file = basePath + "diff" + File.separator + jvm1 + "_" + jvm2 + ".txt";
		String result = "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+\n";
		result += message + "\n";
		result += jvm1 + " -> " + os1Line + "\n";
		result += "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+\n";
		result += jvm2 + " -> " + os2Line + "\n";
		result += "+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+\n";
		FileUtil.writeToFile(result, file, true);
	}

	public static void main(String[] args) {
		String jvm1 = args[0];
		String jvm2 = args[1];
		ReflectionOracle.compareResultsInDifferentJVM(jvm1, jvm2);
	}

}
