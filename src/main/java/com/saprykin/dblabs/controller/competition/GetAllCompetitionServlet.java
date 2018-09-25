package com.saprykin.dblabs.controller.competition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saprykin.dblabs.model.Competition;
import com.saprykin.dblabs.model.Team;

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

        List<Competition> competitions = new ArrayList<>();
        try(
                Connection conn = DriverManager.getConnection(dbUrl, username, password);
                Statement stmt = conn.createStatement();
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
