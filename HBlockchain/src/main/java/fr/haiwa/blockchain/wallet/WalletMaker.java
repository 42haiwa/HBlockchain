package fr.haiwa.blockchain.wallet;

import fr.haiwa.blockchain.Main;
import fr.haiwa.blockchain.log.Log;
import fr.haiwa.blockchain.rsa.RsaEncryption;
import fr.haiwa.blockchain.utils.Ripemd160Digest;
import fr.haiwa.blockchain.utils.Sha256;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class WalletMaker {

    public WalletMaker() {
    }

    public void generateWallet() {
        File privateKeys = new File(Main.WALLET_PATH + "/wallet.keys");
        File publicKeys = new File(Main.WALLET_PATH + "/wallet.pub");
        File address = new File(Main.WALLET_PATH + "/address.adrs");
        List<KeyPair> keyPairs = new ArrayList<>();
        try {
            FileWriter fileWriterPrivateKeys = new FileWriter(privateKeys);
            for (int i = 0; i < 100; i++) {
                keyPairs.add(RsaEncryption.generateWallet());
            }
            for (int i = 0; i < 100; i++) {
                fileWriterPrivateKeys.write(Base64.getEncoder().encodeToString(keyPairs.get(i).getPrivate().getEncoded()) + '\n');
            }
            fileWriterPrivateKeys.close();
            FileWriter fileWriterPublicKeys = new FileWriter(publicKeys);
            for (int i = 0; i < 100; i++) {
                fileWriterPublicKeys.write(Base64.getEncoder().encodeToString(keyPairs.get(i).getPublic().getEncoded()) + '\n');
            }
            fileWriterPublicKeys.close();
            FileWriter fileWriterAddress = new FileWriter(address);
            for (int i = 0; i < 100; i++) {
                //TODO Régler le bug qui ne donne pas la même address dans le fichier et dans la transaction
                System.out.println(RsaEncryption.calculateAddress(Base64.getEncoder().encodeToString(keyPairs.get(i).getPublic().getEncoded())));
                fileWriterAddress.write(RsaEncryption.calculateAddress(Base64.getEncoder().encodeToString(keyPairs.get(i).getPublic().getEncoded())) + '\n');
            }
            fileWriterAddress.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Wallet created in " + Main.WALLET_PATH);
    }
}
