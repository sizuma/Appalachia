package gui;

import gui.editor.Editor;

import javax.swing.*;

public class GUI {
    public static void main(String ...args) {
        SwingUtilities.invokeLater(GUI::open);
    }

    static void open() {
        var editor = new Editor();
        editor.open();
    }
}
