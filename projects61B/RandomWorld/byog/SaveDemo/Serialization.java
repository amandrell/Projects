package byog.SaveDemo;

import java.util.Hashtable;
import java.util.Enumeration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Serialization {

        /**
         * Create a simple Hashtable and serialize it to a file called
         * SavedJungle.ser.
         */
        private static void doSave() {
            //we want to call the doSave method with a long and world[][] as parameters.
            //we then want to add these to our hashtable.
            //

            System.out.println();

            Hashtable h = new Hashtable();
            h.put("Your World", "Give your world a reference");
            h.put("int", new Integer(36));
            h.put("double", new Double(Math.PI));

            try {

                System.out.println("Creating File output stream...");

                FileOutputStream fileOut = new FileOutputStream("SavedJungle.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);

                System.out.println("Writing Hashtable Object...");
                out.writeObject(h);

                System.out.println("Closing all output streams...\n");
                out.close();
                fileOut.close();

            } catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    /**
     * Loads the contents of a previously serialized object from a file called
     * SavedJungle.ser.
     */
    private static void doLoad() {

        System.out.println();
        System.out.println("+------------------------------+");
        System.out.println("| doLoad Method                |");
        System.out.println("+------------------------------+");
        System.out.println();

        Hashtable h = null;


        try {

            System.out.println("Creating File/Object input stream...");

            FileInputStream fileIn = new FileInputStream("SavedJungle.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            System.out.println("Loading Hashtable Object...");
            h = (Hashtable)in.readObject();

            System.out.println("Closing all input streams...\n");
            in.close();
            fileIn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Printing out loaded elements...");
        for (Enumeration e = h.keys(); e.hasMoreElements(); ) {
            Object obj = e.nextElement();
            System.out.println("  - Element(" + obj + ") = " + h.get(obj));
        }
        System.out.println();

    }
    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doSave();
        doLoad();

    }

}
