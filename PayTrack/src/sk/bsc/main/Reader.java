package sk.bsc.main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Print out summary of currencies every minute.
 */
public class Reader implements Runnable {

    private List<String> currencies = new ArrayList<>();


    public void run() {
        while (true){
            waitMinute();
            this.currencies.clear();
            this.currencies = readFile();
            printCurrencies();
        }
    }

    /**
     * Wait 60 seconds.
     */
    private void waitMinute(){
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print currencies from list.
     */
    private void printCurrencies(){
        if (!currencies.isEmpty()) {
            System.out.println("Summary of possibleCurrencies :");
            for (String currencyItr : currencies)
                System.out.println(currencyItr);
        }
    }

    /**
     * Read file and return lines in List of Strings.
     * @return lines readed from file in List of Strings.
     */
    public static List<String> readFile(){
        PayTrack.lock.readLock().lock();
        List<String> currencies = new ArrayList<>();
        try {
            currencies= Files.readAllLines(Paths.get((PayTrack.file).toURI()), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            PayTrack.lock.readLock().unlock();
        }
        return currencies;
    }

}
