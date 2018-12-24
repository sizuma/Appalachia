package runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Block {
    private final Optional<Block> parent;
    private final Map<String, Value> table;

    public Block(Optional<Block> parent) {
        this.parent = parent;
        this.table = new HashMap<>();
    }

    public Optional<Block> getParent() {
        return parent;
    }

    public boolean isRootBlock () {
        return this.parent.isEmpty();
    }

    public void declareVariable(String name, Value variable) {
//        if (this.table.containsKey(name)) {
//            throw new RuntimeException("variable "+name+" is already declared in this scope");
//        }
        this.table.put(name, variable);
    }

    public Optional<Value> getVariable(String name) {
        var mayVariable = Optional.ofNullable(table.get(name));
        if (mayVariable.isEmpty() && !this.isRootBlock()) {
            mayVariable = this.getParent().get().getVariable(name);
        }
        return mayVariable;
    }

    public Block newChildBlock() {
        return new Block(Optional.of(this));
    }

    @Override
    public String toString() {
        return "Block{" +
                "parent=" + parent +
                ", table=" + table +
                '}';
    }
}
