package fr.haiwa.blockchain.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static void startHelp() {
        System.out.println("Welcome to HBlockchain");
        System.out.println("Mode 0: Start new Blockchain");
        System.out.println("Mode 1: Check if the Blockchain is valid");
        System.out.println("Mode 2: Test the transaction system");
        System.out.println("Mode 3: Get the amount of public address");
        System.out.println("Mode 4: Generate Keypair");
        System.out.println("Mode 5: Generate Keypair in a file in " + PathGetter.getConstDataPath() + "Wallet");
        System.out.println("Mode 6: Test Rsa encryption with public key");
        System.out.println("Mode 7: Test Rsa decryption with private key");
        System.out.println("Mode 8: Get the path of the Blockchain");
        System.out.println("Mode 9: Generate Wallet");
        System.out.println("Mode 10: Contiue to mining current Blockchain");
        System.out.println("Mode 98: Test Mode");
        System.out.println("Mode 99: Exit");
    }

    public static void createFolder(String pathStr) throws IOException {
        Path path = Paths.get(pathStr);
        Files.createDirectories(path);
    }
}
