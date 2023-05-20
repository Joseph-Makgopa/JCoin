package blockchain;

import algorithm.HashAlgorithm;
import transaction.Transaction;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block implements Serializable {
    private BlockHeader header;
    private BlockBody body;

    public Block(BlockHeader header){
        this.header = header;
        this.body = new BlockBody();
    }

    public Block(BlockHeader header, BlockBody body){
        this.header = header;
        this.body = body;
    }
    public BlockHeader getHeader(){
        return header;
    }
    public BlockBody getBody(){
        return body;
    }

    public String getHash() throws NoSuchAlgorithmException {
        String data = header.getVersion() +  header.getPreviousHash() + header.getMerkleRoot() + header.getNonce().toString() + header.getDifficulty().toString() + header.getTimeStamp().toString();

        for(Transaction transaction: body.getTransactions()){
            data += transaction.getHash();
        }

        return HashAlgorithm.getInstance().getHash(data);
    }

    public void setMerkleRoot(){
        this.header.setMerkleRoot(this.body.generateMerkleRoot());
    }

    @Override
    public String toString() {
        return "[" +
                "header=" + header +
                ", body=" + body +
                ']';
    }
}
