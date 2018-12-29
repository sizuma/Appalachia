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
    private final EditorPane editorPane = new EditorPane(this);

    public Editor() throws HeadlessException {
        try {
            this.vm = new VM();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void open() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(toolBar);
        pane.add(new JScrollPane(editorPane));
        this.getContentPane().add(pane);
        this.setVisible(true);
        this.pack();
    }

    public String getContent() {
        return this.editorPane.getText();
    }

    public void setContent(String content) {
        this.editorPane.setText(content);
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
        try {
            var tempFile = this.saveTemp();
            var compiler = new Compiler(this.vm);
            var result = compiler.redirection(tempFile);
            this.setContent(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void preprocess() {
        try {
            var tempFile = this.saveTemp();
            var preprocessor = new Preprocessor(this.vm);
            var result = preprocessor.redirection(tempFile);
            this.setContent(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void exec() {
        try {
            var tempFile = this.saveTemp();
            vm.execTree(tempFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
