package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.IRequests;
import essences.Runway;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RunwayMap implements IRequests {
    private Runway[] runways;
    private Runway s;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "`runway`";
    private final String colName0 = "`id`";
    private final String colName1 = "`name`";
    private final String colName2 = "`status`";

    public RunwayMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Object Add(Object obj) {
        s = (Runway) obj;

        SQLrequest = "insert into "+TABLE_NAME+" ( "+colName1+", "+colName2+") values ('"+
                s.getName()+"','"+s.getStatus()+"');";
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
        s = (Runway) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();;

        SQLrequest = "Select * from "+TABLE_NAME;
        if(s.getId()!=0)SQLrequest+=" where "+colName0+" = '"+s.getId()+"'";
        else if(s.getStatus()!=null)SQLrequest+=" where "+colName2+" = '"+s.getStatus()+"'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        runways = new Runway[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++) {
            runways[i] = new Runway();
            runways[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            runways[i].setName(arrayList.get(i)[1]);
            runways[i].setStatus(arrayList.get(i)[2]);
        }

        return runways;
    }

    @Override
    public Object Delete(Object obj) {
        s = (Runway) obj;

        SQLrequest = "Delete from "+TABLE_NAME;
        if(s.getId()!=0)SQLrequest+=" where id = '"+s.getId()+"'";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            return "Wrong request";
        }
        return "ok";
    }

    @Override
    public Object Update(Object obj) {
        s = (Runway) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t."+colName1+" = '"+s.getName()+"', t."+colName2+" = '"+
                s.getStatus()+
                "' where t."+colName0+" = '"+s.getId()+"';";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }
}

