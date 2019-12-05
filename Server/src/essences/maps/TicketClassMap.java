package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.TicketClass;
import essences.IRequests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TicketClassMap implements IRequests {

    private TicketClass[] ticketClasses;
    private TicketClass p;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "`class`";
    private final String colName0 = "`id`";
    private final String colName1 = "`cost`";
    private final String colName2 = "`name`";

    public TicketClassMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object Add(Object obj) {
        p = (TicketClass) obj;

        SQLrequest = "insert into "+TABLE_NAME+" ( "+colName1+", "+colName2+") values ('"+
                p.getCoast()+"','"+p.getCoast()+"');";
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
        p = (TicketClass) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();;

        SQLrequest = "Select * from "+TABLE_NAME;
        if(p.getId()!=0)SQLrequest+=" where "+colName0+" = '"+p.getId()+"'";
        else if(p.getName()!=null)SQLrequest+=" where "+colName1+" = '"+p.getCoast()+"'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        ticketClasses = new TicketClass[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            ticketClasses[i] = new TicketClass();
            ticketClasses[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            ticketClasses[i].setCoast(Integer.parseInt(arrayList.get(i)[1]));
            ticketClasses[i].setName(arrayList.get(i)[2]);
        }

        return ticketClasses;
    }

    @Override
    public Object Delete(Object obj) {
        p = (TicketClass) obj;

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
        p = (TicketClass) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t."+colName1+" = '"+p.getCoast()+"', t."+colName2+" = '"+
                p.getName()+
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
