package gui.editor;

import javax.swing.*;
import javax.swing.text.StyleContext;
import java.awt.*;


public class EditorPane extends JTextPane {
    private final StyleContext styleContext = new StyleContext();
    private final APLDocument aplDocument = new APLDocument(styleContext);
    private final DListener dListener= new DListener(styleContext, aplDocument);
    private final Editor editor;

    enum Type {
        SOURCE,
        PREPROCESSED,
        COMPILED,
    }
    private Type type;

    public EditorPane(Editor editor, Type type) {
        this.editor = editor;
        this.setPreferredSize(new Dimension(800, 600));
        this.setStyledDocument(aplDocument);
        this.type = type;
        if (type != Type.SOURCE) this.setEditable(false);
        aplDocument.addDocumentListener(dListener);
    }

    public DListener getdListener() {
        return dListener;
    }

    public Type getType() {
        return type;
    }
}
