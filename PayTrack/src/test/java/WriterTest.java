import org.junit.Before;
import org.junit.Test;
import sk.bsc.main.PayTrack;
import sk.bsc.main.Summary;
import sk.bsc.main.Writer;

import java.io.File;

import static org.junit.Assert.assertTrue;


public class WriterTest {

    private Writer writer;
    private PayTrack payTrack;

    @Before
    public void initTestFile(){
        File file = new File(System.getProperty("user.dir") + "/test.txt");
        if(file.exists())
            file.delete();

        payTrack = new PayTrack();
        PayTrack.file = (payTrack.findOrCreateFile(System.getProperty("user.dir") + "/test.txt"));

        writer = new Writer();
    }

    @Test
    public void addCurrencyTest(){
        String currency = "EUR 700";

        writer.checkAndWrite(currency);

        System.out.println("Added currency found: " + Summary.readFile().contains(currency));
        assertTrue("Currency not found",Summary.readFile().contains(currency));
    }

    @Test
    public void deleteZeroCurrencyTest(){

        String currencyInitials = "GBP";
        int currencyValue = 100;

        String positiveCurrency = new StringBuilder(currencyInitials +" "+ currencyValue).toString();
        String negativeCurrency = new StringBuilder(currencyInitials +" -"+ currencyValue).toString();

        writer.checkAndWrite(positiveCurrency);
        boolean found = Summary.readFile().contains(positiveCurrency);
        System.out.println("Positive currency added and found : " + found);

        writer.checkAndWrite(negativeCurrency);
        System.out.println("Currency found after delete : " + !Summary.readFile().contains(positiveCurrency));
        assertTrue("Failed to add and delete currency",found && !Summary.readFile().contains(positiveCurrency));
    }
}
