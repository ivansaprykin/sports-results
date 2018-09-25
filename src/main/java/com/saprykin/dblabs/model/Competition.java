package com.saprykin.dblabs.model;

import java.util.Date;


public class Competition {

    private int competition_number;
    private String title;
    private Date competition_date;

    public Competition() {

    }

    public int getCompetition_number() {
        return competition_number;
    }

    public void setCompetition_number(int competition_number) {
        this.competition_number = competition_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCompetition_date() {
        return competition_date;
    }

    public void setCompetition_date(Date competition_date) {
        this.competition_date = competition_date;
    }
}
