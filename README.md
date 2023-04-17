# Quoted
Repository for Wifi Project on Quote Management App

# Setup
1. Setup a local postgresql server. On MacOS, you can e.g. do that using [Homebrew](https://brew.sh) with the commands `brew install postgresql` and then `brew services start postgresql14`. (Default user will be your local user name without a password.) See [here](https://www.postgresql.org/download/) for examples on how to do that on other platforms.
2. Remove the suffix `.example` from `src/main/resources/META-INF/persistence.xml.example` and replace `USER` and `PASSWORD` accordingly.
3. In case you want to use a different database, you can change the `hibernate.connection.url` and `hibernate.dialect` in `src/main/resources/META-INF/persistence.xml` accordingly. The program has only been tested with postgresql.
4. Create the database `quote_db` either using the `CREATE DATABASE quote_db;` command or by using the [pgAdmin](https://www.pgadmin.org) GUI.
5. In case you want to use a different database name or port, you must change the `hibernate.connection.url` in `src/main/resources/META-INF/persistence.xml` and the `DB_NAME` in `src/main/java/org/nico/quoted/config/BackendConfig.java` accordingly.