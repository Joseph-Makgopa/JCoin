package network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
public class NetworkService {
    private ArrayList<ConnectionHandler> connections;
    private Server server;
    private Client client;
    private Integer serverPort = 49999;
    private static NetworkService instance = null;
    private MessageHandler messageHandler;
    private NetworkService(){
        messageHandler = new MessageHandler();
    }
    public void start(){
        connections = new ArrayList<ConnectionHandler>();

        try {
            server = new Server(connections, serverPort);
            server.start();
        }catch(IOException error){
            error.printStackTrace();
        }

        try(
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("addresses.txt")));
        ){
            client = new Client();
            String line;

            while( (line = bufferedReader.readLine()) != null ){
                String[] address = line.split(",");
                try{
                    Socket socket = client.getConnection(address[0], Integer.parseInt(address[1]));

                    if(socket != null){
                        connections.add(new ConnectionHandler(socket));
                    }
                }catch(ConnectException error){
                    System.out.println("Failed to connect to '" + address[0] + "':" + address[1]);
                }
            }

            for(int count = 0; count < connections.size(); count++){
                connections.get(count).start();
            }

        }catch(Exception error){
            error.printStackTrace();
        }
    }
    public static NetworkService getInstance(){
        if(instance == null){
            instance = new NetworkService();
        }
        return instance;
    }
    public void setServerPort(Integer serverPort){
        this.serverPort = serverPort;
    }
    public MessageHandler getMessageHandler(){
        return messageHandler;
    }
    public void broadcast(Object message){
        for(int count = 0; count < connections.size(); count++){
            connections.get(count).send(message);
        }
    }

    public void stop(){

        for(int count = 0; count < connections.size(); count++){
            connections.get(count).setActive(false);
            try {
                connections.get(count).getConnection().close();
            }catch(SocketException error){

            }catch(IOException error){

            }

            try {
                connections.get(count).join();
            }catch(InterruptedException error){

            }
        }

        try{
            server.close();
        }catch(Exception error){

        }

        try{
            server.join();
        }catch(InterruptedException error){

        }
    }


}
