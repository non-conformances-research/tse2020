# A Technique to Test APIs Specified in Natural Language (Artifacts)

## Technique

### Source Code

 * [View source code](technique)
 * Clone repository:

 ```
 git clone https://github.com/non-conformances-research/tse2020.git
 cd tse2020/technique
 ```
 
## Evaluation

 * [Testing the Java Reflection API](reflection/README.md)
 * [Testing the Java Collections API](collections/README.md)

### How to run the automatic steps of our technique

 * Requirements
	* Linux
	* Maven 3.3+
	* Java Virtual Machines:
	  * [Oracle 1.8.0_151](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)
	  * [OpenJDK 1.8.0_141](http://hg.openjdk.java.net/jdk8u/jdk8u/archive/jdk8u141-b15.tar.bz2)
	  * [Eclipse OpenJ9 0.8.0](https://github.com/AdoptOpenJDK/openjdk8-openj9-releases/releases/download/jdk8u162-b12_openj9-0.8.0/OpenJDK8-OPENJ9_x64_Linux_jdk8u162-b12_openj9-0.8.0.tar.gz)
	  * [IBM J9 8.0.5.10](https://www-01.ibm.com/support/docview.wss?uid=swg24042430#80510)
	* Python 2.7
	* Pip
	* Vrtualenv
 * Install all JVMs
 * Run the following commands
	```
	cd classifier
	virtualenv env
	source env/bin/activate
	pip install -r requirements.txt
	deactivate
	cd ..
	export ORACLE_PATH=<oracle_jvm_installation_path>
	export OPENJDK_PATH=<openjdk_jvm_installation_path>
	export ECLIPSE_OPENJ9_PATH=<eclipse_openj9_jvm_installation_path>
	export IBM_J9_PATH=<ibm_j9_jvm_installation_path>
	cd technique
	sh scripts/run_fse_experiment.sh
	
	```

