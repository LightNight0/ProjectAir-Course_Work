package server;

import dataBase.DBManager;
import dataBase.DBConnector;

import java.util.Scanner;

public class Admin implements Runnable {

    private boolean isRun;

    public Admin(){isRun=true;}

    @Override
    public void run() {
        Scanner scn = new Scanner(System.in);
        while(isRun){
            System.out.println("1 - sql request\n" +
                    "2 - stop server");
            String s = scn.nextLine();
            switch (s){
                case "1":
                    System.out.println("input request:");
                    String request = scn.nextLine();
                    DBManager.getRequestFromAdmin(request);
                    break;
                case "2":
                    DBConnector.getInstance().close();
                    isRun = false;
                    break;
                default:System.out.println("Wrong request");
            }
        }
    }
}
