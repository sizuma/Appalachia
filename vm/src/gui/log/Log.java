package gui.log;

import runtime.VM;

import javax.swing.*;
import java.awt.*;

public class Log extends JFrame {
    private final VM vm;
    private final LogPane pane = new LogPane();

    public Log(VM vm) throws HeadlessException {
        this.vm = vm;
        this.vm.setLogger(pane);
    }

    public void open() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(new JScrollPane(pane));
        this.setSize(new Dimension(800, 600));
        this.setVisible(true);
    }
}
