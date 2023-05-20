package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import network.message.TextMessage;

public class Server extends Thread{
    private ArrayList<ConnectionHandler> connections;
    private Boolean exit;
    private ServerSocket serverSocket;
    public Server(ArrayList<ConnectionHandler> connections, Integer portNo) throws IOException {
        this.connections = connections;
        serverSocket = new ServerSocket(portNo);
        exit = false;
    }

    public void toggleExit(){
        exit = !exit;
    }

    public void setExit(Boolean exit){
        this.exit = exit;
    }

    public ServerSocket getServerSocket(){
        return serverSocket;
    }

    public void close() throws IOException{
        exit = true;
        serverSocket.close();
    }

    @Override
    public void run() {
        while(!exit) {
            try{
                Socket socket = serverSocket.accept();

                Integer found = -1;

                for(int count = 0; count < connections.size() && found == -1; count++){
                    if(socket.getInetAddress().getHostAddress() == connections.get(count).getConnection().getInetAddress().getHostAddress() && socket.getPort() == connections.get(count).getConnection().getPort()){
                        found = count;
                    }
                }

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                if(found != -1){
                    objectOutputStream.writeObject(new TextMessage("Already Connected"));
                }else{
                    objectOutputStream.writeObject(new TextMessage("Success"));
                    ConnectionHandler handler = new ConnectionHandler(socket);
                    handler.start();
                    connections.add(handler);

                }
            }catch(Exception error) {

            }
        }
    }

    public ArrayList<ConnectionHandler> getConnections(){
        return connections;
    }
}
