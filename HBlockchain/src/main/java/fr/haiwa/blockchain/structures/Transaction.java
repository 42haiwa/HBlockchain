package fr.haiwa.blockchain.structures;

import fr.haiwa.blockchain.rsa.RsaEncryption;
import fr.haiwa.blockchain.utils.Sha256;

import java.security.PrivateKey;
import java.sql.Timestamp;

public class Transaction {

    private String toHashedAddress;
    private String fromHashedAddress;
    private String fromAddress;
    private float amount;
    private long timestamp;
    private String signature;
    private String hash;

    public Transaction(String toAddress, String fromAddress, float amount, PrivateKey privateKey) {
        this.toHashedAddress = RsaEncryption.calculateAddress(toAddress);
        this.fromHashedAddress = RsaEncryption.calculateAddress(fromAddress);
        this.fromAddress = fromAddress;
        this.amount = amount;
        this.timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        this.signature = this.calculateSignature(privateKey);
        this.hash = this.calculateHash();
    }

    private String calculateSignature(PrivateKey privateKey) {
        if (privateKey == null) return null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.toHashedAddress).append(this.fromHashedAddress).append(this.fromAddress).append(this.amount).append(this.timestamp);
        try {
            return RsaEncryption.sign(stringBuilder.toString(), privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String calculateHash() {
        return Sha256.Sha256(this.toHashedAddress + this.fromHashedAddress + this.fromAddress + this.amount + this.timestamp + this.signature);
    }

    public String getToAddress() {
        return toHashedAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public float getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSignature() {
        return signature;
    }
}
