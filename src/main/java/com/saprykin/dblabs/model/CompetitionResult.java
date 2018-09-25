package com.saprykin.dblabs.model;


public class CompetitionResult {

    private int competitionNumber;
    private String teamName;
    private int teamPosition;
    private int scoredGoals;
    private int missedGoals;

    public CompetitionResult() {
    }

    public int getCompetitionNumber() {
        return competitionNumber;
    }

    public void setCompetitionNumber(int competitionNumber) {
        this.competitionNumber = competitionNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamPosition() {
        return teamPosition;
    }

    public void setTeamPosition(int teamPosition) {
        this.teamPosition = teamPosition;
    }

    public int getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public int getMissedGoals() {
        return missedGoals;
    }

    public void setMissedGoals(int missedGoals) {
        this.missedGoals = missedGoals;
    }
}
