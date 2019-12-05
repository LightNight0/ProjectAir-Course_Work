package essences;

import java.io.Serializable;

public class Route extends Essence implements Serializable {

    private String from;
    private String to;
    private String flightTime;
    private int distance;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    @Override
    public String toString(){
        return from+" - "+to;
    }
}
