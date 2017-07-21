package sk.bsc.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Load and holds possible currencies.
 */
public class PossibleCurrencies {

    private List<String> possibleCurrencies;


    public PossibleCurrencies() {
        loadCurrencies();
    }

    /**
     * Load existing currencies from file to List of Strings.
     */
    private void loadCurrencies(){
        try (InputStream resource = this.getClass().getClassLoader().getResourceAsStream("currencies.txt")) {
            possibleCurrencies = new BufferedReader(new InputStreamReader(resource,
                    StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get list of existing currencies.
     * @return list of existing currencies.
     */
    public List<String> getPossibleCurrencies() {
        return possibleCurrencies;
    }
}
