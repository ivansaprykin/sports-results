package com.ivansaprykin.dblabs.dao;

import java.net.URI;
import java.net.URISyntaxException;

public class DBCredentials {


    private String dbUrl;
    private String username ;
    private String password ;

    public DBCredentials() {
        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        username = dbUri.getUserInfo().split(":")[0];
        password = dbUri.getUserInfo().split(":")[1];
        dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath() + "?characterEncoding=UTF-8";


    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
