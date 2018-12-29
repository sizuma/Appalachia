package gui.editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EditorTab extends JTabbedPane {
    private final Editor editor;
    private final List<EditorPane> editors = new ArrayList<>();

    public EditorTab(Editor editor) {
        this.editor = editor;
        this.editors.add(new EditorPane(editor));
        this.addTab("file 1", this.editors.get(0));
    }

    public String getActiveContent() {
        var index = this.getSelectedIndex();
        return this.editors.get(index).getText();
    }

    public void setActiveContent(String content) {
        var index = this.getSelectedIndex();
        this.editors.get(index).setText(content);
    }

    public int addEditor() {
        var editorPane = new EditorPane(editor);
        this.editors.add(editorPane);
        this.addTab("file "+this.editors.size(), editorPane);
        return this.editors.size()-1;
    }

    void closeTab(int index) {
        this.removeTabAt(index);
        this.editors.remove(index);
    }
}
