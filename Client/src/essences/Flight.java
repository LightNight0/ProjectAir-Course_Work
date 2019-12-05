package essences;

import java.io.Serializable;

public class Flight extends Essence implements Serializable {

    private String startTime;
    private int route;
    private int aircraft;
    private int strip;
    private int crew;
    private String days;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public int getAircraft() {
        return aircraft;
    }

    public void setAircraft(int aircraft) {
        this.aircraft = aircraft;
    }

    public int getStrip() {
        return strip;
    }

    public void setStrip(int strip) {
        this.strip = strip;
    }


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getCrew() {
        return crew;
    }

    public void setCrew(int crew) {
        this.crew = crew;
    }
}
