package sk.bsc.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * ConsoleUI reads user input and inform about current state.
 */
public class ConsoleUI implements Runnable{

    PossibleCurrencies possibleCurrencies;

    public ConsoleUI() {
        possibleCurrencies = new PossibleCurrencies();
    }



    @Override
    public void run() {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.handleUserInput();
    }


    /**
     * Inform user about wrong format and ask him to type again.
     */
    private void handleFalseInput(){
        System.out.println("Wrong format of input! Please make sure you type in Format: CCC <amount> , etc. USD -5329");
        checkUserInput();
    }

    /**
     * Ask user to write to console.
     */
    private void handleUserInput(){
        System.out.println("Please type in currency and amount (Format: CCC <amount> , etc. USD -5329)");
        checkUserInput();
    }


    /**
     * Check if user input is in right format, and compare if inserted currency really exist.
     * If not, offer similar currencies.
     */
    private void checkUserInput(){
        String input = readConsole();
        checkQuit(input);
        if (!input.matches("^[A-Z]{3}[ ]*[-+]?\\d{1,}[ ]*"))
            handleFalseInput();
        else {
            String typedCurrency = input.substring(0, 3);
            if (checkIfExist(typedCurrency)) {
                System.out.println("Command accepted !");
                Writer.checkAndWrite(input);
                handleUserInput();
            }
            else {
                offerSimmilarCurrencies(getSimmilarCurrencies(typedCurrency));
                handleUserInput();
            }
        }
    }


    /**
     * Read user input from console.
     * @return readed input in String.
     */
     private String readConsole(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = "";
        try {
            s = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * Check if currency really exist.
     * @param currency typed currency by User.
     * @return true if exist.
     */
    private boolean checkIfExist(String currency){
        for (String currencyItr: possibleCurrencies.getPossibleCurrencies())
            if (currency.equals(currencyItr))
                return true;
        return false;
    }

    /**
     * Display to console similar currencies.
     * @param possibleCurrencies List of simmilar currencies.
     */
    private void offerSimmilarCurrencies(ArrayList<String> possibleCurrencies){
        System.out.println("Don't you mean one of those possibleCurrencies?  ");
        for (String possibleCurrency : possibleCurrencies)
            System.out.print(possibleCurrency + ", ");
        System.out.print(System.getProperty("line.separator"));
    }

    /**
     * Find similar currencies in list of existing.
     * @param currency typed currency by User.
     * @return List of simmilar currencies.
     */
    private ArrayList<String> getSimmilarCurrencies(String currency){
        ArrayList<String> possibleCurrencies = new ArrayList<>();
        String[] charOptions = getCharOptions(currency);

        for (String currencyItr: this.possibleCurrencies.getPossibleCurrencies())
            for (int i=0; i<3; i++)
                if (currencyItr.contains(charOptions[i]) && currencyItr.contains(charOptions[i+1]))
                    possibleCurrencies.add(currencyItr);
        return possibleCurrencies;
    }

    /**
     * Get array of chars from typed currency.
     * @param string typed currency by User.
     * @return array of chars.
     */
    private String[] getCharOptions(String string){
        String[] strings = new String[4];
        for (int i=0; i<3; i++){
            strings[i] = string.substring(i,i+1);
        }
        strings[3] = string.substring(0,1);
        return strings;
    }

    /**
     * Check if user typed quit, if yes exit app.
     * @param input from console.
     */
    private void checkQuit(String input){
        if (input.contentEquals("quit") || input.contentEquals("QUIT") ){
            System.out.println("Thank you for using our Application!");
            System.exit(0);
        }
    }




//    public static Set<Character> stringToCharacterSet(String s) {
//        Set<Character> set = new HashSet<>();
//        for (char c : s.toCharArray()) {
//            set.add(c);
//        }
//        return set;
//    }
//
//    public static boolean containsAllChars
//            (String container, String containee) {
//        return stringToCharacterSet(container).containsAll
//                (stringToCharacterSet(containee));
//    }

}


//^[A-Z]{3}[ ]*\d{1,}