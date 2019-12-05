package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ClientListener implements Runnable {

    private Socket socket;
    private static ServerSocket serverSocket;
    private Date d;

    @Override
    public void run() {
        d = new Date();
        try {
            if(serverSocket==null)serverSocket = new ServerSocket(2525);
            System.out.println("Server is ready to connection...");
            while (true) {
                socket = serverSocket.accept();
                System.out.println(d.toString()+"!DEBUG! Подключился клиент с IP: "+socket.getInetAddress());
                new Thread(new ClientManager(socket)).start();
            }
        }catch (IOException ex){
            System.out.println("Connection lost.");
        }
    }

    public static void close(){
        if(serverSocket!=null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
