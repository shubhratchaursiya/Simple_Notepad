import javax.swing.*;
import java.awt.event.*;
import java.io.*;
public class SimpleNotepad extends JFrame implements ActionListener {
    JTextArea textArea;
    JFileChooser fileChooser;
    String currentFilePath = null;
    public SimpleNotepad() {
        setTitle("Java Notepad by Shubhrat");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);
        fileChooser = new JFileChooser();
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                textArea.setText("");
                currentFilePath = null;
                break;
            case "Open":
                int openResult = fileChooser.showOpenDialog(this);
                if (openResult == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        textArea.read(reader, null);
                        currentFilePath = file.getAbsolutePath();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error opening file!");
                    }
                }
                break;
            case "Save":
                if (currentFilePath == null) {
                    int saveResult = fileChooser.showSaveDialog(this);
                    if (saveResult == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                            textArea.write(writer);
                            currentFilePath = file.getAbsolutePath();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Error saving file!");
                        }
                    }
                } else {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
                        textArea.write(writer);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error saving file!");
                    }
                }
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Cut":
                textArea.cut();
                break;
            case "Copy":
                textArea.copy();
                break;
            case "Paste":
                textArea.paste();
                break;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleNotepad::new);
    }
}
