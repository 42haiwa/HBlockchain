package fr.haiwa.blockchain.structures;

import fr.haiwa.blockchain.Main;
import fr.haiwa.blockchain.utils.Sha256;
import fr.haiwa.blockchain.utils.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Block {

    private int id;
    private String prevHash;
    private long timestamp;
    private int nonce;
    private List<Transaction> txs;
    private String merkleRoot;
    private String hash;


    public Block(int id, String prevHash) {
        this.id = id;
        this.prevHash = prevHash;
        this.timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        this.nonce = 0;
        this.txs = new ArrayList<>();
        this.merkleRoot = null;
        this.hash = this.calculateHash();
    }

    public void addTx(Transaction tx) {
        this.txs.add(tx);
        this.calculateMerkleRoot();
    }

    public String calculateMerkleRoot() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Transaction tx : txs) {
            tx.setHash(tx.calculateHash());
            stringBuilder.append(tx.getHash());
        }
        this.merkleRoot = Sha256.Sha256(stringBuilder.toString());
        return this.merkleRoot;
    }

    public void mineBlock() {
        String target = StringUtils.getDifficultyString(Main.DIFFICULTY);

        while (!this.hash.substring(0, Main.DIFFICULTY).equals(target)) {
            this.nonce = new Random().nextInt((Integer.MAX_VALUE - 1) + 1) + 1;
            this.hash = this.calculateHash();
        }
        if (this.nonce == 0) System.err.println(this.nonce);
    }

    public String calculateHash() {
        return StringUtils.convert(Sha256.Sha256(this.id + this.prevHash + this.timestamp + this.nonce + this.merkleRoot));
    }

    public int getId() {
        return id;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public int getNonce() {
        return nonce;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public List<Transaction> getTxs() {
        return txs;
    }
}
