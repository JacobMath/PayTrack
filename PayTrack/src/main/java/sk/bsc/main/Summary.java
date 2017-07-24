package sk.bsc.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Print out summary of currenciesInitials every minute.
 */
public class Summary implements Runnable {

    private List<String> currenciesInitials = new ArrayList<>();

    @Override
    public void run() {
        while (true){
            waitMinute();
            this.currenciesInitials.clear();
            this.currenciesInitials = readFile();
            printCurrencies();
        }
    }

    /**
     * Wait 60 seconds.
     */
    private void waitMinute(){
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print currenciesInitials from list.
     */
    private void printCurrencies(){
        if (!currenciesInitials.isEmpty()) {
            System.out.println("Summary of existingCurrencies :");
            for (String currencyInitials : currenciesInitials) {
                System.out.print(currencyInitials);
                printExchange(currencyInitials);
            }
        }
    }

    /**
     * Read file and return lines in List of Strings.
     * @return lines red from file in List of Strings.
     */
    public static List<String> readFile(){
        PayTrack.lock.readLock().lock();
        List<String> currencies = new ArrayList<>();
        try {
            currencies = Files.readAllLines(Paths.get((PayTrack.file).toURI()), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            PayTrack.lock.readLock().unlock();
        }
        return currencies;
    }

    /**
     * Convert all currenciesInitials to USD and print exchanged value in bracelets, next to original currency.
     * Do not print converted currency if original currency is USD.
     * @param currency original currency.
     */
    private void printExchange(String currency){
        if (!currency.contains("USD")) {
            Float exchangedValue = ((Float.parseFloat(currency.substring(3)))*(ExistingCurrencies.getInstanceEC().findCurrency(currency.substring(0,3)).getUSDPerUnits()));
            System.out.print("   (USD " + round(exchangedValue, 2) + ")" + System.getProperty("line.separator"));
        } else {
            System.out.print(System.getProperty("line.separator"));
        }
    }

    /**
     * Round float number.
     * @param d float number to be rounded.
     * @param decimalPlace count of decimal places of rounded number.
     * @return rounded number in float type.
     */
    private float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
