package fr.haiwa.blockchain.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.haiwa.blockchain.Main;
import fr.haiwa.blockchain.structures.Blockchain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

    private Blockchain blockchain;
    private Gson gson;

    public FileManager(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    }

    public FileManager() {
        this.gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

    public void writeBlockchain() {
        File file = new File(Main.BLOCKCHAIN_PATH);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(this.blockchain));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Blockchain readBlockchain() {
        File file = new File(Main.BLOCKCHAIN_PATH);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            return gson.fromJson(stringBuilder.toString(), Blockchain.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
