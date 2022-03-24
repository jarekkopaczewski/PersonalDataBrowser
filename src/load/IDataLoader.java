package load;

import serialized.Person;

import javax.swing.*;

public interface IDataLoader {
    Person loadPersonalData(String path, String name);
    ImageIcon loadImage(String path, String id);
}
