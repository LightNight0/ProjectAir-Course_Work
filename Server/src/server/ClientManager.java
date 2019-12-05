package server;

import dataBase.DBManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

public class ClientManager implements Runnable {

    private Socket socket;
    private ObjectInputStream sois;
    private ObjectOutputStream soos;
    private Date d;

    private DBManager dbManager;

    ClientManager(Socket socket){
        this.socket = socket;
        d = new Date();
    }

    @Override
    public void run() {
        dbManager = new DBManager();

        try {
            sois = new ObjectInputStream(socket.getInputStream());
            soos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("!DEBUG!1");
            Object message = sois.readObject();
            System.out.println("!DEBUG!2");
            while (!message.equals("quite")) {
                System.out.println("!DEBUG!3");
                message = dbManager.getRequestFromClient(message,socket);
                soos.writeObject(message);
                message = sois.readObject();
            }

            socket.close();
            sois.close();
            soos.close();
            //System.out.println(d.toString()+" Клиент "+socket.getInetAddress()+" отключился");
        } catch (IOException e) {
            if(e instanceof SocketException)System.out.println("Клиент "+socket.getInetAddress()+" экстренно отключился!");
            else e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
