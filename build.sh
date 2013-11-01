#!/bin/sh

/usr/java/default/bin/javac -cp lib/* -d bin $(find src/* | grep "\.java")
