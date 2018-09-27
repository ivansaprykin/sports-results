package com.ivansaprykin.dblabs.dao;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBCreator {

    public DBCredentials credentials;

    public void createDB() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
        }

        credentials = new DBCredentials();

        try(
                Connection connection = DriverManager.getConnection(credentials.getDbUrl(), credentials.getUsername(), credentials.getPassword());
                Statement stmt = connection.createStatement();
        ) {
            String dropAllTablesIfExist = "DROP TABLE IF EXISTS sportsman_result, competition_result, sportsman, team, competition";
            stmt.executeUpdate(dropAllTablesIfExist);

            String createCompetitionTableSqlQuery = "CREATE TABLE IF NOT EXISTS competition (" +
                    "competition_number INT NOT NULL PRIMARY KEY, " +
                    "title VARCHAR(120) NOT NULL, " +
                    "competition_date DATE" +
                    ")";
            stmt.executeUpdate(createCompetitionTableSqlQuery);

            String createTeamTableSqlQuery = "CREATE TABLE IF NOT EXISTS team (" +
                    "team_name VARCHAR(50) NOT NULL PRIMARY KEY, " +
                    "league VARCHAR(50), " +
                    "coach_full_name VARCHAR(80), " +
                    "doctor_full_name VARCHAR(80)" +
                    ")";
            stmt.executeUpdate(createTeamTableSqlQuery);

            String createSportsmanTableSqlQuery = "CREATE TABLE IF NOT EXISTS sportsman (" +
                    "passport_number INT NOT NULL PRIMARY KEY, " +
                    "full_name VARCHAR(80) NOT NULL, " +
                    "birth_date DATE, " +
                    "profession VARCHAR(50), " +
                    "team_role VARCHAR(50), " +
                    "team_name VARCHAR(50)," +
                    "FOREIGN KEY (team_name) REFERENCES team(team_name)" +
                    ")";
            stmt.executeUpdate(createSportsmanTableSqlQuery);


            String createCompetitionResultTableSqlQuery = "CREATE TABLE IF NOT EXISTS competition_result (" +
                    "competition_number INT NOT NULL, " +
                    "team_name VARCHAR(50) NOT NULL, " +
                    "team_position INT, " +
                    "scored_goals INT, " +
                    "missed_goals INT, " +
                    "PRIMARY KEY (competition_number, team_name), " + //CONSTRAINT pk_competition_result
                    // CONSTRAINT fk_competition_result
                    "FOREIGN KEY (competition_number) REFERENCES competition(competition_number), " +
                    "FOREIGN KEY (team_name) REFERENCES team(team_name)" +
                    ")";
            stmt.executeUpdate(createCompetitionResultTableSqlQuery);

            String createSportsmanResultTableSqlQuery = "CREATE TABLE IF NOT EXISTS sportsman_result (" +
                    "competition_number INT NOT NULL, " +
                    "team_name VARCHAR(50) NOT NULL, " +
                    "passport_number INT NOT NULL, " +
                    "scored_goals INT, " +
                    // CONSTRAINT pk_sportsman_result    CONSTRAINT fk_sportsman_result
                    "PRIMARY KEY (competition_number, team_name, passport_number), " +
                    "FOREIGN KEY (competition_number) REFERENCES competition(competition_number), " +
                    "FOREIGN KEY (team_name) REFERENCES team(team_name), " +
                    "FOREIGN KEY (passport_number) REFERENCES sportsman(passport_number)" +
                    ")";
            stmt.executeUpdate(createSportsmanResultTableSqlQuery);


            String insertIntoTeamTableSqlQuery = "INSERT INTO team (team_name, league, coach_full_name, doctor_full_name) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertIntoTeamTableSqlQuery);

            preparedStatement.setString(1, "Спартак");
            preparedStatement.setString(2, "Высшая лига");
            preparedStatement.setString(3, "Иванов Иван Иванович");
            preparedStatement.setString(4, "Петров Петр Петрович");
            preparedStatement.execute();

            preparedStatement.setString(1, "Динамо");
            preparedStatement.setString(2, "Высшая лига");
            preparedStatement.setString(3, "Петров Петр Петрович");
            preparedStatement.setString(4, "Иванов Иван Иванович");
            preparedStatement.execute();

            preparedStatement.setString(1, "ЦСК");
            preparedStatement.setString(2, "Высшая лига");
            preparedStatement.setString(3, "Иванов Петр Иванович");
            preparedStatement.setString(4, "Петров Иван Петрович");
            preparedStatement.execute();

            preparedStatement.setString(1, "Таврия");
            preparedStatement.setString(2, "Высшая лига");
            preparedStatement.setString(3, "Петров Иван Иванович");
            preparedStatement.setString(4, "Иванов Петр Петрович");
            preparedStatement.execute();

            String insertIntoCompetitionTableSqlQuery = "INSERT INTO competition (competition_number, title, competition_date) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(insertIntoCompetitionTableSqlQuery);


            String date = "2015-07-18";
            java.sql.Date sqlDate = null;
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date myDate = formatter.parse(date);
                sqlDate = new java.sql.Date(myDate.getTime());

            } catch (ParseException e) {
            }
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Карибский клубный чемпионат");
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.execute();


            date = "2016-03-22";
            sqlDate = null;
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date myDate = formatter.parse(date);
                sqlDate = new java.sql.Date(myDate.getTime());

            } catch (ParseException e) {

            }
            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Match World Cup 2016");
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.execute();


            String insertIntoCompetitionResultTableSqlQuery = "INSERT INTO competition_result (competition_number, team_name, team_position, scored_goals, missed_goals) VALUES (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(insertIntoCompetitionResultTableSqlQuery);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Спартак");
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, 6);
            preparedStatement.setInt(5, 2);
            preparedStatement.execute();

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Динамо");
            preparedStatement.setInt(3, 2);
            preparedStatement.setInt(4, 5);
            preparedStatement.setInt(5, 3);
            preparedStatement.execute();

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "ЦСК");
            preparedStatement.setInt(3, 4);
            preparedStatement.setInt(4, 2);
            preparedStatement.setInt(5, 6);
            preparedStatement.execute();

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Таврия");
            preparedStatement.setInt(3, 3);
            preparedStatement.setInt(4, 4);
            preparedStatement.setInt(5, 5);
            preparedStatement.execute();



        } catch(SQLException e) {

        }
    }
}
