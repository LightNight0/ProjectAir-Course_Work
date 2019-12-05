package server;

public class Main {

    public static void main(String[] args){

        Thread admin = new Thread(new Admin());
        admin.start();
        Thread clientListener = new Thread(new ClientListener());
        clientListener.start();
        while (admin.isAlive()){}
        ClientListener.close();
        System.exit(1);
    }
}
