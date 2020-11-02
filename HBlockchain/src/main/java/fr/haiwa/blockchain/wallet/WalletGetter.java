package fr.haiwa.blockchain.wallet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WalletGetter {

    private File publicKeys;

    public WalletGetter(File publicKeys) {
        this.publicKeys = publicKeys;
    }

    public String getPublicKey(int n) throws FileNotFoundException {
        n = n + 1;
        if (n < 1) n = 1;
        Scanner scanner = new Scanner(this.publicKeys);
        StringBuilder stringBuilder = new StringBuilder();
        if (n > 100) n = 100;

        for (int i = 0; i < n; i++) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }
}
