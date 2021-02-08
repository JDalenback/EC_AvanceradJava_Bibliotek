package Utils;

import java.io.*;

public class LibraryFileUtils {
    private static String filePath = "src/library.ser";
    @Serial
    private static final long serialVersionUID = 1L;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
