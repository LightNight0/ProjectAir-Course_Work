package dataBase;

import essences.*;
import essences.maps.*;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {

    private static Statement statement;
    private static ResultSet rs;

    public static synchronized Object getRequestFromClient(Object request, Socket socket) {
        if (((Essence) request).getDataBaseCommand() == null) return "Wrong request";
        System.out.println("!DEBUG!Запрос от клиента "+socket.getInetAddress()+"  ; тип запроса: "+((Essence) request).getDataBaseCommand());
        switch (((Essence) request).getDataBaseCommand()) {
            case "Select":
                if (request instanceof Crew) return new CrewMap().Select(request);
                else if (request instanceof Flight) return new FlightMap().Select(request);
                else if (request instanceof User) {
                    if(((User) request).getRole()!=null)return new UserMap().Select(request);
                    else return new UserMap().LogIn(request);
                }
                else if (request instanceof Aircraft) return new AircraftMap().Select(request);
                else if (request instanceof Baggage) return new BaggageMap().Select(request);
                else if (request instanceof Passenger) return new PassengerMap().Select(request);
                else if (request instanceof Route) return new RouteMap().Select(request);
                else if (request instanceof Runway) return new RunwayMap().Select(request);
                else if (request instanceof TicketClass) return new TicketClassMap().Select(request);
            case "Add":
                if (request instanceof Crew) return new CrewMap().Add(request);
                else if (request instanceof Flight) return new FlightMap().Add(request);
                else if (request instanceof User) return new UserMap().Add(request);
                else if (request instanceof Aircraft) return new AircraftMap().Add(request);
                else if (request instanceof Baggage) return new BaggageMap().Add(request);
                else if (request instanceof Passenger) return new PassengerMap().Add(request);
                else if (request instanceof Route) return new RouteMap().Add(request);
                else if (request instanceof Runway) return new RunwayMap().Add(request);
                else if (request instanceof TicketClass) return new TicketClassMap().Add(request);
            case "Delete":
                if (request instanceof Crew) return new CrewMap().Delete(request);
                else if (request instanceof Flight) return new FlightMap().Delete(request);
                else if (request instanceof User) return new UserMap().Delete(request);
                else if (request instanceof Aircraft) return new AircraftMap().Delete(request);
                else if (request instanceof Baggage) return new BaggageMap().Delete(request);
                else if (request instanceof Passenger) return new PassengerMap().Delete(request);
                else if (request instanceof Route) return new RouteMap().Delete(request);
                else if (request instanceof Runway) return new RunwayMap().Delete(request);
                else if (request instanceof TicketClass) return new TicketClassMap().Delete(request);
            case "Update":
                if (request instanceof Crew) return new CrewMap().Update(request);
                else if (request instanceof Flight) return new FlightMap().Update(request);
                else if (request instanceof User) return new UserMap().Update(request);
                else if (request instanceof Aircraft) return new AircraftMap().Update(request);
                else if (request instanceof Baggage) return new BaggageMap().Update(request);
                else if (request instanceof Passenger) return new PassengerMap().Update(request);
                else if (request instanceof Route) return new RouteMap().Update(request);
                else if (request instanceof Runway) return new RunwayMap().Update(request);
                else if (request instanceof TicketClass) return new TicketClassMap().Update(request);
            default:
                return "Wrong request";
        }

    }

    public static synchronized void getRequestFromAdmin(String request) {
        String requestType = request.split(" ")[0];
        try {
            statement = DBConnector.getInstance().getConnection().createStatement();

            if (requestType.equalsIgnoreCase("select")) {
                rs = statement.executeQuery(request);
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    String str = rs.getMetaData().getColumnName(i + 1);
                    int k = 25 - str.length();
                    for (int j = 0; j < k; j++)
                        str += " ";
                    System.out.print(str);
                }
                System.out.println("\n==================================================" +
                        "=======================================================" +
                        "=======================================================");
                while (rs.next()) {
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        String str = rs.getString(i + 1);
                        int k = 25 - str.length();
                        for (int j = 0; j < k; j++)
                            str += " ";
                        System.out.print(str);
                    }
                    System.out.println();
                }
                System.out.println();
            } else {
                statement.executeUpdate(request);
                System.out.println("Запрос обработан\n");
            }


        } catch (SQLException e) {
            System.out.println("Wrong request");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void ResultSetIntoArray(String SQLrequest, Statement stmt, ArrayList<String[]> arrayList)
            throws SQLException {
        ResultSet rs;
        rs = stmt.executeQuery(SQLrequest);

        int columnNum = rs.getMetaData().getColumnCount();

        while (rs.next()) {
            String[] row = new String[columnNum];
            for (int i = 0; i < columnNum; i++)
                row[i] = rs.getString(i + 1);
            arrayList.add(row);
        }
    }
}