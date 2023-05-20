package transaction;

import java.io.Serializable;
import java.security.Signature;

public class TransactionInput implements Serializable {
    private String transactionHash;
    private Integer outputIndex;
    private UnlockParameters unlockParameters;
    public TransactionInput(String transactionHash, Integer outputIndex, UnlockParameters unlockParameters){
        this.transactionHash = transactionHash;
        this.outputIndex = outputIndex;
        this.unlockParameters = unlockParameters;
    }
    public TransactionInput(String transactionHash, Integer outputIndex){
        this.transactionHash = transactionHash;
        this.outputIndex = outputIndex;
        unlockParameters = null;
    }
    public String getTransactionHash(){
        return transactionHash;
    }
    public Integer getOutputIndex(){
        return outputIndex;
    }
    public UnlockParameters getUnlockParameters(){
        return unlockParameters;
    }

    @Override
    public String toString() {
        return "TXI{" +
                "transactionHash='" + transactionHash + '\'' +
                ", outputIndex=" + outputIndex +
                ", unlockParameters=" + unlockParameters +
                '}';
    }
}
