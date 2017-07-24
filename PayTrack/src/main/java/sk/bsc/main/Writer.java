package sk.bsc.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Sum and write typed currencies by user to file.
 */
public class Writer {

    /**
     * Check if currency is already in file, if it is change value, if it is not, write it.
     * @param input red line from console.
     */
    public void checkAndWrite(String input){
        String consoleCurrencyInitials = input.substring(0,3);
        String consoleCurrencyValueString = input.substring(4);
        float consoleCurrencyValueFloat = Float.parseFloat(consoleCurrencyValueString);

        List<String> fileCurrencies;
        PayTrack.lock.readLock().lock();
        try {
            fileCurrencies = Summary.readFile();
        } finally {
            PayTrack.lock.readLock().unlock();
        }

        String existingLine = null;
        for (String line : fileCurrencies) {
            if (line.contains(consoleCurrencyInitials)) {
                existingLine = line;
                break;
            }
        }

        if (existingLine != null) {
            Float fileValue = Float.valueOf(existingLine.substring(4));
            fileValue = fileValue + consoleCurrencyValueFloat;
            if (fileValue == 0)
                fileCurrencies.remove(existingLine);
            else {
                String newLine = existingLine.substring(0, 3) + " " + fileValue;
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
     * @param fileCurrencies currencies red from file.
     */
    private static void writeToFile(List<String> fileCurrencies){
        PayTrack.lock.writeLock().lock();
        FileWriter writer;
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
