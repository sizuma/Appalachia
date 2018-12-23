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

    @Override
    public String toString() {
        return kind+"{" +
                "car : " + car +
                ", cdr : " + cdr +
                '}';
    }
}
