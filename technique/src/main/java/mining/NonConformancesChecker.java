package mining;

import executors.CompilationExecutor;
import executors.GeneralExecutor;
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class NonConformancesChecker implements CommitVisitor {

	public NonConformancesChecker() {
		super();
	}

	public String name() {
		return "Non-conformances Checker";
	}

	public synchronized void process(SCMRepository repository, Commit commit, PersistenceMechanism persistenceMechanism) {
		try {
			CompilationExecutor.execute(repository, commit);
			GeneralExecutor.execute(repository, commit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}