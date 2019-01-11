package gui.editor;

import runtime.Formatter;
import runtime.Compiler;
import runtime.Preprocessor;

import runtime.VM;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Editor extends JFrame {
    private final VM vm;

    private final ToolBar toolBar = new ToolBar(this);
    private final EditorTab editorTab = new EditorTab(this);

    public Editor(VM vm) throws HeadlessException {
        this.vm = vm;
    }

    public void open() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new MenuBar(this));
        var pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(toolBar);
        pane.add(editorTab);
        this.getContentPane().add(pane);
        this.setVisible(true);
        this.pack();
    }

    public String getContent() {
        return this.editorTab.getActiveContent();
    }

    public void setContent(String content) {
        this.editorTab.setActiveContent(content);
    }

    public void openFile(File file) {
        try {
            var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            var stringBuilder = new StringBuilder();
            var read = reader.readLine();
            while (read != null) {
                stringBuilder.append(read);
                stringBuilder.append("\n");
                read = reader.readLine();
            }
            reader.close();
            this.newTab(true, EditorPane.Type.SOURCE);
            this.setContent(stringBuilder.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile(File file) {
        try {
            var writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(this.getContent());
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void format() {
        try {
            var tempFile = this.saveTemp();
            var formatter = new Formatter(this.vm);
            var result = formatter.redirection(tempFile);
            this.setContent(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void compile() {
        if (this.editorTab.getActiveType() != EditorPane.Type.PREPROCESSED) this.preprocess();
        try {
            var tempFile = this.saveTemp();
            var compiler = new Compiler(this.vm);
            var result = compiler.redirection(tempFile);
            this.newTab(true, EditorPane.Type.COMPILED);
            this.setContent(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void preprocess() {
        this.format();
        try {
            var tempFile = this.saveTemp();
            var preprocessor = new Preprocessor(this.vm);
            var result = preprocessor.redirection(tempFile);
            this.newTab(true, EditorPane.Type.PREPROCESSED);
            this.setContent(result);
            this.format();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void exec() {
        if (this.editorTab.getActiveType() != EditorPane.Type.COMPILED) this.compile();
        try {
            var tempFile = this.saveTemp();
            vm.execTree(tempFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void closeTab() {
        this.editorTab.closeTab(this.editorTab.getSelectedIndex());
    }

    public int newTab(boolean autoSelect, EditorPane.Type type) {
        int tab = this.editorTab.addEditor(type);
        if (autoSelect) this.editorTab.setSelectedIndex(tab);
        return tab;
    }

    public File saveTemp() {
        try {
            var content = this.getContent();
            var tempFileName = "interpret_gui@"+content.hashCode()+".src";
            var out = new File(this.vm.getVmDirectory(), tempFileName);
            var writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
            writer.write(content);
            writer.close();
            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
