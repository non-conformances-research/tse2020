package mining;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.scm.GitRepository;
import util.PropertiesManager;
import util.Repo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class NonConformancesStudy implements Study {

	protected static String REPO_BASE_DIR;
	protected static String BASE_PATH;
	protected String jvm;
	private static NonConformancesStudy instance;

	private NonConformancesStudy() {
		PropertiesManager properties = new PropertiesManager();
		REPO_BASE_DIR = properties.getProperty("repositories_dir");
		BASE_PATH = properties.getProperty("project_workspace");
		properties.close();
	}

	public synchronized static NonConformancesStudy getInstance() {
		if (instance == null) {
			instance = new NonConformancesStudy();
		}
		return instance;
	}

	public void setJvm(String jvm) {
		this.jvm = jvm;
	}

	public String getJvm() {
		return this.jvm;
	}

	public void execute() {
		try {
			cloneRepositories();
			new RepositoryMining().in(GitRepository.allProjectsIn(REPO_BASE_DIR)).through(Commits.onlyInHead())
					.process(new NonConformancesChecker()).mine();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	protected void cloneRepositories() {
		try {
			Gson gson = new GsonBuilder().create();

			@SuppressWarnings("serial")
			List<Repo> repositories = gson.fromJson(getFile("repositories.json"), new TypeToken<List<Repo>>() {
			}.getType());

			for (Repo repo : repositories) {
				File path = new File(REPO_BASE_DIR + repo.getPath());

				if (!path.exists()) {
					// FileUtils.deleteDirectory(repositories.get(uri));
					System.out.println("Cloning repository " + repo.getUrl());
					Git git = Git.cloneRepository().setURI(repo.getUrl()).setDirectory(path).call();
					git.close();
				}
			}
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		} catch (TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	private String getFile(String fileName) {

		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	}

	public static void main(String[] args) {
		NonConformancesStudy instance = NonConformancesStudy.getInstance();
		instance.setJvm(args[0]);
		new RepoDriller().start(instance);
	}

}
