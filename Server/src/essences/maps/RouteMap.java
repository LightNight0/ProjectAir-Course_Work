package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.IRequests;
import essences.Route;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RouteMap implements IRequests {
    private Route[] routes;
    private Route r;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "`route`";
    private final String colName0 = "`id`";
    private final String colName1 = "`departure_point`";
    private final String colName2 = "`arrival_point`";
    private final String colName3 = "`flight_time`";
    private final String colName4 = "`distance`";

    public RouteMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object Add(Object obj) {
        r = (Route) obj;

        SQLrequest = "insert into "+TABLE_NAME+" ( "+colName1+", "+colName2+", "+colName3+", "+colName4+") values ('"+
                r.getFrom()+"','"+r.getTo()+"','"+r.getFlightTime()+"','"+r.getDistance()+"');";
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
        r = (Route) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();

        SQLrequest = "Select * from "+TABLE_NAME;
        if(r.getId()!=0)SQLrequest+=" where "+colName0+" = '"+r.getId()+"'";
        else if(r.getFrom()!=null)SQLrequest+=" where "+colName1+" = '"+r.getFrom()+"'";
        else if(r.getTo()!=null)SQLrequest+=" where "+colName2+" = '"+r.getTo()+"'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        routes = new Route[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            routes[i] = new Route();
            routes[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            routes[i].setFrom(arrayList.get(i)[1]);
            routes[i].setTo(arrayList.get(i)[2]);
            routes[i].setFlightTime(arrayList.get(i)[3]);
            routes[i].setDistance(Integer.parseInt(arrayList.get(i)[4]));
        }

        return routes;
    }

    @Override
    public Object Delete(Object obj) {
        r = (Route) obj;

        SQLrequest = "Delete from "+TABLE_NAME;
        if(r.getId()!=0)SQLrequest+=" where id = '"+r.getId()+"'";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            return "Wrong request";
        }
        return "ok";
    }

    @Override
    public Object Update(Object obj) {
        r = (Route) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t."+colName1+" = '"+r.getFrom()+"', t."+colName2+" = '"+
                r.getTo()+"', t."+colName3+" = '"+r.getFlightTime()+"', t."+colName4+" = '"+r.getDistance()+
                "' where t."+colName0+" = '"+r.getId()+"';";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }
}
