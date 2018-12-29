package cli;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Options {
    private boolean showLog = false;
    private boolean isCompiled = false;

    private final List<File> files = new ArrayList<>();

    public boolean isShowLog() {
        return showLog;
    }

    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
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
