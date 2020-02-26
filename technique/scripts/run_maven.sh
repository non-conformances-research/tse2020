#!/bin/bash

cd $1
mvn clean compile -fae -Dmaven.repo.local=m2/repository
