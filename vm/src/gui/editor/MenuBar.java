package gui.editor;

import javax.swing.*;
import java.io.File;

public class MenuBar extends JMenuBar {
    private final Editor editor;

    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem openMenuItem = new JMenuItem("open");
    private final JMenuItem saveMenuItem = new JMenuItem("save");

    private final JFileChooser chooser = new JFileChooser(System.getenv("user.home"));

    public MenuBar(Editor editor) {
        this.editor = editor;
        this.add(this.fileMenu);
        this.fileMenu.add(this.openMenuItem);
        this.fileMenu.add(this.saveMenuItem);

        this.openMenuItem.addActionListener(event -> {
            if (chooser.showDialog(this, "開く") == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                this.editor.openFile(file);
            }
        });
        this.saveMenuItem.addActionListener(event -> {
            if (chooser.showDialog(this, "保存") == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                this.editor.saveToFile(file);
            }
        });
    }
}
