package fr.haiwa.blockchain.utils;

import java.io.IOException;

public class SystemUtils {

    public SystemUtils() {

    }

    public String getUsername() {
        return System.getProperty("user.name");
    }

    public void execute(String cmd) {
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
