#!/bin/bash

for i in {1..50}
do
  java -jar target/Assignment1-1.0-SNAPSHOT.jar "$i"
  sleep 1
done