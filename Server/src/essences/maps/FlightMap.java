package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.Flight;
import essences.IRequests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FlightMap implements IRequests {

    private Flight[] flights;
    private Flight f;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "`flight`";
    private final String colName0 = "`id`";
    private final String colName1 = "`departure_time`";
    private final String colName2 = "`route`";
    private final String colName3 = "`aircraft`";
    private final String colName4 = "`runway`";
    private final String colName5 = "`crew`";
    private final String colName6 = "`days`";

    public FlightMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object Add(Object obj) {
        f = (Flight) obj;

        SQLrequest = "insert into "+TABLE_NAME+" ( "+colName1+", "+colName2+", "+colName3+", "+colName4+
                ", "+colName5+", "+colName6+") values ('"+
                f.getStartTime()+"','"+f.getRoute()+"','"+f.getAircraft()+"','"+f.getStrip()+"', '"+f.getCrew()+
                "', '"+f.getDays()+"');";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }

    @Override
    public Object Select(Object obj) {
        f = (Flight) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();;

        SQLrequest = "Select * from "+TABLE_NAME;
        if(f.getId()!=0)SQLrequest+=" where "+colName0+" = '"+f.getId()+"'";
        else if(f.getRoute()!=0)SQLrequest+=" where "+colName2+" = '"+f.getRoute()+"'";
        else if(f.getStrip()!=0)SQLrequest+=" where "+colName4+" = '"+f.getStrip()+"'";
        else if(f.getAircraft()!=0)SQLrequest+=" where "+colName3+" = '"+f.getAircraft()+"'";
        else if(f.getCrew()!=0)SQLrequest+=" where "+colName5+" = '"+f.getCrew()+"'";
        else if(f.getDays()!=null)SQLrequest+=" where "+colName3+" like '%"+f.getDays()+"%'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        flights = new Flight[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            flights[i] = new Flight();
            flights[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            flights[i].setStartTime(arrayList.get(i)[1]);
            flights[i].setRoute(Integer.parseInt(arrayList.get(i)[2]));
            flights[i].setAircraft(Integer.parseInt(arrayList.get(i)[3]));
            flights[i].setStrip(Integer.parseInt(arrayList.get(i)[4]));
            flights[i].setCrew(Integer.parseInt(arrayList.get(i)[5]));
            flights[i].setDays(arrayList.get(i)[6]);
        }

        return flights;
    }

    @Override
    public Object Delete(Object obj) {
        f = (Flight) obj;

        SQLrequest = "Delete from "+TABLE_NAME;
        if(f.getId()!=0)SQLrequest+=" where id = '"+f.getId()+"'";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            return "Wrong request";
        }
        return "ok";
    }

    @Override
    public Object Update(Object obj) {
        f = (Flight) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t."+colName1+" = '"+f.getStartTime()+
                "', t."+colName2+" = '"+ f.getRoute()+
                "', t."+colName3+" = '"+f.getAircraft()+
                "', t."+colName4+" = '"+f.getStrip()+
                "', t."+colName5+" = '"+f.getCrew()+
                "', t."+colName6+" = '"+f.getDays()+
                "' where t."+colName0+" = '"+f.getId()+"';";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }
}

