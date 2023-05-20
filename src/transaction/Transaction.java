package transaction;

import algorithm.HashAlgorithm;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Transaction implements Serializable {
    private Float version;
    private ArrayList<TransactionInput> tInputs = new ArrayList<TransactionInput>();;
    private ArrayList<TransactionOutput> tOutputs = new ArrayList<TransactionOutput>();
    private Long lockTime;
    public Transaction(Float version, Long lockTime){
        this.version = version;
        this.lockTime = lockTime;
    }
    public Transaction(Long lockTime){
        this.version = 1.0F;
        this.lockTime = lockTime;
    }
    public Transaction(){
        this.version = 1.0F;
        this.lockTime = 0L;
    }
    public Float getVersion() {
        return version;
    }
    public Long getLockTime() {
        return lockTime;
    }
    public ArrayList<TransactionInput> getInputs() {
        return tInputs;
    }
    public ArrayList<TransactionOutput> getOutputs() {
        return tOutputs;
    }

    public void setLockTime(Long lockTime) {
        this.lockTime = lockTime;
    }
    public void addInput(TransactionInput input){
        tInputs.add(input);
    }
    public void addOutput(TransactionOutput output){
        tOutputs.add(output);
    }
    public String getHash() throws NoSuchAlgorithmException {
        String data = version.toString() + lockTime.toString();

        for(int count = 0; count < tInputs.size(); count++){
            TransactionInput input = tInputs.get(count);
            data += input.getTransactionHash() + input.getOutputIndex() + input.getUnlockParameters().toString();
        }

        for(int count = 0; count < tOutputs.size(); count++){
            TransactionOutput input = tOutputs.get(count);
            data += input.getJCoinValue().toString() + input.getAddress().toString();
        }

        return HashAlgorithm.getInstance().getHash(data);
    }

    @Override
    public String toString() {
        String inputData = "";
        String outputData = "";

        for(int count = 0; count < tInputs.size(); count++){
            inputData += tInputs.get(count).toString();

            if(count != tInputs.size() - 1){
                inputData += ", ";
            }
        }

        for(int count = 0; count < tOutputs.size(); count++){
            inputData += tOutputs.get(count).toString();

            if(count != tOutputs.size() - 1){
                inputData += ", ";
            }
        }

        return "{" +
                "version=" + version +
                ", tInputs= [" + inputData + "]" +
                ", tOutputs= [" + outputData + "]" +
                ", lockTime=" + lockTime +
                '}';
    }
}
