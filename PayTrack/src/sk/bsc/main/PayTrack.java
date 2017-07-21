package sk.bsc.main;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The PayTrack application sum up currencies written by console and printing it out every minute.
 * @author JAKM
 */
public class PayTrack {

    public static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    public static File file;

    public PayTrack() {
        welcomeMesage();
    }

    /**
     * Separate threads for ConsoleUI and Reader, and start them.
     * @param args Absolute path to file where to write written currencies
     */
    public static void main(String[] args) {
        PayTrack payTrack = new PayTrack();

        if (args.length != 0 && !args[0].isEmpty())
            payTrack.findOrCreateFile(args[0]);
        else
            payTrack.findOrCreateFile(System.getProperty("user.dir")+"/default.txt");
        ///home/jackyslaw/Desktop/PayTrack/PayTrack/newFile.txt

        Thread reader = new Thread(new Reader());
        reader.setName("Reader");
        Thread console = new Thread(new ConsoleUI());
        console.setName("ConsoleUI");
        reader.start();
        console.start();
    }

    /**
     * Print welcome message
     */
    private void welcomeMesage(){
        System.out.println("- - - PAYMENT TRACKER - - -");
        System.out.println("Welcome dear customer!");
    }

    /**
     * Try to find file from parameter, if it's not created yet, create it.
     * @param path where to find/create file.
     */
    private void findOrCreateFile(String path) {
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
    }

}
