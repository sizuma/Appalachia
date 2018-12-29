package gui.editor;

import javax.swing.*;
import javax.swing.text.StyleContext;
import java.awt.*;


public class EditorPane extends JTextPane {
    private final StyleContext styleContext = new StyleContext();
    private final APLDocument aplDocument = new APLDocument(styleContext);
    private final DListener dListener= new DListener(styleContext, aplDocument);
    private final Editor editor;
    public EditorPane(Editor editor) {
        this.editor = editor;
        this.setPreferredSize(new Dimension(800, 600));
        this.setStyledDocument(aplDocument);
        aplDocument.addDocumentListener(dListener);
    }

    public DListener getdListener() {
        return dListener;
    }
}
