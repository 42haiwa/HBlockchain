//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package fr.haiwa.blockchain.crypto;

public interface Digest {
    String getAlgorithmName();

    int getDigestSize();

    void update(byte var1);

    void update(byte[] var1, int var2, int var3);

    int doFinal(byte[] var1, int var2);

    void reset();
}
