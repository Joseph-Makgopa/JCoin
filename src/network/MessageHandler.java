package network;

import network.message.InvalidTransactionMessage;
import network.message.MineTransactionMessage;

public class MessageHandler {
    public void InvalidTransaction(ConnectionHandler connectionHandler, InvalidTransactionMessage message){
        System.out.println(message.getMessage());
    }

    public void MineTransaction(ConnectionHandler connectionHandler, MineTransactionMessage message){
        System.out.println(message.getMessage());
    }
}
