package essences;

import java.io.Serializable;

public class TicketClass extends Essence implements Serializable {

    private String name;
    private int coast;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

}
