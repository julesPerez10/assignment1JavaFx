package com.example.assignmentjava;

public class FootballStatistics {
    private String player;
    private int season;
    private int goals;
    private int assists;

    public FootballStatistics(String player, int season, int goals, int assists) {
        this.player = player;
        this.season = season;
        this.goals = goals;
        this.assists = assists;
    }

    public String getPlayer() {
        return player;
    }

    public int getSeason() {
        return season;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }
}
