package algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashAlgorithm {
    private static HashAlgorithm instance = null;
    private static String algorithm;
    private HashAlgorithm(){
        this.algorithm = "SHA-256";
    }
    public static HashAlgorithm getInstance(){
        if(instance == null)
        {
            instance = new HashAlgorithm();
        }

        return instance;
    }
    public void setAlgorithm(String algorithm){
        this.algorithm = algorithm;
    }
    public String getHash(String data) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(this.algorithm);
        final byte[] bytes = messageDigest.digest(data.getBytes());
        final StringBuilder stringBuilder = new StringBuilder();

        for(final byte value: bytes) {
            String strValue = Integer.toHexString(0xff & value);

            if(strValue.length() == 1)
            {
                stringBuilder.append('0');
            }

            stringBuilder.append(strValue);
        }

        return stringBuilder.toString();
    }
    public String getHash(byte[] dataBytes) throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance(this.algorithm);
        final byte[] bytes = messageDigest.digest(dataBytes);
        final StringBuilder stringBuilder = new StringBuilder();

        for(final byte value: bytes) {
            String strValue = Integer.toHexString(0xff & value);

            if(strValue.length() == 1)
            {
                stringBuilder.append('0');
            }

            stringBuilder.append(strValue);
        }

        return stringBuilder.toString();
    }
}
