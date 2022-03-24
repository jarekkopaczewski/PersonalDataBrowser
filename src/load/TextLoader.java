package load;

import serialized.Person;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TextLoader implements IDataLoader {
    public TextLoader() {
    }

    @Override
    public Person loadPersonalData(String currentPath, String id) {
        ObjectInputStream objectinputstream = null;
        Person person = null;
        try {
            FileInputStream streamIn = new FileInputStream(currentPath + "\\" + id + "\\record.txt");
            objectinputstream = new ObjectInputStream(streamIn);
            person = (Person) objectinputstream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectinputstream != null) {
                try {
                    objectinputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return person;
    }

    @Override
    public ImageIcon loadImage(String path, String id) {
        return new ImageIcon(path + "\\" + id + "\\image.jpg");
    }
}
