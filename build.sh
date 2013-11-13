#!/bin/sh

javac -cp "lib/*" -d bin $(find src/* | grep "\.java")
