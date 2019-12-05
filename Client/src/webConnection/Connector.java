package webConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector {

    private static volatile Connector instance;

    private Socket clientSocket;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;

    public static Connector getInstance() {
        if (instance == null) {
            synchronized (Connector.class) {
                if (instance == null) {
                    instance = new Connector();
                    instance.connect();
                }
            }
        }
        return instance;
    }


    public void connect(){
        try {
            System.out.println("server connecting....");
            clientSocket = new Socket(InetAddress.getLocalHost(), 2525);
            System.out.println("connection established....");
            coos = new ObjectOutputStream(clientSocket.getOutputStream());
            cois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            coos.writeObject("quite");
            coos.close();
            cois.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Object o){
        try {
            coos.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Object get(){
        try {
            return cois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public Socket getClientSocket(){
        return clientSocket;
    }
    public ObjectOutputStream getCoos(){
        return coos;
    }
    public ObjectInputStream getCois(){
        return cois;
    }
}
