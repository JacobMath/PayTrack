package sk.bsc.main;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The PayTrack application sum up currencies written in console, store it into file and printing it out every minute.
 * @author JAKM
 */
public class PayTrack {

    public static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    public static File file;

    public PayTrack() {
    }

    /**
     * Separate threads for ConsoleUI and Summary, and start them.
     * @param args Absolute path to file where to write currencies
     */
    public static void main(String[] args) {
        PayTrack payTrack = new PayTrack();
        payTrack.welcomeMesage();

        if (args.length != 0 && !args[0].isEmpty())
            file = payTrack.findOrCreateFile(args[0]);
        else
            file = payTrack.findOrCreateFile(System.getProperty("user.dir")+"/default.txt");

        Thread reader = new Thread(new Summary());
        reader.setName("Summary");
        Thread console = new Thread(new ConsoleUI());
        console.setName("ConsoleUI");
        reader.start();
        console.start();
    }

    /**
     * Print welcome message
     */
    private void welcomeMesage(){
        System.out.println("- - - - - PAYMENT TRACKER - - - - -");
        System.out.println("Welcome dear customer!");
    }

    /**
     * Try to find file obtained as parameter, if it's not created yet, create it.
     * @param path String value of path, where to find/create file.
     */
    public File findOrCreateFile(String path) {
        file = new File(path);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
            System.out.println("Default file will be used !");
            findOrCreateFile(System.getProperty("user.dir") + "/default.txt");
        }
        return file;
    }


}
