package cli;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Options {
    private boolean showLog = false;
    private boolean isInterpret = false;

    private final List<File> files = new ArrayList<>();

    public boolean isShowLog() {
        return showLog;
    }

    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }

    public boolean isInterpret() {
        return isInterpret;
    }

    public void setInterpret(boolean interpret) {
        isInterpret = interpret;
    }

    public List<File> getFiles() {
        return files;
    }

    public void addFile(File file) {
        this.files.add(file);
    }
}
