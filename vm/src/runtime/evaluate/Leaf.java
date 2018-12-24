package runtime.evaluate;

import runtime.Block;
import runtime.Cell;
import runtime.Value;
import runtime.Runtime;

public class Leaf {

    static public Value evaluateLeaf(Runtime runtime, Block block, Cell cell) {
        assert cell.getKind() == Cell.Kind.LEAF;
        var car = cell.getCarString();
        var cdr = cell.getCdrString();

        switch (car) {
            case "NUMBER":
                return new Value(Value.Kind.NUMBER, Double.valueOf(cdr));
            case "STRING":
                return new Value(Value.Kind.STRING, cdr);
            case "ID":
                var mayVariable = block.getVariable(cdr);
                if (mayVariable.isPresent()) return mayVariable.get();
                else throw new RuntimeException("variable "+cdr+" is not declared");
        }
        return Value.nop;
    }
}
