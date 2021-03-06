package com.ivansaprykin.dblabs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivansaprykin.dblabs.dao.DBCredentials;
import com.ivansaprykin.dblabs.model.DatabaseTable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


public class GetDatabaseSchemaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
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

            DatabaseMetaData metadata = connection.getMetaData();
            DatabaseTable[] result = getColumnsMetadata(getTablesMetadata(metadata), metadata);

            mapper.writeValue(response.getOutputStream(), result);

        } catch(SQLException e) {
            mapper.writeValue(response.getOutputStream(), e.getMessage());;
        }

    }


    /**
     *
     * @return Arraylist with the table's name
     */
    private ArrayList<String> getTablesMetadata(DatabaseMetaData metadata) throws SQLException {
        String table[] = { "TABLE" };
        ResultSet rs = null;
        ArrayList<String> tables = null;
        // receive the Type of the object in a String array.
        rs = metadata.getTables(null, null, null, table);
        tables = new ArrayList<String>();
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    /**
     * Prints in the console the columns metadata, based in the Arraylist of
     * tables passed as parameter.
     *
     */
    private DatabaseTable[] getColumnsMetadata(ArrayList<String> tables, DatabaseMetaData metadata) throws SQLException {
        DatabaseTable[] result = new DatabaseTable[tables.size()];
        ResultSet rs = null;
        // Print the columns properties of the actual table
        int i = 0;
        for (String actualTable : tables) {
            rs = metadata.getColumns(null, null, actualTable, null);
            DatabaseTable tableInfo = new DatabaseTable();

            tableInfo.setTableName(actualTable.toUpperCase());
            while (rs.next()) {
                tableInfo.getColumns().add(rs.getString("COLUMN_NAME") + " "
                        + rs.getString("TYPE_NAME") + " "
                        + rs.getString("COLUMN_SIZE"));
            }
            result[i] = tableInfo;
            i++;
        }

        return result;
    }
}
