package fr.haiwa.blockchain.structures;

import com.google.gson.GsonBuilder;
import fr.haiwa.blockchain.Main;
import fr.haiwa.blockchain.rsa.RsaEncryption;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<Block> blocks;
    private List<Transaction> pendingTxs;

    public Blockchain() {
        this.blocks = new ArrayList<>();
        this.pendingTxs = new ArrayList<>();
    }

    public float getAmount(String address) {
        float amount = 0;
        for (Block block : this.blocks) {
            for (Transaction tx : block.getTxs()) {
                if (tx.getFromAddress().equalsIgnoreCase(address)) {
                    amount -= tx.getAmount();
                }

                if(tx.getToAddress().equalsIgnoreCase(address)) {
                    amount += tx.getAmount();
                }
            }
        }
        return amount;
    }

    public void addTransaction(Transaction tx) {
        this.pendingTxs.add(tx);
    }

    public void miningPendingTxs(String rewardAddress) {
        Block block = new Block(this.blocks.size(), this.getLatestBlock().getHash());
        for (Transaction tx : this.pendingTxs) {
            System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(tx));
            if (tx.getFromAddress().equalsIgnoreCase("COINBASE")) {
                boolean fake = false;
                for (int i = 0; i < this.pendingTxs.size(); i++) {
                    if (this.pendingTxs.get(i).getFromAddress().equalsIgnoreCase("COINBASE")) {
                        if (this.blocks.get(this.blocks.size() - 1).getTxs().size() != 0) {
                            if (this.pendingTxs.get(i).getPrevHash().equals(this.getLatestBlock().getLatestTx().getHash())) break;
                        }
                        if (this.pendingTxs.get(i) != tx) fake = true;
                    }
                }
                if (fake) continue;
                block.addTx(tx);
            }
            if (this.getAmount(tx.getFromAddress()) >= tx.getAmount()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(tx.getToAddress()).append(tx.getFromAddress()).append(tx.getAmount()).append(tx.getTimestamp());
                if (RsaEncryption.verify(stringBuilder.toString(), tx.getSignature(), RsaEncryption.loadPublicKey(tx.getFromAddress()))) {
                    block.addTx(tx);
                } else {
                    System.err.println("Error");
                }
            }
        }
        block.mineBlock();
        blocks.add(block);
        pendingTxs.clear();
        System.out.println(new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(this.blocks.get(this.blocks.size() - 2)));
        if (this.blocks.get(this.blocks.size() - 1).getTxs().size() == 0) {
            this.addTransaction(new Transaction("0", rewardAddress, "COINBASE", Main.REWARD, null));
            return;
        }
        this.addTransaction(new Transaction(this.blocks.get(this.blocks.size() - 1).getLatestTx().getHash(), rewardAddress, "COINBASE", Main.REWARD, null));
    }

    public Block createGenesisBlock() {
        Block genesis = new Block(0, "");
        blocks.add(genesis);
        return genesis;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Block getLatestBlock() {
        return blocks.get(blocks.size() - 1);
    }

    public Block addBlock() {
        Block block = new Block(blocks.size(), getLatestBlock().getHash());
        blocks.add(block);
        return block;
    }

    public List<Transaction> getPendingTxs() {
        return pendingTxs;
    }

    public boolean isChainIsValid() {
        //TODO Secure COINBASE txs
        for (int i = 1; i < blocks.size(); i++) {
            if (!blocks.get(i).getHash().equals(blocks.get(i).calculateHash())) {
                System.err.println("Error 1 i = " + i);
                System.err.println("Block " + i + " id: " + blocks.get(i).getId() +
                        " prevHash: " + blocks.get(i).getPrevHash() +
                        " timestamp: " + blocks.get(i).getTimestamp() +
                        " nonce: " + blocks.get(i).getNonce() +
                        " merkleRoot: " + blocks.get(i).getMerkleRoot());
                System.err.println(blocks.get(i).getHash() + " != " + blocks.get(i).calculateHash());
                return false;
            }
            if (!blocks.get(i -1).getHash().equals(blocks.get(i).getPrevHash())) {
                System.err.println("Error 2 i = " + i);
                return false;
            }
            if (blocks.get(i).getMerkleRoot() == null) continue;
            if (!blocks.get(i).getMerkleRoot().equals(blocks.get(i).calculateMerkleRoot())) {
                System.err.println("Error 3 i = " + i);
                return false;
            }
        }
        return true;
    }
}
