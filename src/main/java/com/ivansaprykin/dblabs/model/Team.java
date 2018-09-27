package com.ivansaprykin.dblabs.model;


public class Team {
    /*
     "team_name VARCHAR(50) NOT NULL PRIMARY KEY, " +
                    "league VARCHAR(50), " +
                    "coach_full_name VARCHAR(80), " +
                    "doctor_full_name VARCHAR(80)" +
     */

    private String teamName;
    private String league;
    private String coachFullName;
    private String doctorFullName;

    public Team() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getCoachFullName() {
        return coachFullName;
    }

    public void setCoachFullName(String coachFullName) {
        this.coachFullName = coachFullName;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }
}
