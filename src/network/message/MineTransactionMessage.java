package network.message;

import transaction.Transaction;

public class MineTransactionMessage extends Message{
    private Transaction transaction;
    public MineTransactionMessage(Transaction transaction){
        this.transaction = transaction;
    }
    public MineTransactionMessage(Transaction transaction, String message){
        super(message);
        this.transaction = transaction;
    }
    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
