package essences;

import java.io.Serializable;

public class Aircraft extends Essence implements Serializable {

    private String name;
    private String type;
    private int seatCount;
    private int businessSeatCount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public int getBusinessSeatCount() {
        return businessSeatCount;
    }

    public void setBusinessSeatCount(int businessSeatCount) {
        this.businessSeatCount = businessSeatCount;
    }

    @Override
    public String toString(){
        return name;
    }
}
