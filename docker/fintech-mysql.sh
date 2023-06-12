docker run -d \
--name fintech-mysql \
-e MYSQL_ROOT_PASSWORD="1q2w3e4r!@" \
-e MYSQL_USER="root" \
-e MYSQL_PASSWORD="1q2w3e4r!@" \
-e MYSQL_DATABASE="fintech" \
-p 3306:3306 \
--network docker_fintech mysql:latest