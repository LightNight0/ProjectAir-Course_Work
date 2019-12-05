package essences;

import java.io.Serializable;

public class Runway extends Essence implements Serializable {

    private String name;
    private String status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString(){
        return name;
    }
}
