package transaction;

import java.io.Serializable;
import java.security.Signature;

public class TransactionOutput implements Serializable {
    private Double jCoinValue;
    private String address;
    public TransactionOutput(Double jCoinValue, String address){
        this.jCoinValue = jCoinValue;
        this.address = address;
    }
    public TransactionOutput(Double jCoinValue){
        this.jCoinValue = jCoinValue;
        address = "";
    }
    public Double getJCoinValue(){
        return jCoinValue;
    }
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "TXO{" +
                "jCoinValue=" + jCoinValue +
                ", address=" + address +
                '}';
    }
}
