# Hub
The implementation of the hub server

Developed by Choukas.

## Requirements
The following items are required to run the server :
- Java 21
- Git
- MySQL 9

## Installation
1. Install the required software
2. Clone the repository using `git clone https://github.com/pandoniamc/hub.git`
3. Run the server as described below

## Pull the latest version
1. Open a terminal in the project directory
2. Get the latest version of the server using `git pull`
3. Create the database

For Windows, open a new mysql terminal in the project directory.

For Unix, run the following command:
```bash
mysql -u root -p
```
Run the script:
```
source create-database.sql
```
Insert your UUID:
```mysql
USE pandonia;
INSERT INTO players(id) VALUES ('your-uuid');
```
4. Build the project using `./gradlew build`

**How to get my uuid ?** You can find your UUID on https://mcuuid.net/ (You must use the `Full UUID` field)

## Run the server
1. Navigate to the `build/libs` directory (`cd build/libs`)
2. Run the server using `java -jar hub-1.0-SNAPSHOT.jar`

The server will be available as `localhost:25565`.
