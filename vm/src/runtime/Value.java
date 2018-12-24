package runtime;

public class Value {
    public enum Kind {
        NUMBER,
        STRING,
        LAMBDA,
        NOP,
    }

    private final Kind kind;
    private final Object object;

    static public Value nop = new Value(Kind.NOP, null);
    static public Value True = new Value(Kind.NUMBER, 1.0);
    static public Value False = new Value(Kind.NUMBER, 0.0);

    public Value(Kind kind, Object object) {
        this.kind = kind;
        this.object = object;
    }

    public Kind getKind() {
        return kind;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Value) {
            Value other = (Value)obj;
            if (this.kind == Kind.NOP && other.kind == Kind.NOP) return true;
            else return this.kind == other.kind && this.object.equals(other.object);
        }
        return false;
    }

    @Override
    public String toString() {
        return kind+"{" + object +"}";
    }

    public boolean isTrue() {
        var isTrueCondition = true;
        if (this.getObject().equals(0.0)) isTrueCondition = false;
        if (this.getObject().equals("")) isTrueCondition = false;
        if (this.getKind() == Kind.NOP) isTrueCondition = false;
        return isTrueCondition;
    }
}
