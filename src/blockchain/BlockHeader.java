package blockchain;

import java.io.Serializable;
import java.util.Date;

public class BlockHeader implements Serializable {
    private Float version;
    private String previousHash;
    private String merkleRoot;
    private Integer nonce;
    private Long timeStamp;
    private Integer difficulty;
    public BlockHeader(Float version, String previousHash) {
        this.version = version;
        this.previousHash = previousHash;
        this.nonce = 0;
        this.difficulty = 0;
        setTimeStamp();
    }

    public BlockHeader(String previousHash) {
        this.version = 1.0F;
        this.previousHash = previousHash;
        this.nonce = 0;
        this.difficulty = 0;
        setTimeStamp();
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setTimeStamp(){
        this.timeStamp = System.currentTimeMillis();
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public void setNonce(Integer nonce) {
        this.nonce = nonce;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public Float getVersion()
    {
        return this.version;
    }
    public Long getTimeStamp() {
        return timeStamp;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public Integer getNonce() {
        return nonce;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    @Override
    public String toString() {
        return "{" +
                "version=" + version +
                ", previousHash='" + previousHash + '\'' +
                ", merkleRoot='" + merkleRoot + '\'' +
                ", nonce=" + nonce +
                ", timeStamp=" + timeStamp +
                ", difficulty=" + difficulty +
                '}';
    }
}
