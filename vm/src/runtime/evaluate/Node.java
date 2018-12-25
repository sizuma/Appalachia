package runtime.evaluate;

import runtime.*;
import runtime.Runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node {

    static public Value evaluateNode(Runtime runtime, Block block, Cell cell) {
        assert cell.getKind() == Cell.Kind.NODE;
        var action = cell.getCarString();
        var cdr = cell.getCdrCell();
        switch (action) {
            case "block":
                return Node.block(runtime, block, cdr);
            case "lambda":
                return Node.lambda(runtime, block, cdr);
            case "call":
                return Node.call(runtime, block, cdr);
            case "ref":
                return Node.ref(runtime, block, cdr);
        }

        return Value.nop;
    }

    static Value block(Runtime runtime, Block block, Cell cons) {
        var newBlock = block.newChildBlock();
        if (cons.getKind() == Cell.Kind.LEAF) return Leaf.evaluateLeaf(runtime, newBlock, cons);
        else if (cons.getKind() == Cell.Kind.NODE) return Node.evaluateNode(runtime, newBlock, cons);
        return Node.depthFirstVisit(runtime, newBlock, cons);
    }

    static private Value depthFirstVisit(Runtime runtime, Block block, Cell cons) {
        var car = cons.getCarCell();
        var cdr = cons.getCdrCell();

        if (car.getKind() == Cell.Kind.CONS) {
            Node.depthFirstVisit(runtime, block, car);
        } else {
            runtime.evaluate(block, car);
        }
        return runtime.evaluate(block, cdr);
    }

    static Value lambda(Runtime runtime, Block block, Cell cons) {
        return new Value(Value.Kind.LAMBDA, new LambdaBody(block, cons));
    }

    static Value call(Runtime runtime, Block block, Cell cons) {
        var car = cons.getCarCell();
        var cdr = cons.getCdrCell();
        var lambda = runtime.evaluate(block, car);
        if (lambda.getKind() != Value.Kind.LAMBDA) throw new RuntimeException(lambda + " is not lambda");
        var body = (LambdaBody) lambda.getObject();
        var inLambdaBlock = body.getBlock().newChildBlock();

        var actualArgs = Node.evaluateArgs(runtime, block, cdr);
        if (body.getBuiltIn()) {
            return body.performBuildIn(runtime, actualArgs);
        }
        var parameters = Node.listLeave(body.getLambda().getCarCell())
                .stream()
                .filter(leaf -> !leaf.getCar().equals("NOP"))
                .collect(Collectors.toList());

        for(int index=0; index<parameters.size(); index++) {
            Value arg = Value.nop;
            if (index<actualArgs.size()) {
                arg = actualArgs.get(index);
            }
            var param = parameters.get(index);
            var paramName = param.getCdrString();
            inLambdaBlock.declareVariable(paramName.strip(), arg);
        }

        return runtime.evaluate(inLambdaBlock, (Cell) body.getLambda().getCdr());
    }

    private static List<Cell> listLeave(Cell cell) {
        switch (cell.getKind()) {
            case LEAF:
                return List.of(cell);
            case NODE:
                return listLeave((Cell) cell.getCdr());
            case CONS:
                var list = new ArrayList<Cell>();
                list.addAll(listLeave(cell.getCarCell()));
                list.addAll(listLeave(cell.getCdrCell()));
                return list;
        }
        return List.of();
    }

    private static List<Value> evaluateArgs(Runtime runtime, Block block, Cell cell) {
        switch (cell.getKind()) {
            case LEAF:
                var leafValue = Leaf.evaluateLeaf(runtime, block, cell);
                return List.of(leafValue);
            case NODE:
                var nodeOperator = (String)cell.getCar();
                nodeOperator = nodeOperator.strip();
                if (nodeOperator.equals(",")) {
                    return Node.evaluateArgs(runtime, block, (Cell) cell.getCdr());
                }
                return List.of(Node.evaluateNode(runtime, block, cell));
            case CONS:
                var list = new ArrayList<Value>();
                list.addAll(Node.evaluateArgs(runtime, block, cell.getCarCell()));
                list.addAll(Node.evaluateArgs(runtime, block, cell.getCdrCell()));
                return list;
        }
        return List.of();
    }

    static Value ref(Runtime runtime, Block block, Cell cell) {
        var car = cell.getCarCell();
        var cdr = cell.getCdrCell();

        var carValue = runtime.evaluate(block, car);
        if (carValue.getKind() != Value.Kind.BLOCK) {
            throw new RuntimeException(carValue + "is not block");
        }
        var carBlock = (Block) carValue.getObject();
        return runtime.evaluate(carBlock, cdr);
    }
}
