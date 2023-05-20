package blockchain;

import blockchain.Block;
import transaction.Transaction;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Blockchain implements Serializable {
    private ArrayList<Block> chain;
    public Blockchain(){
        chain = new ArrayList<>();
        Block genesis = new Block(new BlockHeader(""));

        chain.add(genesis);
    }

    public ArrayList<Block> getChain() {
        return chain;
    }
    public Integer getHeight(){
        return chain.size();
    }
    public Block getBlock(Integer sub){
        return chain.get(sub);
    }
    public void setChain(ArrayList<Block> chain){
        this.chain = chain;
    }

    public void addBlock(Block block){
        chain.add(block);
    }
    public Block getTop(){
        return chain.get(chain.size() - 1);
    }
    public Transaction getTransaction(String hash){
        for(Block block: chain){
            for(Transaction transaction: block.getBody().getTransactions()){
                try{
                    if(transaction.getHash().equals(hash)){
                        return transaction;
                    }
                }catch(NoSuchAlgorithmException error){

                }
            }
        }

        return null;
    }

    @Override
    public String toString() {
        String data = "";

        for(int count = 0; count < chain.size(); count++){
            data += "\n\t" + chain.get(count).toString();

            if(count != chain.size() - 1){
                data += ", \n";
            }else
                data += "\n";
        }

        return "[" +
                 data +
                ']';
    }
}
