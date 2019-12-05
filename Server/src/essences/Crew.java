package essences;

import java.io.Serializable;

public class Crew extends Essence implements Serializable{

    private String responsible;
    private int count;


    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    @Override
    public String toString(){
        return responsible;
    }
}
