package fr.haiwa.blockchain.log;

import fr.haiwa.blockchain.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Log {

    //TODO finir le système de log avec des Thread

    private final SimpleDateFormat simpleDateFormat;
    private Timestamp timestamp;
    private String date;
    private File file;
    private FileWriter fileWriter;

    public Log() {
        this.simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.date = simpleDateFormat.format(timestamp);
        this.file = new File(Main.DATA_PATH + "/Log/log" + this.date);
        try {
            this.fileWriter = new FileWriter(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outLog(String string) {
        try {
            this.fileWriter.write(string + '\n');
            this.fileWriter.close();
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void errLog(String string) {
        try {
            this.fileWriter.write(string + '\n');
            System.err.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
