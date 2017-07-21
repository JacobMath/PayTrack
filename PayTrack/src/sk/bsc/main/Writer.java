package sk.bsc.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sum and write typed currencies by user to file.
 */
public class Writer {

    /**
     * Check if currency is already in file, if it is change value, if not just write it.
     * @param input from console.
     */
    public static void checkAndWrite(String input){
        String currency = input.substring(0,3);
        String valueString = input.substring(4);
        int valueInt = Integer.parseInt(valueString);

        List<String> fileCurrencies = new ArrayList<>();
        PayTrack.lock.readLock().lock();
        try {
            fileCurrencies = Reader.readFile();
        } finally {
            PayTrack.lock.readLock().unlock();
        }

        String existingLine = null;
        for (String line : fileCurrencies) {
            if (line.contains(currency)) {
                existingLine = line;
                break;
            }
        }

        if (existingLine != null) {
            Integer value = Integer.valueOf(existingLine.substring(4));
            value = value + valueInt;
            if (value == 0)
                fileCurrencies.remove(existingLine);
            else {
                String newLine = existingLine.substring(0, 3) + " " + value;
                System.out.println(newLine);
                fileCurrencies.remove(existingLine);
                fileCurrencies.add(newLine);
            }
        }
        else {
            fileCurrencies.add(input);
        }

        Collections.sort(fileCurrencies);
        writeToFile(fileCurrencies);
    }

    /**
     * Write edited values to file.
     * @param fileCurrencies readed from file.
     */
    private static void writeToFile(List<String> fileCurrencies){
        PayTrack.lock.writeLock().lock();
        FileWriter writer = null;
        try {
            writer = new FileWriter(PayTrack.file);
        for(String str: fileCurrencies) {
            writer.write(str+System.getProperty("line.separator"));
        }
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PayTrack.lock.writeLock().unlock();
    }
}
