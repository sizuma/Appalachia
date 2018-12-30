package gui.log;

import log.Logger;

import javax.swing.*;
import java.awt.*;

public class LogPane extends JTextPane implements Logger {
    private Level level = Level.INFO;

    public LogPane() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setEditable(false);
    }

    @Override
    public void log(String log, Level level) {
        var builder = new StringBuilder(this.getText());
        builder.append("[");
        builder.append(level);
        builder.append("] ");
        builder.append(log);
        builder.append("\n");
        this.setText(builder.toString());
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }
}
