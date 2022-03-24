package ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import main.DataBrowser;
import serialized.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class UserInterface{
    private final DataBrowser dataBrowser;
    private JPanel mainPanel;
    private JTable personalDataTable;
    private JButton dirButton;
    private JList<Object> filesList;
    private JButton addPerson;
    private JButton addImageButton;
    private JLabel photoLabel;
    private JButton getDetailsButton;
    private JLabel loadingPersonLabel;
    private JLabel loadingImageLabel;
    private String currentPath;
    private String selectedFolder;
    private ImageIcon currentImage;

    public UserInterface() {
        tableIni();
        dataBrowser = new DataBrowser(loadingPersonLabel, loadingImageLabel);

        dirButton.addActionListener(e -> {
            currentPath = chooseDirectory(JFileChooser.DIRECTORIES_ONLY);
            addFileNames();
        });

        addPerson.addActionListener(e -> {
            ArrayList<String> personData = getTableData();
            if (personData != null) {
                if (currentImage != null) {
                    if (currentPath != null) {
                        Person person = new Person(personData);
                        dataBrowser.addPerson(person, currentPath, currentImage);
                        JOptionPane.showMessageDialog(new JFrame(), "Person added.");
                    } else
                        JOptionPane.showMessageDialog(new JFrame(), "You need to chose path!");
                } else
                    JOptionPane.showMessageDialog(new JFrame(), "You need to add image!");
            }
        });

        addImageButton.addActionListener(e -> {
            String imagePath = chooseDirectory(JFileChooser.FILES_AND_DIRECTORIES);
            if (imagePath != null) loadImage(imagePath);
            System.gc();
        });

        getDetailsButton.addActionListener(e -> {
            try {
                selectedFolder = filesList.getSelectedValue().toString();
                currentImage = dataBrowser.getPersonImage(currentPath, selectedFolder);
                setPersonToTable(dataBrowser.getPerson(currentPath, selectedFolder));
                photoLabel.setIcon(currentImage);
                photoLabel.setText("");
            } catch (NullPointerException exp) {
                JOptionPane.showMessageDialog(new JFrame(), "There is no selected folder!");
            }
        });

    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        JFrame frame = new JFrame("Personal data browser");
        frame.setContentPane(new UserInterface().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 500);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadImage(String imagePath) {
        currentImage = new ImageIcon(imagePath);
        Image image = currentImage.getImage();
        Image resizedImage = image.getScaledInstance(300, 200, java.awt.Image.SCALE_SMOOTH);
        currentImage = new ImageIcon(resizedImage);
        photoLabel.setText("");
        photoLabel.setIcon(currentImage);
    }

    private String chooseDirectory(int type) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(type);
            fileChooser.showSaveDialog(null);
            return fileChooser.getSelectedFile().toString();
        } catch (NullPointerException exp) {
            JOptionPane.showMessageDialog(new JFrame(), "You need to choose directory!");
        }
        return null;
    }

    private void addFileNames() {
        try {
            File folder = new File(currentPath);
            File[] listOfFiles = folder.listFiles();
            ArrayList<String> listOfFileNames = new ArrayList<>();
            assert listOfFiles != null;
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isDirectory()) listOfFileNames.add(listOfFile.getName());
            }
            filesList.setListData(listOfFileNames.toArray());
        } catch (NullPointerException exp) {
            System.err.println("Current path is empty");
        }
    }

    public ArrayList<String> getTableData() {
        try {
            DefaultTableModel dtm = (DefaultTableModel) personalDataTable.getModel();
            int nRow = dtm.getRowCount();
            ArrayList<String> array = new ArrayList<>();
            for (int i = 0; i < nRow; i++)
                array.add(dtm.getValueAt(i, 1).toString());
            return array;
        } catch (NullPointerException exp) {
            JOptionPane.showMessageDialog(new JFrame(), "Fill all data!");
        }
        return null;
    }

    private void setPersonToTable(Person person) {
        personalDataTable.setValueAt(person.getName(), 0, 1);
        personalDataTable.setValueAt(person.getSurname(), 1, 1);
        personalDataTable.setValueAt(person.getEmail(), 2, 1);
        personalDataTable.setValueAt(person.getPhone(), 3, 1);
        personalDataTable.setValueAt(person.getCity(), 4, 1);
    }

    private void tableIni() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Data");
        tableModel.insertRow(0, new Object[]{"city"});
        tableModel.insertRow(0, new Object[]{"phone"});
        tableModel.insertRow(0, new Object[]{"email"});
        tableModel.insertRow(0, new Object[]{"surname"});
        tableModel.insertRow(0, new Object[]{"name"});
        personalDataTable.setModel(tableModel);
    }
}
