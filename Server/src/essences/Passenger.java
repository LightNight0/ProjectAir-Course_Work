package essences;

import java.io.Serializable;

public class Passenger extends Essence implements Serializable {

    private int passengerClass;
    private int seatNumber;
    private int flight;
    private String FIO;
    private int personDataid;

    public int getPassengerClass() {
        return passengerClass;
    }

    public void setPassengerClass(int passengerClass) {
        this.passengerClass = passengerClass;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getFlight() {
        return flight;
    }

    public void setFlight(int flight) {
        this.flight = flight;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getPersonDataid() {
        return personDataid;
    }

    public void setPersonDataid(int personDataid) {
        this.personDataid = personDataid;
    }
}
