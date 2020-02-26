#!/bin/bash

#Compile project
mvn clean compile

#Run test cases for JVM
export JAVA_HOME=$ORACLE_PATH
mvn exec:java -Dexec.mainClass="mining.NonConformancesStudy" -Dexec.args="oracle"
export JAVA_HOME=$ECLIPSE_OPENJ9_PATH
mvn exec:java -Dexec.mainClass="mining.NonConformancesStudy" -Dexec.args="eclipse-openj9"
export JAVA_HOME=$OPENJDK_PATH
mvn exec:java -Dexec.mainClass="mining.NonConformancesStudy" -Dexec.args="openjdk"
export JAVA_HOME=$IBM_J9_PATH
mvn exec:java -Dexec.mainClass="mining.NonConformancesStudy" -Dexec.args="ibm-j9"


#Run oracle
export JAVA_HOME=$ORACLE_PATH
mvn exec:java -Dexec.mainClass="oracles.ReflectionOracle" -Dexec.args="oracle eclipse-openj9"
mvn exec:java -Dexec.mainClass="oracles.ReflectionOracle" -Dexec.args="oracle openjdk"
mvn exec:java -Dexec.mainClass="oracles.ReflectionOracle" -Dexec.args="oracle ibm-j9"
mvn exec:java -Dexec.mainClass="oracles.ReflectionOracle" -Dexec.args="eclipse-openj9 openjdk"
mvn exec:java -Dexec.mainClass="oracles.ReflectionOracle" -Dexec.args="eclipse-openj9 ibm-j9"
mvn exec:java -Dexec.mainClass="oracles.ReflectionOracle" -Dexec.args="openjdk ibm-j9"


cd classifier
sh filter_results.sh
virtualenv env
source env/bin/activate

for nc in `cat non-conformances.txt`; do
        python bugs_classifier.py results/${nc}.txt; 
done

deactivate
