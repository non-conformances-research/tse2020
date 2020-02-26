package executors;

import org.repodriller.domain.Commit;
import org.repodriller.scm.SCMRepository;
import util.Binary;
import util.FileUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralExecutor {

	private static Map<String, URLClassLoader> classLoaders = new HashMap<>();

	protected static List<Binary> getBinaries(File binariesFolder) {
		ArrayList<String> binaryFilesPath = new ArrayList<String>();
		FileUtil.findFiles(binariesFolder, ".class", binaryFilesPath);
		List<Binary> binaryFiles = new ArrayList<Binary>();
		for (int i = 0; i < binaryFilesPath.size(); i++) {
			binaryFiles.add(new Binary(binaryFilesPath.get(i)));
		}
		return binaryFiles;
	}

	public static Class<?> loadClass(String projectPath, Binary binary, boolean initialize) {
		Class<?> loadedBinary = null;
		String classFileName = getClassFileName(binary);
		try {
			URLClassLoader classLoader = getClassLoader(projectPath);
			loadedBinary = Class.forName(classFileName, initialize, classLoader);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return loadedBinary;
	}

	private static URLClassLoader getClassLoader(String projectPath) {
		URLClassLoader classLoader = null;
		String key = projectPath;
		if (!classLoaders.containsKey(key)) {
			classLoaders.clear();
			ArrayList<String> jarFiles = new ArrayList<String>();
			FileUtil.findFiles(
					new File(projectPath + File.separator + "m2" + File.separator), ".jar", jarFiles );
			ArrayList<String> binaryFolders = new ArrayList<String>();
			FileUtil.findFolders(new File(projectPath), "binaries", binaryFolders);
			URL[] classLoaderURLs = new URL[jarFiles.size() + binaryFolders.size()];
			int index = 0;
			try {
				for (String binaryFolder : binaryFolders) {
					classLoaderURLs[index] = new File(binaryFolder).toURI().toURL();
					index++;
				}

				for (String jarFile : jarFiles) {
					classLoaderURLs[index] = new File(jarFile).toURI().toURL();
					index++;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			classLoader = new URLClassLoader(classLoaderURLs);
			classLoaders.put(key, classLoader);
		} else {
			classLoader = classLoaders.get(key);
		}
		return classLoader;
	}

	private static String getClassFileName(Binary binary) {
		String classFileName = "";
		String splitChar = File.separator;
		String[] split = binary.getAbsolutePath().split(splitChar);
		ArrayList<String> packages = new ArrayList<String>();
		for (int i = split.length - 2; i >= 0; i--) {
			if (split[i].equals("binaries")) {
				break;
			}
			packages.add(split[i]);
		}
		for (int i = packages.size() - 1; i >= 0; i--) {
			classFileName += packages.get(i) + ".";
		}
		classFileName += split[split.length - 1].substring(0,
				split[split.length - 1].indexOf("."));
		return classFileName;
	}

	public static void execute(SCMRepository repository, Commit commit) {
		File binariesFolder = new File(repository.getPath() + File.separator + "binaries");
		List<Binary> binaries = GeneralExecutor.getBinaries(binariesFolder);
		for (int j = 0; j < binaries.size(); j++) {
			Binary binaryFile = new Binary(binaries.get(j).getAbsolutePath());
			try {
				ReflectionAPIExecutor.execute(repository.getPath(), binaryFile,false);
			} catch (Error e) {
			}
		}
	}

}
