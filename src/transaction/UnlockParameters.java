package transaction;

import java.security.PublicKey;
import java.security.Signature;

public class UnlockParameters {
    private PublicKey publicKey;
    private byte[] signature;
    public UnlockParameters(PublicKey publicKey, byte[] signature){
        this.publicKey = publicKey;
        this.signature = signature;
    }
    public PublicKey getPublicKey() {
        return publicKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        String signatureString = "";

        for(int count = 0; count < signature.length; count++){
            signatureString += signature[count] + " ";
        }

        return "UP{" +
                "publicKey='" + publicKey + '\'' +
                ", signature=" + signatureString +
                '}';
    }
}
