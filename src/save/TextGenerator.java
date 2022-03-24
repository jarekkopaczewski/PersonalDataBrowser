package save;

import serialized.Person;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextGenerator implements IDataGenerator {

    @Override
    public void addPerson(Person person, String path, ImageIcon image) {
        generateDir(path, person.id);
        createPersonFile(path, person);
        generateImage(path, image, person.id);
    }

    private void generateImage(String path, ImageIcon image, String id) {
        Image img = image.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        try {
            ImageIO.write(bi, "jpg", new File(path + "\\" + id + "\\image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateDir(String currentPath, String id) {

        if (!Files.exists(Path.of(currentPath + "\\" + id))) {
            try {
                Path path = Paths.get(currentPath + "\\" + id);
                Files.createDirectory(path);
            } catch (IOException exp) {
                System.out.println("Create dir error!");
            }
        }
    }

    private void createPersonFile(String currentPath, Person person) {
        File file = new File(currentPath + "\\" + person.id + "\\record.txt");
        try {
            Files.deleteIfExists(Paths.get(currentPath + "\\" + person.id + "\\record.txt"));
            file.createNewFile();
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(person);
            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
