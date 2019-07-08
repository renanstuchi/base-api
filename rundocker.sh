#!/bin/bash
docker run -p 8080:8080 -p 3306:3306 -e MYSQL_DATABASE=base_api_dev --rm base-api