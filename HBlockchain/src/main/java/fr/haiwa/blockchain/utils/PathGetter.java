package fr.haiwa.blockchain.utils;

import fr.haiwa.blockchain.Main;

public class PathGetter {

    public static String getConstWalletPath() {
        if (Main.OS_LOWER_CASE.contains("nix") || Main.OS_LOWER_CASE.contains("nux") || Main.OS_LOWER_CASE.contains("aix")) {
            return "/home/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/Wallet";
        } else if (Main.OS_LOWER_CASE.contains("win")) {
            return "C:/Users/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/Wallet";
        }
        return null;
    }

    public static String getConstDataPath() {
        if (Main.OS_LOWER_CASE.contains("nix") || Main.OS_LOWER_CASE.contains("nux") || Main.OS_LOWER_CASE.contains("aix")) {
            return "/home/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/";
        } else if (Main.OS_LOWER_CASE.contains("win")) {
            return "C:/Users/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/";
        }
        return null;
    }

    public static String getConstBlockchainPath() {
        if (Main.OS_LOWER_CASE.contains("nix") || Main.OS_LOWER_CASE.contains("nux") || Main.OS_LOWER_CASE.contains("aix")) {
            return "/home/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/Blockchain.json";
        } else if (Main.OS_LOWER_CASE.contains("win")) {
            return "C:/Users/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/Blockchain.json";
        }
        return null;
    }

    public static String getConstPrivateKeyPath() {
        if (Main.OS_LOWER_CASE.contains("nix") || Main.OS_LOWER_CASE.contains("nux") || Main.OS_LOWER_CASE.contains("aix")) {
            return "/home/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/Wallet/private.key";
        } else if (Main.OS_LOWER_CASE.contains("win")) {
            return "C:/Users/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/Wallet/private.key";
        }
        return null;
    }

    public static String getConstPublicKeyPath() {
        if (Main.OS_LOWER_CASE.contains("nix") || Main.OS_LOWER_CASE.contains("nux") || Main.OS_LOWER_CASE.contains("aix")) {
            return "/home/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/Wallet/public.pub";
        } else if (Main.OS_LOWER_CASE.contains("win")) {
            return "C:/Users/" + new SystemUtils().getUsername() + "/Documents/NedgeCoin/Wallet/public.pub";
        }
        return null;
    }
}
