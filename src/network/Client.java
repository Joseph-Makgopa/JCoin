package network;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import network.message.TextMessage;
public class Client {
    public Socket getConnection(String address, Integer portNo) throws ConnectException, UnknownHostException, IOException {
        Socket socket = new Socket(address, portNo);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        try {
            TextMessage textMessage = (TextMessage) objectInputStream.readObject();

            if(textMessage.getData() == "Already Connected"){
                return null;
            }
        }catch(ClassNotFoundException error){
            error.printStackTrace();
        }

        return socket;
    }
}
