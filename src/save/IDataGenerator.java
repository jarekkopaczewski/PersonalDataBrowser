package save;

import serialized.Person;

import javax.swing.*;

public interface IDataGenerator {
    void addPerson(Person person, String path, ImageIcon image);
}
