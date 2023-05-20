package blockchain;

import algorithm.HashAlgorithm;
import transaction.Transaction;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockBody implements Serializable {
    private ArrayList<Transaction> transactions;
    private ArrayList<String> generateMerkleRootHelper(ArrayList<String> hashes) throws NoSuchAlgorithmException{
        if(hashes.size() == 1){
            return hashes;
        }else{
            ArrayList<String> result = new ArrayList<>();

            for(int count = 0; count < hashes.size(); count+= 2)
            {
                if(count + 1 < hashes.size())
                {
                    result.add(HashAlgorithm.getInstance().getHash(HashAlgorithm.getInstance().getHash(hashes.get(count) + hashes.get(count + 1))));
                }else{
                    result.add(HashAlgorithm.getInstance().getHash(HashAlgorithm.getInstance().getHash(hashes.get(count) + hashes.get(count))));
                }
            }

            return generateMerkleRootHelper(result);
        }
    }

    public BlockBody()
    {
        transactions = new ArrayList<>();
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public Integer transactionCount(){
        return transactions.size();
    }
    public String generateMerkleRoot(){
        try{
            ArrayList<String> hashes = new ArrayList<>();

            for(Transaction transaction: transactions){
                hashes.add(HashAlgorithm.getInstance().getHash(transaction.getHash()));
            }

            return generateMerkleRootHelper(hashes).get(0);
        }catch (NoSuchAlgorithmException error)
        {
            error.printStackTrace();
        }

        return "";
    }

    @Override
    public String toString() {
        String data = "";

        for(int count = 0; count < transactions.size(); count++){
            data += transactions.get(count).toString();

            if(count != transactions.size() - 1){
                data += ", ";
            }
        }

        return "{" +
                "transactions= [" + data + "]" +
                '}';
    }
}
