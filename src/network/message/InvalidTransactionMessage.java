package network.message;

import transaction.Transaction;

import java.io.Serializable;

public class InvalidTransactionMessage extends Message implements Serializable {
    private Transaction transaction;
    public InvalidTransactionMessage(Transaction transaction, String reason){
        super(reason);
        this.transaction = transaction;
    }
    public InvalidTransactionMessage(Transaction transaction){
        this.transaction = transaction;
    }
    public Transaction getTransaction() {
        return transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
