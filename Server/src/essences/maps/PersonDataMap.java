package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.PersonData;
import essences.IRequests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PersonDataMap implements IRequests {
    private PersonData[] personsData;
    private PersonData p;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "`person_data`";
    private final String colName0 = "`id`";
    private final String colName1 = "`age`";
    private final String colName2 = "`mail`";
    private final String colName3 = "`adress`";
    private final String colName4 = "`sex`";

    public PersonDataMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object Add(Object obj) {
        p = (PersonData) obj;

        SQLrequest = "insert into "+TABLE_NAME+" ( "+colName1+", "+colName2+", "+colName3+", "+colName4+") values ('"+
                p.getMail()+"','"+p.getSex()+"','"+p.getAdress()+"','"+p.getAge()+"');";
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
        p = (PersonData) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();;

        SQLrequest = "Select * from "+TABLE_NAME;
        if(p.getId()!=0)SQLrequest+=" where "+colName0+" = '"+p.getId()+"'";
        else if(p.getMail()!=null)SQLrequest+=" where "+colName3+" = '"+p.getMail()+"'";
        else if(p.getSex()!=null)SQLrequest+=" where "+colName1+" = '"+p.getSex()+"'";
        else if(p.getAdress()!=null)SQLrequest+=" where "+colName2+" = '"+p.getAdress()+"'";
        else if(p.getAge()!=0)SQLrequest+=" where "+colName4+" like '%"+p.getAge()+"%'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        personsData = new PersonData[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            personsData[i] = new PersonData();
            personsData[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            personsData[i].setMail(arrayList.get(i)[1]);
            personsData[i].setSex(arrayList.get(i)[2]);
            personsData[i].setAdress(arrayList.get(i)[3]);
            personsData[i].setAge(Integer.parseInt(arrayList.get(i)[4]));
        }

        return personsData;
    }

    @Override
    public Object Delete(Object obj) {
        p = (PersonData) obj;

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
        p = (PersonData) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t."+colName1+" = '"+p.getMail()+"', t."+colName2+" = '"+
                p.getSex()+"', t."+colName3+" = '"+p.getAdress()+"', t."+colName4+" = '"+p.getAge()+
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
