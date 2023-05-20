package network;

import network.message.InvalidTransactionMessage;
import network.message.MineTransactionMessage;

public class MessageHandler {
    public void InvalidTransaction(ConnectionHandler connectionHandler, InvalidTransactionMessage message){
        System.out.println("Invalid Transaction Handler");
    }

    public void MineTransaction(ConnectionHandler connectionHandler, MineTransactionMessage message){
        System.out.println("Mine Transaction Handler");
    }
}
