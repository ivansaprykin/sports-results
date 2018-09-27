package com.ivansaprykin.dblabs.controller.competition_result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivansaprykin.dblabs.dao.DBCredentials;
import com.ivansaprykin.dblabs.model.CompetitionResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GetAllCompetitionResultServlet extends HttpServlet {

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


        List<CompetitionResult> competitionResultList = new ArrayList<>();
        try(
                Connection connection = DriverManager.getConnection(credentials.getDbUrl(), credentials.getUsername(), credentials.getPassword());
                Statement stmt = connection.createStatement();
        ) {


            String selectAllCompetitionResultsSqlQuery = "SELECT * FROM competition_result";
            ResultSet rs = stmt.executeQuery(selectAllCompetitionResultsSqlQuery);
            while(rs.next()){
                CompetitionResult competitionResult = new CompetitionResult();
                competitionResult.setCompetitionNumber(rs.getInt("competition_number"));
                competitionResult.setTeamName(rs.getString("team_name"));
                competitionResult.setTeamPosition(rs.getInt("team_position"));
                competitionResult.setScoredGoals(rs.getInt("scored_goals"));
                competitionResult.setMissedGoals(rs.getInt("missed_goals"));

                competitionResultList.add(competitionResult);
            }


        } catch(SQLException e) {
            mapper.writeValue(response.getOutputStream(), e.getMessage());
        }

        mapper.writeValue(response.getOutputStream(), competitionResultList);
    }
}
