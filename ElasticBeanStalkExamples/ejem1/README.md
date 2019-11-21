## Create Database

Run a new container
```
docker run --name aws-eb-mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:5.7
```

Create database
```
$ docker exec -it aws-eb-mysql bash
# mysql -uroot -proot
mysql> CREATE DATABASE aws_eb_db;
```

# Build app

```
mvn clean package
```