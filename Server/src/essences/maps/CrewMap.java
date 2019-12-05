package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.Crew;
import essences.IRequests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CrewMap implements IRequests {

    private Crew[] crews;
    private Crew c;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "crew";

    public CrewMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object Add(Object obj) {
        c = (Crew) obj;

        SQLrequest = "insert into "+TABLE_NAME+" (`captain`, `quantity`) values ('"+
                c.getResponsible()+"','"+c.getCount()+"');";
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
        c = (Crew) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();

        SQLrequest = "Select * from "+TABLE_NAME;
        if(c.getId()!=0)SQLrequest+=" where id = '"+c.getId()+"'";
        else if(c.getResponsible()!=null)SQLrequest+=" where captain = '"+c.getResponsible()+"'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        crews = new Crew[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            crews[i] = new Crew();
            crews[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            crews[i].setResponsible(arrayList.get(i)[1]);
            crews[i].setCount(Integer.parseInt(arrayList.get(i)[2]));
        }

        return crews;
    }

    @Override
    public Object Delete(Object obj) {
        c = (Crew) obj;

        SQLrequest = "Delete from "+TABLE_NAME;
        if(c.getId()!=0)SQLrequest+=" where id = '"+c.getId()+"'";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            return "Wrong request";
        }
        return "ok";
    }

    @Override
    public Object Update(Object obj) {
        c = (Crew) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t.`captain` = '"+c.getResponsible()+
                "', t.`quantity` = '"+c.getCount()+"' where t.`id` = '"+c.getId()+"';";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }

}
