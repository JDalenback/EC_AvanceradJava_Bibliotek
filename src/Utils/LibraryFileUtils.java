package Utils;

import java.io.*;

public class LibraryFileUtils {
    private static String filePath = "src/library.ser";

    public LibraryFileUtils() {
    }

    public static void serializeObject(Object object) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object deSerializeObject() {
        Object object = null;
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            object = objectInputStream.readObject();
        }catch (FileNotFoundException e){
            // New Library Will be created if file is not found.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
