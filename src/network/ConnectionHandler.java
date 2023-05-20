package network;

import blockchain.Block;
import blockchain.Blockchain;
import network.message.InvalidTransactionMessage;
import network.message.MineTransactionMessage;
import network.message.TextMessage;
import transaction.Transaction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionHandler extends Thread{
    private Socket connection;
    private Boolean active;
    public ConnectionHandler(Socket connection){
        this.connection = connection;
        this.active = true;
    }
    public void toggleActive(){
        active = !active;
    }

    public void setActive(Boolean active){
        this.active = active;
    }

    public void send(Object message){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
            objectOutputStream.writeObject(message);
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    public Socket getConnection() {
        return connection;
    }

    @Override
    public void run(){
        while(!connection.isClosed()){
            try {
                if(!active){
                   connection.close();
                   break;
                }

                ObjectInputStream objectInputStream = new ObjectInputStream(connection.getInputStream());
                Object message = objectInputStream.readObject();

                if(message instanceof MineTransactionMessage){
                    NetworkService.getInstance().getMessageHandler().MineTransaction(this, (MineTransactionMessage)message);
                }else if(message instanceof InvalidTransactionMessage){
                    NetworkService.getInstance().getMessageHandler().InvalidTransaction(this, (InvalidTransactionMessage)message);
                }
            }catch(IOException error){
                active = false;
                try {
                    connection.close();
                }catch(IOException err){

                }
                break;

            }catch(ClassNotFoundException error){
                error.printStackTrace();
            }
        }
    }
}
