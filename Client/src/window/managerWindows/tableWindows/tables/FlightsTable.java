package window.managerWindows.tableWindows.tables;

import essences.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import webConnection.Connector;

import java.util.Calendar;
import java.util.Date;


public class FlightsTable {

    public static int day1;
    public static int day2;
    public static int day3;
    public static int day4;
    public static int day5;
    public static int day6;
    public static int day7;

    private static ObservableList<Tablo> tablos;
    public static ObservableList<Tablo> getTablos(){
        Calendar day = Calendar.getInstance();
        day.setTime(new Date());
            try {
                ObservableList<FlightsTable> l = getList(String.valueOf(Calendar.DAY_OF_WEEK));
            } catch (Exception e) {
                e.printStackTrace();
        }
        return tablos;
    }

    private int id;
    private String startTime;
    private String endTime;
    private String route;
    private String aircraft;
    private String strip;
    private String crew;
    private String days;

    public static ObservableList<FlightsTable> getList(String days) throws Exception {
        day1=0;day2=0;day3=0;day4=0;day5=0;day6=0;day7=0;
        tablos = FXCollections.observableArrayList();
        ObservableList<FlightsTable> list = FXCollections.observableArrayList();
        Flight f = new Flight();
        f.setDataBaseCommand("Select");
        Connector.getInstance().send(f);
        Object o = Connector.getInstance().get();
        if (o instanceof String) {
            switch ((String) o) {
                case "empty":
                    throw new Exception("empty");
                case "Wrong request":
                    throw new Exception("Wrong request");
            }
        } else {
            Flight[] flights = (Flight[]) o;
            for (int i = 0; i < flights.length; i++) {
                FlightsTable flightsTable = new FlightsTable();
                flightsTable.setId(flights[i].getId());
                flightsTable.setDays(flights[i].getDays());
                String[] str = flights[i].getStartTime().split(":");
                flightsTable.setStartTime(str[0] + ":" + str[1]);
                Route r = new Route();
                r.setDataBaseCommand("Select");
                r.setId(flights[i].getRoute());
                Connector.getInstance().send(r);
                Route[] routes = (Route[]) Connector.getInstance().get();
                flightsTable.setRoute(routes[0].getFrom() + " - " + routes[0].getTo());
                str = routes[0].getFlightTime().split(":");
                int minutes = Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]);
                str = flights[i].getStartTime().split(":");
                minutes += Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]);
                if (minutes > 1439) minutes -= 1440;
                String endTime = minutes / 60 + ":";
                minutes = minutes - (minutes / 60) * 60;
                if (minutes < 10) endTime += "0" + minutes;
                else endTime += minutes;
                flightsTable.setEndTime(endTime);
                Aircraft aircraft = new Aircraft();
                aircraft.setDataBaseCommand("Select");
                aircraft.setId(flights[i].getAircraft());
                Connector.getInstance().send(aircraft);
                o = Connector.getInstance().get();
                Aircraft[] aircrafts = (Aircraft[]) o;
                flightsTable.setAircraft(aircrafts[0].getName());
                Runway strip = new Runway();
                strip.setDataBaseCommand("Select");
                strip.setId(flights[i].getStrip());
                Connector.getInstance().send(strip);
                o = Connector.getInstance().get();
                Runway[] strips = (Runway[]) o;
                flightsTable.setStrip(strips[0].getName());
                Crew crew = new Crew();
                crew.setDataBaseCommand("Select");
                crew.setId(flights[i].getCrew());
                Connector.getInstance().send(crew);
                o = Connector.getInstance().get();
                Crew[] crews = (Crew[]) o;
                flightsTable.setCrew(crews[0].getResponsible() + "(" + crews[0].getCount() + " чел.)");
                if(flightsTable.getDays().indexOf("1")!=-1)day1++;
                if(flightsTable.getDays().indexOf("2")!=-1)day2++;
                if(flightsTable.getDays().indexOf("3")!=-1)day3++;
                if(flightsTable.getDays().indexOf("4")!=-1)day4++;
                if(flightsTable.getDays().indexOf("5")!=-1)day5++;
                if(flightsTable.getDays().indexOf("6")!=-1)day6++;
                if(flightsTable.getDays().indexOf("7")!=-1)day7++;
                if(days.equals("1234567"))list.add(flightsTable);
                else if(flightsTable.getDays().indexOf(days)!=-1){
                    list.add(flightsTable);
                    Tablo t = new Tablo();t.setStartTime(flightsTable.startTime);
                    t.setEndTime(flightsTable.endTime);t.setRoute(flightsTable.route);
                    t.setView(true);
                    tablos.add(t);
                }
            }
        }

        return list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getStrip() {
        return strip;
    }

    public void setStrip(String strip) {
        this.strip = strip;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
