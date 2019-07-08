#!/bin/bash
echo -e "----- Starting FlywayScript ----"

while !(mysqladmin ping) do
  echo -n "."; sleep 0.2
done

echo -e "----- Mysql is RUNNIG ----"
mysql --user=root --password= -e 'CREATE DATABASE IF NOT EXISTS base_api_dev;'

cd /app/api

echo -e $PWD

echo -e "----- Starting FLYWAY ----"
mvn flyway:clean && mvn flyway:migrate

echo -e "----- Finished FLYWAY ----"