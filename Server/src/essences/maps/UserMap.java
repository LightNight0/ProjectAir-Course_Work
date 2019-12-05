package essences.maps;

import dataBase.DBManager;
import dataBase.DBConnector;
import essences.IRequests;
import essences.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserMap implements IRequests {

    private User u;
    private User[] users;

    private Statement stmt;
    private String SQLrequest;

    private final String TABLE_NAME = "`user`";
    private final String colName0 = "`id`";
    private final String colName1 = "`name`";
    private final String colName2 = "`login`";
    private final String colName3 = "`password`";
    private final String colName4 = "`role`";

    public UserMap(){
        try {
            stmt = DBConnector.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Object Add(Object obj) {
        u = (User) obj;

        SQLrequest = "insert into "+TABLE_NAME+" ( "+colName1+", "+colName2+", "+colName3+", "+colName4+") values ('"+
                u.getName()+"','"+u.getLogin()+"','"+u.getPassword()+"','"+u.getRole()+"');";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }

    @Override
    public Object Select(Object obj){
        u = (User) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();

        SQLrequest = "Select * from "+TABLE_NAME;
        if(u.getId()!=0)SQLrequest+=" where "+colName0+" = '"+u.getId()+"'";
        else if(u.getLogin()!=null)SQLrequest+=" where "+colName2+" = '"+u.getLogin()+"'";
        else if(u.getName()!=null)SQLrequest+=" where "+colName1+" = '"+u.getName()+"'";

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        users = new User[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            users[i] = new User();
            users[i].setId(Integer.parseInt(arrayList.get(i)[0]));
            users[i].setName(arrayList.get(i)[1]);
            users[i].setLogin(arrayList.get(i)[2]);
            users[i].setPassword(arrayList.get(i)[3]);
            users[i].setRole(arrayList.get(i)[4]);
        }

        return users;
    }


    public Object LogIn(Object obj) {
        u = (User) obj;
        ArrayList<String[]> arrayList = new ArrayList<>();;

        SQLrequest = "Select * from "+TABLE_NAME;

        if(u.getLogin()==null || u.getPassword()==null)return "Wrong request";
        else {
            SQLrequest += " where "+colName2+" = '"+u.getLogin()+"' and "+colName3+" = '"+u.getPassword()+"';";
        }

        try {
            DBManager.ResultSetIntoArray(SQLrequest,stmt,arrayList);
        }catch (SQLException ex){return "Wrong request";}

        if(arrayList.size()==0)return "empty";

        u = new User();
        for(int i = 0; i < arrayList.size(); i++){
            u.setId(Integer.parseInt(arrayList.get(i)[0]));
            u.setName(arrayList.get(i)[1]);
            u.setLogin("");
            u.setPassword("");
            u.setRole(arrayList.get(i)[4]);
        }

        return u;
    }

    @Override
    public Object Delete(Object obj) {
        u = (User) obj;

        SQLrequest = "Delete from "+TABLE_NAME;
        if(u.getId()!=0)SQLrequest+=" where id = '"+u.getId()+"'";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            return "Wrong request";
        }
        return "ok";
    }

    @Override
    public Object Update(Object obj) {
        u = (User) obj;

        SQLrequest = "update "+TABLE_NAME+" t set t."+colName1+" = '"+u.getName()+"', t."+colName2+" = '"+
                u.getLogin()+"', t."+colName3+" = '"+u.getPassword()+"', t."+colName4+" = '"+u.getRole()+
                "' where t."+colName0+" = '"+u.getId()+"';";
        try {
            stmt.executeUpdate(SQLrequest);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Wrong request";
        }

        return "ok";
    }
}
