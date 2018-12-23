package runtime.evaluate;

import runtime.Block;
import runtime.Cell;
import runtime.Value;
import runtime.Runtime;

public class Node {

    static public Value evaluateNode(Runtime runtime, Block block, Cell cell) {
        assert cell.getKind() == Cell.Kind.NODE;
        var action = (String)cell.getCar();
        var cdr = (Cell) cell.getCdr();
        switch (action) {
            case "=":
                return Node.assign(runtime, block, cdr);
            case "block":
                return Node.block(runtime, block, cdr);
        }

        return Value.nop;
    }

    static Value assign(Runtime runtime, Block block, Cell cons) {
        assert cons.getKind() == Cell.Kind.CONS;
        var car = (Cell) cons.getCar();
        var cdr = (Cell) cons.getCdr();

        assert car.getKind() == Cell.Kind.LEAF;
        assert car.getCar().equals("ID");
        var variableName = (String) car.getCdr();
        var variableValue = runtime.evaluate(block, cdr);
        block.declareVariable(variableName, variableValue);
        runtime.log("declare variable "+variableName+":"+variableValue+" in "+block);
        return Value.nop;
    }

    static Value block(Runtime runtime, Block block, Cell cons) {
        var newBlock = block.newChildBlock();
        if (cons.getKind() == Cell.Kind.LEAF) return Leaf.evaluateLeaf(runtime, newBlock, cons);
        return Node.depthFirstVisit(runtime, newBlock, cons);
    }

    static private Value depthFirstVisit(Runtime runtime, Block block, Cell cons) {
        var car = (Cell) cons.getCar();
        var cdr = (Cell) cons.getCdr();

        if (car.getKind() == Cell.Kind.CONS) {
            Node.depthFirstVisit(runtime, block, car);
        } else {
            runtime.evaluate(block, car);
        }
        return runtime.evaluate(block, cdr);
    }
}
