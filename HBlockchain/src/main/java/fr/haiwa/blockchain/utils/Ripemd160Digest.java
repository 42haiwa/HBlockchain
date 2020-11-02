package fr.haiwa.blockchain.utils;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Ripemd160Digest {

    public static String ripemd160Digest(String string) {
        try {
            byte[] r = string.getBytes("UTF-8");
            RIPEMD160Digest ripemd160Digest = new RIPEMD160Digest();
            ripemd160Digest.update(r, 0, r.length);
            byte[] o = new byte[ripemd160Digest.getDigestSize()];
            ripemd160Digest.doFinal(o, 0);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Hex.encode(o, byteArrayOutputStream);
            return byteArrayOutputStream.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] ripemd160DigestBytes(String string) {
        try {
            byte[] r = string.getBytes("UTF-8");
            RIPEMD160Digest ripemd160Digest = new RIPEMD160Digest();
            ripemd160Digest.update(r, 0, r.length);
            byte[] o = new byte[ripemd160Digest.getDigestSize()];
            ripemd160Digest.doFinal(o, 0);
            return o;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
