package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.Aircraft;
import essences.IRequests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AircraftMap implements IRequests {

    private Aircraft[] aircrafts;
    private Aircraft a;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "`aircraft`";

    public AircraftMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object Add(Object obj) {
        a = (Aircraft) obj;

        SQLrequest = "insert into "+TABLE_NAME+" (`name`, `type`, `seats_amount`, " +
                " `business_seats_amount`) values ('"+
                a.getName()+"','"+a.getType()+"','"+a.getSeatCount()+"','"+a.getBusinessSeatCount()+"');";
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
        a = (Aircraft) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();;

        SQLrequest = "Select * from "+TABLE_NAME;
        if(a.getId()!=0)SQLrequest+=" where id = '"+a.getId()+"'";
        else if(a.getType()!=null)SQLrequest+=" where type = '"+a.getType()+"'";
        else if(a.getName()!=null)SQLrequest+=" where name = '"+a.getName()+"'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        aircrafts = new Aircraft[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            aircrafts[i] = new Aircraft();
            aircrafts[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            aircrafts[i].setName(arrayList.get(i)[1]);
            aircrafts[i].setType(arrayList.get(i)[2]);
            aircrafts[i].setSeatCount(Integer.parseInt(arrayList.get(i)[3]));
            aircrafts[i].setBusinessSeatCount(Integer.parseInt(arrayList.get(i)[4]));
        }

        return aircrafts;
    }

    @Override
    public Object Delete(Object obj) {
        a = (Aircraft) obj;

        SQLrequest = "Delete from "+TABLE_NAME;
        if(a.getId()!=0)SQLrequest+=" where id = '"+a.getId()+"'";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            return "Wrong request";
        }
        return "ok";
    }

    @Override
    public Object Update(Object obj) {
        a = (Aircraft) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t.`name` = '"+a.getName()+"', t.`type` = '"+
                a.getType()+"', t.`seats_amount` = '"+
                a.getSeatCount()+"', t.`business_seats_amount` = '"+
                a.getBusinessSeatCount()+"' where t.`id` = '"+a.getId()+"';";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }
}
