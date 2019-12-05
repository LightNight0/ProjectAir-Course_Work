package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.Passenger;
import essences.IRequests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PassengerMap implements IRequests {

    private Passenger[] passengers;
    private Passenger p;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "`passenger`";
    private final String colName0 = "`id`";
    private final String colName1 = "`class`";
    private final String colName2 = "`place_number`";
    private final String colName3 = "`flight`";
    private final String colName4 = "`full_name`";

    public PassengerMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Object Add(Object obj) {
        p = (Passenger) obj;

        SQLrequest = "insert into "+TABLE_NAME+" ( "+colName1+", "+colName2+", "+colName3+", "+colName4+") values ('"+
                p.getPassengerClass()+"','"+p.getSeatNumber()+"','"+p.getFlight()+"','"+p.getFIO()+"');";
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
        p = (Passenger) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();;

        SQLrequest = "Select * from "+TABLE_NAME;
        if(p.getId()!=0)SQLrequest+=" where "+colName0+" = '"+p.getId()+"'";
        else if(p.getFlight()!=0)SQLrequest+=" where "+colName3+" = '"+p.getFlight()+"'";
        else if(p.getPassengerClass()!=0)SQLrequest+=" where "+colName1+" = '"+p.getPassengerClass()+"'";
        else if(p.getSeatNumber()!=0)SQLrequest+=" where "+colName2+" = '"+p.getSeatNumber()+"'";
        else if(p.getFIO()!=null)SQLrequest+=" where "+colName4+" like '%"+p.getFIO()+"%'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        passengers = new Passenger[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            passengers[i] = new Passenger();
            passengers[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            passengers[i].setPassengerClass(Integer.parseInt(arrayList.get(i)[1]));
            passengers[i].setSeatNumber(Integer.parseInt(arrayList.get(i)[2]));
            passengers[i].setFlight(Integer.parseInt(arrayList.get(i)[3]));
            passengers[i].setFIO(arrayList.get(i)[4]);
        }

        return passengers;
    }

    @Override
    public Object Delete(Object obj) {
        p = (Passenger) obj;

        SQLrequest = "Delete from "+TABLE_NAME;
        if(p.getId()!=0)SQLrequest+=" where id = '"+p.getId()+"'";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            return "Wrong request";
        }
        return "ok";
    }

    @Override
    public Object Update(Object obj) {
        p = (Passenger) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t."+colName1+" = '"+p.getPassengerClass()+"', t."+colName2+" = '"+
                p.getSeatNumber()+"', t."+colName3+" = '"+p.getFlight()+"', t."+colName4+" = '"+p.getFIO()+
                "' where t."+colName0+" = '"+p.getId()+"';";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }
}
