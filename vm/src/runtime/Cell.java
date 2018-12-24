package runtime;

public class Cell {
    public enum Kind {
        CONS,
        NODE,
        LEAF,
    }

    private final Object car;
    private final Object cdr;
    private final Kind kind;
    public Cell(Object car, Object cdr, Kind kind) {
        this.car = car;
        this.cdr = cdr;
        this.kind = kind;
    }

    public Object getCar() {
        return this.car;
    }
    public Object getCdr() {
        return this.cdr;
    }
    public Kind getKind() {
        return this.kind;
    }

    public Cell getCarCell() {
        assert this.getKind() == Kind.CONS;
        return (Cell) this.getCar();
    }

    public String getCarString() {
        assert this.getKind() == Kind.NODE || this.getKind() == Kind.LEAF;
        return ((String) this.getCar()).strip();
    }

    public Cell getCdrCell() {
        assert this.getKind() == Kind.CONS || this.getKind() == Kind.NODE;
        return (Cell) this.getCdr();
    }

    public String getCdrString() {
        assert this.getKind() == Kind.LEAF;
        return ((String) this.getCdr()).strip();
    }

    @Override
    public String toString() {
        return kind+"{" +
                "car : " + car +
                ", cdr : " + cdr +
                '}';
    }
}
