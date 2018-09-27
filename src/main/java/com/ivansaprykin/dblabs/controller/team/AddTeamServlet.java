package com.ivansaprykin.dblabs.controller.team;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivansaprykin.dblabs.dao.DBCredentials;
import com.ivansaprykin.dblabs.model.Team;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;


public class AddTeamServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        BufferedReader br = null;
        Team team = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String json = br.readLine();

            team = mapper.readValue(json, Team.class);
        } catch(IOException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            String exceptionString = "Error happened while trying to get articleId from JSON file!" + e;
            mapper.writeValue(response.getOutputStream(), exceptionString);
        } finally {
            if(null == team) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                String exceptionString = "Error happened while trying to get articleId from JSON file!" + " articleId == null";
                mapper.writeValue(response.getOutputStream(), exceptionString);
            }
        }

        DBCredentials credentials = new DBCredentials();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            mapper.writeValue(response.getOutputStream(), e.getMessage());
        }

        try(
                Connection connection = DriverManager.getConnection(credentials.getDbUrl(), credentials.getUsername(), credentials.getPassword());
                Statement stmt = connection.createStatement();
        ) {

            String insertIntoTeamTableSqlQuery = "INSERT INTO team VALUES(" +
                    team.getTeamName() + ", " + team.getLeague() + ", " + team.getCoachFullName() + ", " + team.getDoctorFullName() +
                    ")";

            int i = stmt.executeUpdate(insertIntoTeamTableSqlQuery);
            if(i != 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(response.getOutputStream(), "ok");
            } else {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                mapper.writeValue(response.getOutputStream(), "Row is not created");
            }
        } catch(SQLException e) {
            mapper.writeValue(response.getOutputStream(), e.getMessage());
        }


    }
}
