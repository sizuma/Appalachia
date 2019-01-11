package gui.editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EditorTab extends JTabbedPane {
    private final Editor editor;
    private final List<EditorPane> editors = new ArrayList<>();

    public EditorTab(Editor editor) {
        this.editor = editor;
        this.editors.add(new EditorPane(editor, EditorPane.Type.SOURCE));
        this.addTab("file 1", new JScrollPane(this.editors.get(0)));
    }

    public String getActiveContent() {
        var index = this.getSelectedIndex();
        return this.editors.get(index).getText();
    }

    public EditorPane.Type getActiveType() {
        var index = this.getSelectedIndex();
        return this.editors.get(index).getType();
    }

    public void setActiveContent(String content) {
        var index = this.getSelectedIndex();
        this.editors.get(index).setText(content);
    }

    public int addEditor(EditorPane.Type type) {
        var editorPane = new EditorPane(editor, type);
        this.editors.add(editorPane);
        this.addTab("file "+this.editors.size(), new JScrollPane(editorPane));
        return this.editors.size()-1;
    }

    void closeTab(int index) {
        this.removeTabAt(index);
        this.editors.remove(index);
    }
}
