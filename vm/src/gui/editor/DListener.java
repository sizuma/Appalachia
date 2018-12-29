package gui.editor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyleContext;

public class DListener implements DocumentListener {
    private final StyleContext styleContext;
    private final APLDocument aplDocument;
    public DListener(StyleContext styleContext, APLDocument aplDocument) {
        this.styleContext = styleContext;
        this.aplDocument = aplDocument;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.onUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.onUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.onUpdate(e);
    }

    void onUpdate(DocumentEvent e) {
        var document = e.getDocument();
        try {
            var content = document.getText(0, document.getLength());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
