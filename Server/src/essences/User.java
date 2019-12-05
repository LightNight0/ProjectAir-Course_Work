package essences;

import java.io.Serializable;

public class User extends Essence implements Serializable {

    private String role;
    private String name;
    private String login;
    private String password;
    private int personDataid;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPersonDataid() {
        return personDataid;
    }

    public void setPersonDataid(int personDataid) {
        this.personDataid = personDataid;
    }

}
