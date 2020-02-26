package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.repodriller.domain.Commit;
import org.repodriller.scm.SCMRepository;

public class FileUtil {

	/**
	 * Recursive function to descend into the directory tree and find all the
	 * files that end with ".mp3"
	 * 
	 * @param dir
	 *            A file object defining the top directory
	 **/
	public static synchronized ArrayList<String> findFiles(File dir, String pattern, ArrayList<String> files) {
		File listFile[] = dir.listFiles();
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				if (listFile[i].isDirectory()) {
					findFiles(listFile[i], pattern, files);
				} else {
					if (listFile[i].getName().endsWith(pattern)) {
						files.add(listFile[i].getPath());
					}
				}
			}
		}
		return files;
	}

	public static synchronized ArrayList<String> findFolders(File dir, String pattern, ArrayList<String> files) {
		File listFile[] = dir.listFiles();
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				if (listFile[i].isDirectory()) {
					if (!listFile[i].getName().endsWith(pattern)) {
						findFolders(listFile[i], pattern, files);
					} else {
						files.add(listFile[i].getPath());
					}
				}
			}
		}
		return files;
	}

	public static synchronized void writeToFile(String content, String file, boolean append) {
		PrintWriter out = null;
		try {
			// if (file.lastIndexOf(File.separator) != -1) {
			makeDir(file.substring(0, file.lastIndexOf(File.separator)));
			// }
			// out = new PrintWriter(file);
			out = new PrintWriter(new FileOutputStream(new File(file), append));
			out.append(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static synchronized void makeDir(String destPath) {
		File destFile = new File(destPath);
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
	}

	public synchronized String getFileName(String program) {
		String[] programLines = program.split("\n");
		for (String line : programLines) {
			String[] tokens = null;
			if (line.contains("class")) {
				tokens = line.trim().split("class");
			}
			if (line.contains("interface")) {
				tokens = line.trim().split("interface");
			}
			if (line.contains("enum")) {
				tokens = line.trim().split("enum");
			}
			if (tokens != null) {
				String fileName = tokens[1].split(" ")[1];
				if (fileName.contains("<")) {
					fileName = fileName.split("<")[0];
				}
				return fileName + ".java";
			}
		}
		return null;
	}

	public synchronized String getFilesListToCompile(String outputPath) {
		ArrayList<String> files = new ArrayList<String>();
		this.findFiles(new File(outputPath), ".java", files);
		String filesToCompile = "";
		for (String file : files) {
			file = file.replace(File.separator, File.separator + File.separator);
			filesToCompile += "\"" + file + "\" ";
		}
		return filesToCompile;
	}

	public static String getFileLines(String filePath) {
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(new File(filePath)));
			String line = null;
			String ls = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();

	}

	public static void copy(String from, String to) {
		try {
			FileUtils.copyFile(new File(from), new File(to));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void delete(String pomFilePath) {
		new File(pomFilePath).delete();
	}

	public static ArrayList<String> saveBinaries(SCMRepository repository) {
		String destFolder = repository.getPath() + File.separator + "binaries";
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<String> auxFiles = new ArrayList<>();
		ArrayList<String> folders = new ArrayList<>();
		findFolders(new File(repository.getPath()), "classes", folders);
		for (String string : folders) {
			findFiles(new File(string), ".class", auxFiles);
			for (String file : auxFiles) {
				copy(file, destFolder
						+ file.replace(string, ""));
			}
			files.addAll(auxFiles);
			auxFiles.clear();
		}
		
		return files;
		
		
	}

	public static String getProjectName(String projectPath) {
		if (projectPath.contains(File.separator)) {
			String[] tokens = projectPath.split(File.separator);
			return tokens[tokens.length - 1];
		}
		return projectPath;
	}

	public static String getProjectName(SCMRepository repository) {
		return getProjectName(repository.getPath());
	}


}
