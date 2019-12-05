package window.managerWindows.tableWindows.tables;

import essences.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import webConnection.Connector;

public class RoutesTable {

    private int id;
    private String from;
    private String to;
    private String flightTime;
    private String distance;

    public static ObservableList<RoutesTable> getList() throws Exception {
        ObservableList<RoutesTable> list = FXCollections.observableArrayList();
        RoutesTable[] routesTables;
        Route r = new Route();
        r.setDataBaseCommand("Select");
        Connector.getInstance().send(r);
        Object o = Connector.getInstance().get();
        if (o instanceof String) {
            switch ((String) o) {
                case "empty":
                    throw new Exception("empty");
                case "wrong request":
                    throw new Exception("Wrong request");
            }
        } else {
            Route[] routes = (Route[]) o;
            for (int i = 0; i < routes.length; i++) {
                RoutesTable routesTable = new RoutesTable();
                routesTable.id = routes[i].getId();
                routesTable.distance = routes[i].getDistance()+" км";
                String[] str = routes[i].getFlightTime().split(":");
                routesTable.flightTime = str[0]+" ч, "+str[1]+" мин";
                routesTable.from = "из "+routes[i].getFrom();
                routesTable.to = "в "+routes[i].getTo();
                list.add(routesTable);
            }
        }
        return list;
    }

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
