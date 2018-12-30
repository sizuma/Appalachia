package cli;

import log.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Options {
    private boolean isCompiled = false;
    private Logger.Level level = Logger.Level.INFO;

    private final List<File> files = new ArrayList<>();

    public Logger.Level getLevel() {
        return level;
    }

    public void setLevel(Logger.Level level) {
        this.level = level;
    }

    public List<File> getFiles() {
        return files;
    }

    public void addFile(File file) {
        this.files.add(file);
    }

    public boolean isCompiled() {
        return isCompiled;
    }

    public void setCompiled(boolean compiled) {
        isCompiled = compiled;
    }
}
