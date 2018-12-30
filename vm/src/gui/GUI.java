package gui;

import gui.editor.Editor;
import gui.log.Log;
import runtime.VM;

import javax.swing.*;

public class GUI {
    private static final VM vm = new VM();

    public static void main(String ...args) {
        SwingUtilities.invokeLater(GUI::open);
    }

    static void open() {
        var log = new Log(vm);
        log.open();
        var editor = new Editor(vm);
        editor.open();
    }
}
