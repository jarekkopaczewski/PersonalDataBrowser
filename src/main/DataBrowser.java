package main;

import load.IDataLoader;
import load.TextLoader;
import save.IDataGenerator;
import save.TextGenerator;
import serialized.Person;

import javax.swing.*;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.WeakHashMap;

public class DataBrowser {
    private final WeakHashMap<String, WeakReference<Person>> usersData;
    private final WeakHashMap<String, WeakReference<ImageIcon>> usersImages;
    private final IDataGenerator IDataGenerator;
    private final IDataLoader IDataLoader;
    private final JLabel loadingPerson;
    private final JLabel loadingImage;

    public DataBrowser(JLabel loadingPerson, JLabel loadingImage) {
        this.usersData = new WeakHashMap<>();
        this.usersImages = new WeakHashMap<>();
        this.IDataGenerator = new TextGenerator();
        this.IDataLoader = new TextLoader();
        this.loadingPerson = loadingPerson;
        this.loadingImage = loadingImage;
    }

    public void addPerson(Person person, String path, ImageIcon image) {
        IDataGenerator.addPerson(person, path, image);
    }

    public Person getPerson(String path, String id) {
        if (usersData.containsKey(id) && usersData.get(id).get() != null) {
            loadFromMap(loadingPerson);
            return usersData.get(id).get();
        }
        WeakReference<Person> weakPerson = new WeakReference<>(IDataLoader.loadPersonalData(path, id));
        usersData.put(id, weakPerson);
        loadFromDrive(loadingPerson);
        return weakPerson.get();
    }

    public ImageIcon getPersonImage(String path, String id) {
        if (usersImages.containsKey(id) && usersImages.get(id).get() != null) {
            loadFromMap(loadingImage);
            return usersImages.get(id).get();
        }
        WeakReference<ImageIcon> weakImage = new WeakReference<>(IDataLoader.loadImage(path, id));
        usersImages.put(id, weakImage);
        loadFromDrive(loadingImage);
        return weakImage.get();
    }

    private void loadFromDrive(JLabel label) {
        label.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons8-drive-64.png"))));
    }

    private void loadFromMap(JLabel label) {
        label.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons8-refresh-40.png"))));
    }

}
