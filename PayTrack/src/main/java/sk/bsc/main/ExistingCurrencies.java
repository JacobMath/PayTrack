package sk.bsc.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Load and holds existing currencies.
 */
public class ExistingCurrencies {

    private static ExistingCurrencies instanceEC;

    private List<Currency> existingCurrencies;

    private ExistingCurrencies() {
        readLines();
    }

    /**
     * Load existing currencies from file to List of Strings.
     */
    private void readLines(){
        try (InputStream resource = this.getClass().getClassLoader().getResourceAsStream("currenciesExchange.txt")) {
            List<String> lines = new BufferedReader(new InputStreamReader(resource,
                    StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
            loadCurrencies(lines);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates list of {@link Currency} from Strings obtained as parameter.
     * @param lines from file stored in List of Strings.
     */
    private void loadCurrencies(List<String> lines){
        existingCurrencies = new ArrayList<>();
        for (String line: lines) {
            String[] words = line.split("\\s+");

            StringBuilder nameBuilder = new StringBuilder();
            for (int i = 1; i < words.length-2; i++)
                   nameBuilder.append(words[i]);
            existingCurrencies.add(new Currency(words[0], nameBuilder.toString(),
                    Float.parseFloat((words[words.length-2])), Float.parseFloat((words[words.length-1]))));
        }
    }


    /**
     * Get list of existing currencies.
     * @return list of existing currencies.
     */
    public List<Currency> getExistingCurrencies() {
        return existingCurrencies;
    }

    /**
     * Find currency by initials.
     * @param currencyInitials Initials of searched currency.
     * @return {@link Currency}, if not found return null.
     */
    public Currency findCurrency(String currencyInitials){
        for (Currency currency: existingCurrencies){
            if (currency.getInitials().contentEquals(currencyInitials))
                return currency;
        }
        return null;
    }

    /**
     * Gets Singleton instance of {@link ExistingCurrencies}
     * @return instance of {@link ExistingCurrencies}
     */
    public static ExistingCurrencies getInstanceEC(){
        if(instanceEC == null) {
            instanceEC = new ExistingCurrencies();
        }
        return instanceEC;
    }
}
