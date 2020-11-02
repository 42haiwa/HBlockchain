package fr.haiwa.blockchain.utils;

public class StringUtils {

    public static String getDifficultyString(int difficulty) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < difficulty; i++) {
            sb.append(0);
        }

        return sb.toString();
    }

    public static String convert(String hash) {
        StringBuilder sb = new StringBuilder();
        sb.append(hash);
        if (hash.length() < 64) {
            for (int i = 0; i < 64 - hash.length(); i++) {
                sb.insert(0, "0");
            }
        }

        return sb.toString();
    }
}
