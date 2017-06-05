#!/usr/bin/env bash

function run() {
    file_name=$1
    printf ".java\t->\t.class\n"
    javac ${file_name}.java
    printf ".class\t->\t.j\n"
    java -cp ../lib/Jasmin/bcel-5.2.jar:../lib/Jasmin/  JasminVisitor  *.class
	#cat ${file_name}.j | less
}

if [ $# != 1 ] ; then
    run test
else
    run $1
fi
