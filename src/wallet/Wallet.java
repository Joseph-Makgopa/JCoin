package wallet;

import algorithm.HashAlgorithm;
import algorithm.Pair;
import algorithm.Triple;
import blockchain.Block;
import blockchain.Blockchain;
import network.NetworkService;
import transaction.Transaction;
import transaction.TransactionInput;
import transaction.TransactionOutput;
import transaction.UnlockParameters;

import java.security.*;
import java.util.ArrayList;

public class Wallet {
    private KeyPair keys;
    public Wallet(){
        keys = null;
    }
    public void setKeys(KeyPair keys){
        this.keys = keys;
    }
    public void generateKeys(){
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048, secureRandom);
            keys =  keyPairGenerator.generateKeyPair();
        }catch(NoSuchAlgorithmException error){

        }
    }
    public KeyPair getKeys(){
        return keys;
    }
    public String getAddress(){
        String result = "";

        try{
            result = HashAlgorithm.getInstance().getHash(keys.getPublic().getEncoded());
        }catch(NoSuchAlgorithmException error){
            System.out.println("Error: Failed to get address");
        }

        return result;
    }
    public Double getBalance(Blockchain blockchain) {
        ArrayList<TransactionInput> transactionInputs = new ArrayList<>();
        ArrayList<TransactionOutput> transactionOutputs = new ArrayList<>();
        Double sumOutputs = 0.0;
        Double sumInputs = 0.0;
        String address = getAddress();

        for(int count = 0; count < blockchain.getHeight(); count++){
            Block block = blockchain.getBlock(count);

            for(Transaction transaction: block.getBody().getTransactions()){
                for(TransactionOutput output: transaction.getOutputs()){
                    if(output.getAddress().equals(address)){
                        sumOutputs += output.getJCoinValue();
                    }
                }

                for(TransactionInput input: transaction.getInputs()){
                    Transaction linkTransaction = blockchain.getTransaction(input.getTransactionHash());

                    try{
                        TransactionOutput output = linkTransaction.getOutputs().get(input.getOutputIndex());

                        if(output.getAddress().equals(address)){
                            sumInputs += output.getJCoinValue();
                        }
                    } catch(ArrayIndexOutOfBoundsException error){

                    }
                }
            }
        }

        return sumOutputs - sumInputs;
    }
    public ArrayList<Pair<Transaction, Integer>> getUnspentTransactionOutput(Blockchain blockchain){
        ArrayList<Pair<Transaction, Integer>> result = new ArrayList<>();
        String address = getAddress();
        ArrayList<TransactionInput> inputs = new ArrayList<>();

        for(Block block: blockchain.getChain()){
            for(Transaction transaction: block.getBody().getTransactions()){
                for(int count = 0; count < transaction.getOutputs().size(); count++ ){
                    TransactionOutput output = transaction.getOutputs().get(count);

                    if(output.getAddress().equals(address)){
                        result.add(new Pair(transaction, count));
                    }
                }

                inputs.addAll(transaction.getInputs());
            }
        }

        ArrayList<Pair<Transaction, Integer>> temp = result;
        result = new ArrayList<>();

        for(int count = 0; count < temp.size(); count++){
            Boolean found = false;
            String hash = "";

            try{
                hash = temp.get(count).getFirst().getHash();
            }catch(NoSuchAlgorithmException error){
                continue;
            }

            for(int step = 0; step < inputs.size() && !found; step++){
                if(hash.equals(inputs.get(step).getTransactionHash()) && inputs.get(step).getOutputIndex().equals(temp.get(count).getSecond())){
                    result.add(temp.get(count));
                }
            }
        }

        return result;
    }
    public byte[] createSignature(Transaction transaction){
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            String address = getAddress();
            signature.initSign(keys.getPrivate());
            signature.update((address + transaction.getHash()).getBytes());

            return signature.sign();
        }catch(NoSuchAlgorithmException error){

        }catch(InvalidKeyException error){

        }catch(SignatureException error){

        }

        return null;
    }
    public Transaction createTransaction(Blockchain blockchain, Double value,String recepient){
        ArrayList<Pair<Transaction, Integer>> utxo = getUnspentTransactionOutput(blockchain);
        Double inputValue = 0.0;

        for(int count = 0; count < utxo.size(); count++){
            inputValue += utxo.get(count).getFirst().getOutputs().get(utxo.get(count).getSecond()).getJCoinValue();
        }

        if(inputValue < value){
            return null;
        }

        Transaction result = new Transaction();

        for(Pair<Transaction, Integer> txo: utxo){
            try {
                result.addInput(new TransactionInput(txo.getFirst().getHash(), txo.getSecond(), new UnlockParameters(keys.getPublic(),createSignature(txo.getFirst()))));
            }catch(NoSuchAlgorithmException error){

            }
        }

        result.addOutput(new TransactionOutput(value, recepient));
        result.addOutput(new TransactionOutput(inputValue - value));

        return result;
    }
    public Transaction createTransaction(Blockchain blockchain, Double value, Double fee, String recepient){
        ArrayList<Pair<Transaction, Integer>> utxo = getUnspentTransactionOutput(blockchain);
        Double inputValue = 0.0;

        for(int count = 0; count < utxo.size(); count++){
            inputValue += utxo.get(count).getFirst().getOutputs().get(utxo.get(count).getSecond()).getJCoinValue();
        }

        if(inputValue < value){
            return null;
        }

        Transaction result = new Transaction();

        for(Pair<Transaction, Integer> txo: utxo){
            try {
                result.addInput(new TransactionInput(txo.getFirst().getHash(), txo.getSecond(), new UnlockParameters(keys.getPublic(),createSignature(txo.getFirst()))));
            }catch(NoSuchAlgorithmException error){

            }
        }

        result.addOutput(new TransactionOutput(value, recepient));
        result.addOutput(new TransactionOutput(inputValue - value - fee));

        return result;
    }
    public void submitTransaction(Transaction transaction){
        NetworkService.getInstance().broadcast(transaction);
    }
}
