package fr.haiwa.blockchain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.haiwa.blockchain.log.Log;
import fr.haiwa.blockchain.rsa.RsaEncryption;
import fr.haiwa.blockchain.structures.Blockchain;
import fr.haiwa.blockchain.structures.Transaction;
import fr.haiwa.blockchain.utils.*;
import fr.haiwa.blockchain.wallet.WalletGetter;
import fr.haiwa.blockchain.wallet.WalletMaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Scanner;

public class Main {

    public static final String OS_LOWER_CASE = System.getProperty("os.name").toLowerCase();
    public static final String OS = System.getProperty("os.name");

    private final Gson gson;
    private final Log log;

    public static final int DIFFICULTY = 4;
    public static final float REWARD = 50;
    public static String DATA_PATH = PathGetter.getConstDataPath();
    public static String WALLET_PATH = PathGetter.getConstWalletPath();
    public static String BLOCKCHAIN_PATH = PathGetter.getConstBlockchainPath();
    public static String PRIVATE_KEY_PATH = PathGetter.getConstPrivateKeyPath();
    public static String PUBLIC_KEY_PATH = PathGetter.getConstPublicKeyPath();

    public Main() {
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        this.log = new Log();
    }

    public void init() {
        if (!(OS_LOWER_CASE.contains("nix") || OS_LOWER_CASE.contains("nux") || OS_LOWER_CASE.contains("aix") || OS_LOWER_CASE.contains("win"))) {
            System.err.println("HBlockchain and NedgeCoin are unavailable for " + OS);
            System.exit(-1);
        }
    }

    public int askMode() {
        return new Scanner(System.in).nextInt();
    }

    public void isChainIsValid() {
        Blockchain blockchain = new FileManager().readBlockchain();
        System.out.println(blockchain.isChainIsValid());
    }

    public void testTx() throws FileNotFoundException {
        Blockchain blockchain = new Blockchain();
        blockchain.createGenesisBlock();
        blockchain.addTransaction(new Transaction(new WalletGetter(new File(WALLET_PATH + "/wallet.pub")).getPublicKey(0), "COINBASE", REWARD, null));
        blockchain.miningPendingTxs(new WalletGetter(new File(WALLET_PATH + "/wallet.pub")).getPublicKey(0));
        blockchain.addTransaction(new Transaction(new WalletGetter(new File(WALLET_PATH + "/wallet.pub")).getPublicKey(0), "COINBASE", REWARD, null));
        blockchain.miningPendingTxs(new WalletGetter(new File(WALLET_PATH + "/wallet.pub")).getPublicKey(0));
        System.out.println(gson.toJson(blockchain));
        new FileManager(blockchain).writeBlockchain();
    }

    public void getAmount() {
        String address = new Scanner(System.in).nextLine();
        Blockchain blockchain = new FileManager().readBlockchain();
        System.out.println(address + " " + blockchain.getAmount(address));
    }

    public void generateKeyPair() {
        try {
            RsaEncryption.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encryptString() {
        try {
            System.out.println(RsaEncryption.encrypt("Bonjour", RsaEncryption.loadPublicKey()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decryptString() {
        try {
            System.out.println(RsaEncryption.decrypt("C/tjWqTzX/Gl8hQTvf0rExh0+vq3V2b4RdFDrXqkVSJgYyxr0xKCfOhjdv2TnxiRbbzo3kZIAkrjyBE1vGLnH6f7UPwZ1VGU98XXVHLBdGkOcGfHjCvU/mo2R+8z7m8c5viucfDuh/xqp5AsvESjik3agAuGAcQFla+ECAzNwbj6RWIfvKa5rAw2kryvCYA9BPmbES5ZSBxRrdVlJuO4xC6pxiFDRmcm92+arcWJqkiTtN1z8CvZp6IuUdDmjOgYxsal8P82MobKBiR5Eoebk5pXPKBBz7OWMRQdT4cRAPFCnjTjdKJVJ+5XJkj2REQFBX4IDSuqQ/m5fHTYktdPwA==", RsaEncryption.loadPrivateKey()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBlockchainPath() {
        System.out.println(new File(BLOCKCHAIN_PATH).getAbsolutePath());
    }

    public void generateKeyPairInFile() {
        try {
            RsaEncryption.generateKeyPair(DATA_PATH + "Wallet/public.pub",
                    DATA_PATH + "Wallet/private.key");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void getAmountByWallet() {
        Blockchain blockchain = new FileManager().readBlockchain();
        if (blockchain.isChainIsValid()) {
            System.err.println(RsaEncryption.loadPublicKeyToString());
            System.out.println("The amount in your wallet is " + blockchain.getAmount(RsaEncryption.loadPublicKeyToString()));
        }
    }

    public void generateWallet() {
        WalletMaker walletMaker = new WalletMaker();
        walletMaker.generateWallet();
    }

    public void test() {
        //GENERATE Public Address
        try {
            KeyPair pair = RsaEncryption.generateKeyPair();
            System.out.println("Private: " + Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
            System.out.println("Public: " + Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
            System.out.println("Address: " + Base64.getEncoder().encodeToString(Ripemd160Digest.ripemd160DigestBytes(Sha256.Sha256(Base64.getEncoder().encodeToString(pair.getPublic().getEncoded())))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("");//TODO GENERATE PUBLIC ADDRESS
    }

    public void verifyWallet() throws FileNotFoundException {
        File file = new File(WALLET_PATH + "/wallet.pub");
        Scanner scanner = new Scanner(file);
        for (int i = 0; i < 100; i++) {
            if (scanner.hasNextLine()) {
                System.out.println(RsaEncryption.calculateAddress(scanner.nextLine()));
            }
        }
    }

    public void nedgeCoin() throws FileNotFoundException {
        Blockchain blockchain = new Blockchain();
        blockchain.createGenesisBlock();

        for (int i = 0; i < 100; i++) {
            blockchain.miningPendingTxs(new WalletGetter(new File(WALLET_PATH + "/wallet.pub")).getPublicKey(0));
        }
        System.out.println(gson.toJson(blockchain));
        new FileManager(blockchain).writeBlockchain();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Main main = new Main();

        main.init();
        Utils.startHelp();
        int mode = main.askMode();

        switch (mode) {
            case 0:
                main.nedgeCoin();
                break;
            case 1:
                main.isChainIsValid();
                break;
            case 2:
                main.testTx();
                break;
            case 3:
                main.getAmount();
                break;
            case 4:
                main.generateKeyPair();
                break;
            case 5:
                main.generateKeyPairInFile();
                break;
            case 6:
                main.encryptString();
                break;
            case 7:
                main.decryptString();
                break;
            case 8:
                main.getBlockchainPath();
                break;
            case 9:
                main.generateWallet();
                break;
            case 98:
                //Test mode
                //main.test();
                main.verifyWallet();
                break;
            case 99:
                System.exit(0);
            default:
                System.err.println("Mode " + mode + " undefied");
        }
        main.getLog().close();
    }

    public Log getLog() {
        return log;
    }
}
