package fr.haiwa.blockchain.rsa;

import fr.haiwa.blockchain.Main;
import fr.haiwa.blockchain.utils.Ripemd160Digest;
import fr.haiwa.blockchain.utils.Sha256;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RsaEncryption {
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        FileWriter fileWriter = new FileWriter(Main.PUBLIC_KEY_PATH);
        fileWriter.write(Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
        fileWriter.close();
        FileWriter fileWriter1 = new FileWriter(Main.PRIVATE_KEY_PATH);
        fileWriter1.write(Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
        fileWriter1.close();

        return pair;
    }

    public static KeyPair generateWallet() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048, new SecureRandom());
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyPair generateKeyPair(String publicKeyFile, String privateKeyFile) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        FileWriter fileWriter = new FileWriter(publicKeyFile);
        fileWriter.write(Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
        fileWriter.close();
        FileWriter fileWriter1 = new FileWriter(privateKeyFile);
        fileWriter1.write(Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
        fileWriter1.close();

        return pair;
    }

    public static String calculateAddress(String publicKey) {
        return Base64.getEncoder().encodeToString(Ripemd160Digest.ripemd160DigestBytes(Sha256.Sha256(publicKey)));
    }

    public static KeyPair getKeyPairFromKeyStore() throws Exception {
        //Generated with:
        //  keytool -genkeypair -alias mykey -storepass s3cr3t -keypass s3cr3t -keyalg RSA -keystore keystore.jks

        InputStream ins = RsaEncryption.class.getResourceAsStream("/keystore.jks");

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(ins, "s3cr3t".toCharArray());   //Keystore password
        KeyStore.PasswordProtection keyPassword =       //Key password
                new KeyStore.PasswordProtection("s3cr3t".toCharArray());

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("mykey", keyPassword);

        java.security.cert.Certificate cert = keyStore.getCertificate("mykey");
        PublicKey publicKey = cert.getPublicKey();
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return new KeyPair(publicKey, privateKey);
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }

    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) {
        Signature publicSignature = null;
        try {
            publicSignature = Signature.getInstance("SHA256withRSA");
            publicSignature.initVerify(publicKey);
            publicSignature.update(plainText.getBytes(UTF_8));

            byte[] signatureBytes = Base64.getDecoder().decode(signature);

            return publicSignature.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static PublicKey loadPublicKey(String publicKey) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey loadPublicKey() {
        File file = new File(Main.PUBLIC_KEY_PATH);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(stringBuilder.toString())));
        } catch (FileNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey loadPrivateKey(String privateKey) {
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey loadPrivateKey() {
        File file = new File(Main.PRIVATE_KEY_PATH);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(stringBuilder.toString())));
        } catch (FileNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String loadPublicKeyToString() {
        File file = new File(Main.PUBLIC_KEY_PATH);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String loadPrivateKeyToString() {
        File file = new File(Main.PRIVATE_KEY_PATH);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    public static String loadPublicKeyToString(File publicKey, int keyNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> publicKeys;
        try {
            Scanner scanner = new Scanner(publicKey);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            publicKeys = Arrays.asList(stringBuilder.toString().split("\n"));
            return publicKeys.get(keyNumber);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String loadPrivateKeyToString(File privateKey) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(privateKey);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
