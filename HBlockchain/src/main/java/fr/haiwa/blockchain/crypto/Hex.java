//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package fr.haiwa.blockchain.crypto;

import fr.haiwa.blockchain.crypto.Encoder;
import fr.haiwa.blockchain.crypto.HexEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Hex {
    private static final Encoder encoder = new HexEncoder();

    public Hex() {
    }

    public static byte[] encode(byte[] var0) {
        return encode(var0, 0, var0.length);
    }

    public static byte[] encode(byte[] var0, int var1, int var2) {
        ByteArrayOutputStream var3 = new ByteArrayOutputStream();

        try {
            encoder.encode(var0, var1, var2, var3);
        } catch (IOException var5) {
            throw new RuntimeException("exception encoding Hex string: " + var5);
        }

        return var3.toByteArray();
    }

    public static int encode(byte[] var0, OutputStream var1) throws IOException {
        return encoder.encode(var0, 0, var0.length, var1);
    }

    public static int encode(byte[] var0, int var1, int var2, OutputStream var3) throws IOException {
        return encoder.encode(var0, var1, var2, var3);
    }

    public static byte[] decode(byte[] var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();

        try {
            encoder.decode(var0, 0, var0.length, var1);
        } catch (IOException var3) {
            throw new RuntimeException("exception decoding Hex string: " + var3);
        }

        return var1.toByteArray();
    }

    public static byte[] decode(String var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();

        try {
            encoder.decode(var0, var1);
        } catch (IOException var3) {
            throw new RuntimeException("exception decoding Hex string: " + var3);
        }

        return var1.toByteArray();
    }

    public static int decode(String var0, OutputStream var1) throws IOException {
        return encoder.decode(var0, var1);
    }
}
