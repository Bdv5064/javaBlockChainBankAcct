import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Define a Student class
class Bankacct {
    private String name;
    private long acctNumber;
    private int routingNumber;
    private double amount;
    private String transactions;


    public Bankacct(String name, long acctNumber, int routingNumber, double amount, String transactions) {
        this.name = name;
        this.acctNumber = acctNumber;
        this.routingNumber = routingNumber;
        this.amount = amount;
        this.transactions = transactions;
    }

    // Getters for student properties
    public String getName() {
        return name;
    }

    public long getAcctNumber() {
        return acctNumber;
    }

    public int getRoutingNumber() {
        return routingNumber;
    }

    public double getAmount() {return amount;}

    public String getTransactions() {return transactions;}
}

// Define a Block class
class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private Bankacct bankAcct;

    public Block(int index, String previousHash, Bankacct bankAcct) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.bankAcct = bankAcct;
        this.hash = calculateHash();
    }

    // Calculate the hash of the block
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            int nonce = 0;
            String input;

            while (true) {
                input = index + timestamp + previousHash + bankAcct.getName() + bankAcct.getAcctNumber() + bankAcct.getRoutingNumber() + bankAcct.getAmount() + bankAcct.getTransactions() + nonce;
                byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                String hash = hexString.toString();

                // Check if the hash starts with "00"
                if (hash.startsWith("155")) {
                    return hash;
                }

                // If not, increment the nonce and try again
                nonce++;
            }
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // Getters
    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public Bankacct getBankAcct() {
        return bankAcct;
    }
}

// Define a Blockchain class
class Blockchain {
    private List<Block> chain;

    // Constructor
    public Blockchain() {
        chain = new ArrayList<Block>();
        // Create the genesis block (the first block in the chain)
        chain.add(new Block(0, "0", new Bankacct("Genesis Block", 00112233445566L, 01234567,0.0," ")));
    }

    // Add a new block to the blockchain
    public void addBankAcct(Bankacct bankAcct) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getIndex() + 1, previousBlock.getHash(), bankAcct);
        chain.add(newBlock);
    }

    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Credit Card Name: " + block.getBankAcct().getName());
            System.out.println("Credit Card Number : " + block.getBankAcct().getAcctNumber());
            System.out.println("Credit Card CVV: " + block.getBankAcct().getRoutingNumber());
            System.out.println("Credit Card Exp: " + block.getBankAcct().getAmount());
            System.out.println("Credit Card Transactions: " + block.getBankAcct().getTransactions());
            System.out.println();
        }
    }
}

public class SimpleBlockChain {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        // Create student objects and add them to the blockchain
        Bankacct bankAcct1 = new Bankacct("Dwayne Carter", 12546974231452L, 10536587,254722.15, "Date: 10/7/22 | Type: Direct Deposit | Deposit Amount: $4554.00 | OnlyFans Tips");
        Bankacct bankAcct2 = new Bankacct("Sean Carter", 32589741245963L, 42344523,14474.50, "Date: 1/7/23 |  Type: ATM Withdraw | Withdraw Amount: $1250.68 | ATM Pin Accepted");
        Bankacct bankAcct3 = new Bankacct("James Johnson", 41752136985102L, 98765214,2141.00, "Date: 9/5/23 | Type: Check Deposit | Deposit Amount: $10575.98 | Invoiced Consulting Fee");

        blockchain.addBankAcct(bankAcct1);
        blockchain.addBankAcct(bankAcct2);
        blockchain.addBankAcct(bankAcct3);

        // Print the blockchain
        blockchain.printBlockchain();
    }
}
