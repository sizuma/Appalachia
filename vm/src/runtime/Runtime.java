package runtime;

import runtime.evaluate.Node;
import runtime.evaluate.Leaf;

public class Runtime {
    private final boolean logging;

    public Runtime(boolean logging) {
        this.logging = logging;
    }

    public Value evaluate(Block block, Cell cell) {
        this.log("evaluate "+cell+" in "+block);

        switch (cell.getKind()) {
            case NODE:
                return Node.evaluateNode(this, block, cell);
            case LEAF:
                return Leaf.evaluateLeaf(this, block, cell);
        }
        return Value.nop;
    }

    public void log(String string) {
        if (this.logging) {
            System.out.println(string);
        }
    }
}
