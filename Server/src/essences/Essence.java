package essences;

import java.io.Serializable;

public abstract class Essence implements Serializable {
    protected String dataBaseCommand;
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataBaseCommand() {
        return dataBaseCommand;
    }
    public void setDataBaseCommand(String dataBaseCommand) { this.dataBaseCommand = dataBaseCommand; }
}
