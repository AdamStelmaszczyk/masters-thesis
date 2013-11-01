#!/bin/sh

javac -d bin -source 1.4 -target 1.4 $(find src/* | grep "\.java")
