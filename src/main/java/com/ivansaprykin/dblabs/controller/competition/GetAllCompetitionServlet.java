package com.ivansaprykin.dblabs.controller.competition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivansaprykin.dblabs.dao.DBCredentials;
import com.ivansaprykin.dblabs.model.Competition;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetAllCompetitionServlet extends HttpServlet {

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



        List<Competition> competitions = new ArrayList<>();
        try(
                Connection connection = DriverManager.getConnection(credentials.getDbUrl(), credentials.getUsername(), credentials.getPassword());
                Statement stmt = connection.createStatement();
        ) {

            String selectAllTeamsSqlQuery = "SELECT * FROM competition";
            ResultSet rs = stmt.executeQuery(selectAllTeamsSqlQuery);
            while(rs.next()){
                Competition competition = new Competition();
                competition.setCompetition_number(rs.getInt("competition_number"));
                competition.setTitle(rs.getString("title"));
                competition.setCompetition_date(rs.getDate("competition_date"));

                competitions.add(competition);
            }

        } catch(SQLException e) {
            mapper.writeValue(response.getOutputStream(), e.getMessage());
        }

        mapper.writeValue(response.getOutputStream(), competitions);
    }
}
