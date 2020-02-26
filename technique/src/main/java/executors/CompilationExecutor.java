package executors;

import org.repodriller.domain.Commit;
import org.repodriller.scm.SCMRepository;
import util.FileUtil;
import util.OSUtil;

import java.io.File;
import java.io.IOException;

public class CompilationExecutor {

    public static void execute(SCMRepository repository, Commit commit) throws Exception {
        File binariesFolder = new File(repository.getPath() + File.separator + "binaries" + File.separator + commit.getHash());
        boolean alreadyCompiled = binariesFolder.exists();
        compileUsingMaven(repository.getPath(), alreadyCompiled);
        FileUtil.saveBinaries(repository);
    }

    public static void compileUsingMaven(String projectPath, boolean alreadyCompiled) throws Exception {
        String resultsFile = projectPath + File.separator + "mavenresults.txt";
        if (!alreadyCompiled) {
            String shell = "bash";
            String script = OSUtil.getScriptsDir() + File.separator + "run_maven.sh";
            String[] command = {shell, script, projectPath};
            new File(resultsFile);
            OSUtil.runScript(command, resultsFile);
        }
    }

}
