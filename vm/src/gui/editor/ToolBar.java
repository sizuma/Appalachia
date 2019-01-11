package gui.editor;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {
    private final JButton formatButton = new JButton("format");
    private final JButton compileButton = new JButton("compile");
    private final JButton preprocessButton = new JButton("preprocess");
    private final JButton execButton = new JButton("exec");

    private final JButton newButton = new JButton("new");
    private final JButton closeButton = new JButton("close");

    private final Editor editor;

    public ToolBar(Editor editor) {
        this.editor = editor;
        this.setLayout(new FlowLayout());
        this.add(formatButton);
        this.add(preprocessButton);
        this.add(compileButton);
        this.add(execButton);

        this.add(newButton);
        this.add(closeButton);

        this.setListeners();
    }
    void setListeners() {
        formatButton.addActionListener(ev -> this.editor.format());
        preprocessButton.addActionListener(ev -> this.editor.preprocess());
        compileButton.addActionListener(ev -> this.editor.compile());
        execButton.addActionListener(ev -> this.editor.exec());
        newButton.addActionListener(ev -> this.editor.newTab(true, EditorPane.Type.SOURCE));
        closeButton.addActionListener(ev -> this.editor.closeTab());
    }
}
