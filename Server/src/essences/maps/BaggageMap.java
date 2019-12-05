package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.Baggage;
import essences.IRequests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BaggageMap implements IRequests {

    private Baggage[] baggages;
    private Baggage l;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "`baggage`";
    private final String colName0 = "`id`";
    private final String colName1 = "`size`";
    private final String colName2 = "`place_number`";
    private final String colName3 = "`owner`";

    public BaggageMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object Add(Object obj) {
        l = (Baggage) obj;

        SQLrequest = "insert into "+TABLE_NAME+" ( "+colName1+", "+colName2+", "+colName3+") values ('"+
                l.getSize()+"','"+l.getSectionNumber()+"','"+l.getOwner()+"');";
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
        l = (Baggage) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();;

        SQLrequest = "Select * from "+TABLE_NAME;
        if(l.getId()!=0)SQLrequest+=" where "+colName0+" = '"+l.getId()+"'";
        else if(l.getSectionNumber()!=0)SQLrequest+=" where "+colName2+" = '"+l.getSectionNumber()+"'";
        else if(l.getOwner()!=0)SQLrequest+=" where "+colName3+" = '"+l.getOwner()+"'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        baggages = new Baggage[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            baggages[i] = new Baggage();
            baggages[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            baggages[i].setSize(arrayList.get(i)[1]);
            baggages[i].setSectionNumber(Integer.parseInt(arrayList.get(i)[2]));
            baggages[i].setOwner(Integer.parseInt(arrayList.get(i)[3]));
        }

        return baggages;
    }

    @Override
    public Object Delete(Object obj) {
        l = (Baggage) obj;

        SQLrequest = "Delete from "+TABLE_NAME;
        if(l.getId()!=0)SQLrequest+=" where id = '"+l.getId()+"'";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            return "Wrong request";
        }
        return "ok";
    }

    @Override
    public Object Update(Object obj) {
        l = (Baggage) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t."+colName1+" = '"+l.getSize()+"', t."+colName2+" = '"+
                l.getSectionNumber()+"', t."+colName3+" = '"+l.getOwner()+
                "' where t."+colName0+" = '"+l.getId()+"';";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }
}
