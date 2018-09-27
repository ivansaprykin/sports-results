package com.ivansaprykin.dblabs.controller.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivansaprykin.dblabs.dao.DBCredentials;
import com.ivansaprykin.dblabs.model.Team;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GetAllTeamsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        DBCredentials credentials = new DBCredentials();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            mapper.writeValue(response.getOutputStream(), e.getMessage());
        }

        List<Team> teams = new ArrayList<>();
        try(
                Connection connection = DriverManager.getConnection(credentials.getDbUrl(), credentials.getUsername(), credentials.getPassword());
                Statement stmt = connection.createStatement();
        ) {


            String selectAllTeamsSqlQuery = "SELECT * FROM team";
            ResultSet rs = stmt.executeQuery(selectAllTeamsSqlQuery);
            while(rs.next()){
                Team team = new Team();
                team.setTeamName(rs.getString("team_name"));
                team.setLeague(rs.getString("league"));
                team.setCoachFullName(rs.getString("coach_full_name"));
                team.setDoctorFullName(rs.getString("doctor_full_name"));

                teams.add(team);
            }


        } catch(SQLException e) {
            mapper.writeValue(response.getOutputStream(), e.getMessage());
        }


        mapper.writeValue(response.getOutputStream(), teams);
    }
}


/*

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            mapper.writeValue(response.getOutputStream(), e.getMessage());
        }

        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String dbUrl = "jdbc:mysql://" + host + ":" + port + "/dblabs";
        String username = "admind6SUcGH";
        String password = "GS476LZ2W1Ni";

        try(
                Connection conn = DriverManager.getConnection(dbUrl, username, password);
                Statement stmt = conn.createStatement();
        ) {



        } catch(SQLException e) {
            mapper.writeValue(response.getOutputStream(), e.getMessage());
        }


//        mapper.writeValue(response.getOutputStream(), allArticles);
    }

 */