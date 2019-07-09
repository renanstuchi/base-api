#!/bin/bash

set -e

echo -e "========================== Starting DEPLOY =========================="

while !(mysqladmin ping) do
  echo -n "."; sleep 0.2
done

echo -e "========================== Mysql is RUNNIG =========================="
mysql --user=root --password= -e 'CREATE DATABASE IF NOT EXISTS base_api_dev;'

cd /app/api

echo -e $PWD

echo -e "========================== Start Maven Build =========================="

mvn clean package

cp ./target/*.war $TOMCAT_HOME/webapps

echo -e "========================== Copied WAR to Tomcat =========================="

echo -e "========================== Starting FLYWAY =========================="
mvn flyway:clean && mvn flyway:migrate

echo -e "========================== Finished FLYWAY =========================="

exec $TOMCAT_HOME/bin/catalina.sh run

echo -e "========================== Finished DEPLOY =========================="